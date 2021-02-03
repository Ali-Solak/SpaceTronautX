package android.ali.space.database.ModelsLocal.PastLaunches

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity
data class PastLaunch(
    @PrimaryKey
    var id: String,
    val date_local: String,
    val details: String? = "no Detail available",
    val pictures: List<String>,
    val patch: String? = "no Patch available",
    val name: String,
    val payload: String,
    val rocket: String,
    val success: Boolean,
    val youtubeId: String? = "not available"
):Serializable
