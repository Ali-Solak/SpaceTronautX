package android.ali.space.api.modelsRemote.Rocket

data class SecondStage(
    val burn_time_sec: Any,
    val engines: Int,
    val fuel_amount_tons: Double,
    val payloads: Payloads,
    val reusable: Boolean,
    val thrust: Thrust
)