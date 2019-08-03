package alytvyniuk.com.data.repository

internal data class LaunchesResponseModel(
    val crew: List<Any>,
    val details: String,
    val flight_number: Int,
    val is_tentative: Boolean,
    val launch_date_local: String,
    val launch_date_unix: Int,
    val launch_date_utc: String,
    val launch_failure_details: LaunchFailureDetails,
    val launch_site: LaunchSite,
    val launch_success: Boolean?,
    val launch_window: Int,
    val launch_year: String,
    val links: Links,
    val mission_id: List<Any>,
    val mission_name: String,
    val rocket: Rocket,
    val ships: List<Any>,
    val static_fire_date_unix: Int,
    val static_fire_date_utc: String,
    val tbd: Boolean,
    val telemetry: Telemetry,
    val tentative_max_precision: String,
    val timeline: Timeline,
    val upcoming: Boolean
)

internal data class LaunchSite(
    val site_id: String,
    val site_name: String,
    val site_name_long: String
)

internal data class Telemetry(
    val flight_club: Any
)

internal data class LaunchFailureDetails(
    val altitude: Int,
    val reason: String,
    val time: Int
)

internal data class Timeline(
    val beco: Int,
    val center_core_entry_burn: Int,
    val center_core_landing: Int,
    val center_stage_sep: Int,
    val engine_chill: Int,
    val fairing_deploy: Int,
    val go_for_launch: Int,
    val go_for_prop_loading: Int,
    val ignition: Int,
    val liftoff: Int,
    val maxq: Int,
    val meco: Int,
    val payload_deploy: Int,
    val prelaunch_checks: Int,
    val propellant_pressurization: Int,
    val `seco-1`: Int,
    val `seco-2`: Int,
    val `seco-3`: Int,
    val `seco-4`: Int,
    val second_stage_ignition: Int,
    val second_stage_restart: Int,
    val side_core_boostback: Int,
    val side_core_entry_burn: Int,
    val side_core_landing: Int,
    val side_core_sep: Int,
    val stage1_lox_loading: Int,
    val stage1_rp1_loading: Int,
    val stage2_lox_loading: Int,
    val stage2_rp1_loading: Int,
    val webcast_liftoff: Any
)

internal data class Rocket(
    val fairings: Fairings,
    val first_stage: FirstStage,
    val rocket_id: String,
    val rocket_name: String,
    val rocket_type: String,
    val second_stage: SecondStage
)

internal data class Fairings(
    val recovered: Any,
    val recovery_attempt: Any,
    val reused: Boolean,
    val ship: Any
)

internal data class SecondStage(
    val block: Int,
    val payloads: List<Payload>
)

internal data class Payload(
    val customers: List<String>,
    val manufacturer: String,
    val nationality: String,
    val norad_id: List<Any>,
    val orbit: String,
    val orbit_params: OrbitParams,
    val payload_id: String,
    val payload_mass_kg: Any,
    val payload_mass_lbs: Any,
    val payload_type: String,
    val reused: Boolean
)

internal data class OrbitParams(
    val apoapsis_km: Any,
    val arg_of_pericenter: Any,
    val eccentricity: Any,
    val epoch: Any,
    val inclination_deg: Any,
    val lifespan_years: Float,
    val longitude: Any,
    val mean_anomaly: Any,
    val mean_motion: Any,
    val periapsis_km: Any,
    val period_min: Any,
    val raan: Any,
    val reference_system: String,
    val regime: String,
    val semi_major_axis_km: Any
)

internal data class FirstStage(
    val cores: List<Core>
)

internal data class Core(
    val block: Int,
    val core_serial: Any,
    val flight: Any,
    val gridfins: Any,
    val land_success: Any,
    val landing_intent: Any,
    val landing_type: Any,
    val landing_vehicle: Any,
    val legs: Any,
    val reused: Boolean
)

internal data class Links(
    val article_link: Any,
    val flickr_images: List<Any>,
    val mission_patch: Any,
    val mission_patch_small: String,
    val presskit: Any,
    val reddit_campaign: Any,
    val reddit_launch: Any,
    val reddit_media: Any,
    val reddit_recovery: Any,
    val video_link: Any,
    val wikipedia: Any,
    val youtube_id: Any
)