package alytvyniuk.com.model

data class LaunchData (
    val crew: List<Any>,
    val details: String,
    val flight_number: Int,
    val is_tentative: Boolean,
    val launch_date_local: String,
    val launch_date_unix: Int,
    val launch_date_utc: String,
    val launch_success: Boolean,
    val launch_window: Int,
    val launch_year: String,
    val mission_id: List<Any>,
    val mission_name: String,
    val ships: List<Any>,
    val static_fire_date_unix: Int,
    val static_fire_date_utc: String,
    val tbd: Boolean,
    val tentative_max_precision: String,
    val upcoming: Boolean
)