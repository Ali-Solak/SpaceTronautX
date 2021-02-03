package android.ali.space.api.modelsRemote.Rocket

data class Engines(
    val engine_loss_max: Any,
    val isp: Isp,
    val layout: Any,
    val number: Int,
    val propellant_1: String,
    val propellant_2: String,
    val thrust_sea_level: ThrustSeaLevel,
    val thrust_to_weight: Double,
    val thrust_vacuum: ThrustVacuum,
    val type: String,
    val version: String
)