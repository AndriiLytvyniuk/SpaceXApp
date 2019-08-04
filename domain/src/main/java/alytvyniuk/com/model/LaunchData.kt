package alytvyniuk.com.model

data class LaunchData (
    val flightNumber: Int,
    val missionName: String,
    val rocketName: String,
    val missionDate: Long,
    val missionImage: String?,
    val isSuccess: Boolean?,
    val isUpcoming: Boolean,
    val details: String?
)