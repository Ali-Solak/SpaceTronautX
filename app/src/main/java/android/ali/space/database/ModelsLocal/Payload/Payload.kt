package android.ali.space.database.ModelsLocal.Payload

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Payload(
        @PrimaryKey
        var id: String,
        val customers: List<String>,
        val manufacturers: List<String>,
        val mass_kg: Double,
        val reused: Boolean,
        val type: String,
        val name: String,
        val nationalities: List<String>
)