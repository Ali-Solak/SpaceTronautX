package android.ali.space.database.ModelsLocal.LatestLaunch

import android.ali.space.api.modelsRemote.LatestLaunch.Core
import android.ali.space.api.modelsRemote.LatestLaunch.Fairings
import android.ali.space.api.modelsRemote.LatestLaunch.Links
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class LatestLaunch(
    @PrimaryKey
    var id: String,
    val date_local: String,
    val details: String,
    val pictures: List<String>,
    val patch: String,
    val name: String,
    val payload: String,
    val rocket: String,
    val youtubeID: String,
)