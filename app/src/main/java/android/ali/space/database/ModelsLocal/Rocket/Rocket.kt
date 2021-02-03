package android.ali.space.database.ModelsLocal.Rocket

import android.ali.space.api.modelsRemote.Rocket.*
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Rocket (
    @PrimaryKey
    val id: String,
    val company: String,
    val cost_per_launch: Int,
    val country: String,
    val description: String,
    val first_flight: String,
    val images: List<String>,
    val height: Double,
    val kg: Int,
    val name: String,
    val stages: Int,
    val legs: Int,
    val success_rate_pct: Int,
    val type: String
)