package alytvyniuk.com.model

sealed class LaunchesResponse
class SuccessResponse(val launches: List<LaunchData>): LaunchesResponse()
class FailureResponse(val error: Throwable): LaunchesResponse()