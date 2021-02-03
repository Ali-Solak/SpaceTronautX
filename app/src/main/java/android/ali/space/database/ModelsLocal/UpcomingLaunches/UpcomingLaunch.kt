package android.ali.space.database.ModelsLocal.UpcomingLaunches

import android.ali.space.api.modelsRemote.UpcomingLaunch.Core
import android.ali.space.api.modelsRemote.UpcomingLaunch.Fairings
import android.ali.space.api.modelsRemote.UpcomingLaunch.Links
import android.ali.space.database.ModelsLocal.PastLaunches.PastLaunch
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity
data class UpcomingLaunch(

    @PrimaryKey
    val id: String,
    val date_local: String,
    val details: String? = "no details available",
    val name: String,
    val patch: String? = "no patch",
    val payloads: String? = "no payload details",
    val rocket: String
):Serializable
