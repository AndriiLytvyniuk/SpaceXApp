package alytvyniuk.com.spacexapp

import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.plugins.RxJavaPlugins
import io.reactivex.schedulers.Schedulers
import org.junit.rules.TestRule
import org.junit.runner.Description
import org.junit.runners.model.Statement

private val SCHEDULER_INSTANCE = Schedulers.trampoline()

class RxSchedulerRule : TestRule {

    override fun apply(base: Statement, description: Description) =
        object : Statement() {
            override fun evaluate() {
                RxAndroidPlugins.reset()
                RxAndroidPlugins.setInitMainThreadSchedulerHandler { SCHEDULER_INSTANCE }

                RxJavaPlugins.reset()
                RxJavaPlugins.setIoSchedulerHandler { SCHEDULER_INSTANCE }
                RxJavaPlugins.setNewThreadSchedulerHandler { SCHEDULER_INSTANCE }
                RxJavaPlugins.setComputationSchedulerHandler { SCHEDULER_INSTANCE }

                base.evaluate()
            }
        }
}

open class TestObserver<T> : Observer<T> {

    val observedValues = mutableListOf<T?>()

    override fun onChanged(value: T?) {
        observedValues.add(value)
    }
}

fun <T> LiveData<T>.testObserver() = TestObserver<T>().also {
    observeForever(it)
}