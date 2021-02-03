package android.ali.space.database

import android.ali.space.database.ModelsLocal.LatestLaunch.LatestLaunch
import android.ali.space.database.ModelsLocal.PastLaunches.PastLaunch
import android.ali.space.database.ModelsLocal.Payload.Payload
import android.ali.space.database.ModelsLocal.Rocket.Rocket
import android.ali.space.database.ModelsLocal.UpcomingLaunches.UpcomingLaunch
import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface DatabaseDao {

    @Query("select * from latestlaunch")
    fun getLatestLaunch(): LatestLaunch

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertLatestLaunch(latestLaunch: LatestLaunch)

    @Query("select * from upcominglaunch")
    fun getUpcomingLaunches(): List<UpcomingLaunch>

    @JvmSuppressWildcards
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllUpcomingLaunches(upcomingLaunches : List<UpcomingLaunch>)

    @Query("SELECT * FROM rocket WHERE id = :id")
    fun getRocket(id: String): Rocket

    @JvmSuppressWildcards
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertRocket(objects: List<Rocket>)

    @Query("SELECT * FROM payload WHERE id = :id")
    fun getPayload(id: String): Payload

    @JvmSuppressWildcards
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPayload(objects: List<Payload>)

    @Query("select * from pastlaunch")
    fun getPastLaunches(): List<PastLaunch>

    @JvmSuppressWildcards
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllPastLaunches(objects: List<PastLaunch>)





}
