package alytvyniuk.com.spacexapp

import alytvyniuk.com.model.*
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.nhaarman.mockitokotlin2.mock
import io.kotlintest.matchers.types.shouldBeInstanceOf
import io.kotlintest.shouldBe
import io.reactivex.Single
import io.reactivex.Single.just
import org.junit.Before
import org.junit.Rule
import org.junit.Test


class LaunchesViewModelTest {

    @get:Rule
    val taskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val rxSchedulerRule = RxSchedulerRule()

    private lateinit var launchesViewModel: LaunchesViewModel

    @Before
    fun setup() {
        launchesViewModel = LaunchesViewModel(object : LaunchesRepository {
            override fun getLaunchesInRange(start: Int, count: Int): Single<LaunchesResponse> {
                return just(SuccessResponse(List(count) { mock<LaunchData>() }))
            }
        })
    }

    @Test
    fun `test requestMoreLaunches twice, launchesLiveData should change appropriately`() = runViewModelTest(
        object : LaunchesRepository {
            override fun getLaunchesInRange(start: Int, count: Int): Single<LaunchesResponse> {
                return just(SuccessResponse(List(count) { mock<LaunchData>() }))
            }
        }
    ) {
        val launchesObserver = launchesViewModel.launchesLiveData.testObserver()
        launchesViewModel.requestMoreLaunches()
        launchesViewModel.requestMoreLaunches()
        val result = launchesObserver.observedValues
        result.size shouldBe 4
        result[0]!!.size shouldBe REQUEST_THRESHOLD
        result[0]!![0].shouldBeInstanceOf<ProgressItem>()
        result[1]!!.size shouldBe REQUEST_THRESHOLD
        result[1]!![0].shouldBeInstanceOf<LaunchesDataItem>()
        result[2]!!.size shouldBe REQUEST_THRESHOLD * 2
        result[2]!![REQUEST_THRESHOLD].shouldBeInstanceOf<ProgressItem>()
        result[3]!!.size shouldBe REQUEST_THRESHOLD * 2
        result[3]!![REQUEST_THRESHOLD].shouldBeInstanceOf<LaunchesDataItem>()
    }

    @Test
    fun `test all launches received`() = runViewModelTest(
        object : LaunchesRepository {
            override fun getLaunchesInRange(start: Int, count: Int): Single<LaunchesResponse> {
                return just(SuccessResponse(List(count - 1) { mock<LaunchData>() }))
            }
        }
    ) {
        val launchesObserver = launchesViewModel.launchesLiveData.testObserver()
        val allItemsReceivedObserver = launchesViewModel.allItemsReceived.testObserver()
        launchesViewModel.requestMoreLaunches()
        launchesViewModel.requestMoreLaunches()
        val result = launchesObserver.observedValues
        result.size shouldBe 2
        result[0]!!.size shouldBe REQUEST_THRESHOLD
        result[0]!![0].shouldBeInstanceOf<ProgressItem>()
        result[1]!!.size shouldBe REQUEST_THRESHOLD - 1
        result[1]!![0].shouldBeInstanceOf<LaunchesDataItem>()
        allItemsReceivedObserver.observedValues.size shouldBe 1
        allItemsReceivedObserver.observedValues[0] shouldBe true
    }

    @Test
    fun `test launches error`() {
        val testThrowable = Throwable("Test throwable")
        runViewModelTest(
            object : LaunchesRepository {
                override fun getLaunchesInRange(start: Int, count: Int): Single<LaunchesResponse> {
                    return just(FailureResponse(testThrowable))
                }
            }
        ) {
            val launchesObserver = launchesViewModel.launchesLiveData.testObserver()
            val errorObserver = launchesViewModel.errorLiveData.testObserver()
            launchesViewModel.requestMoreLaunches()
            val result = launchesObserver.observedValues
            result.size shouldBe 1
            result[0]!!.size shouldBe REQUEST_THRESHOLD
            result[0]!![0].shouldBeInstanceOf<ProgressItem>()
            errorObserver.observedValues.size shouldBe 1
            errorObserver.observedValues[0] shouldBe testThrowable
        }
    }

    private fun runViewModelTest(launchesRepository: LaunchesRepository, test: () -> Unit) {
        launchesViewModel = LaunchesViewModel(launchesRepository)
        test.invoke()
    }
}
