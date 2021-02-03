package android.ali.space.api.modelsRemote.PastLaunches

data class Fairings(
    val recovered: Boolean,
    val recovery_attempt: Boolean,
    val reused: Boolean,
    val ships: List<String>
)