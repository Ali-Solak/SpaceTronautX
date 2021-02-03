package android.ali.space.database

import android.ali.space.database.ModelsLocal.LatestLaunch.LatestLaunch
import android.ali.space.database.ModelsLocal.PastLaunches.PastLaunch
import android.ali.space.database.ModelsLocal.Payload.Payload
import android.ali.space.database.ModelsLocal.Rocket.Rocket
import android.ali.space.database.ModelsLocal.UpcomingLaunches.UpcomingLaunch
import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(
    entities = [LatestLaunch::class, PastLaunch::class, Payload::class, Rocket::class, UpcomingLaunch::class],
    version = 1
)
@TypeConverters(Converters::class)
abstract class SpaceDatabase : RoomDatabase() {

    abstract fun getArticleDao(): DatabaseDao
}

private lateinit var INSTANCE: SpaceDatabase

fun getDatabase(context: Context): SpaceDatabase {
    synchronized(SpaceDatabase::class.java) {
        if (!::INSTANCE.isInitialized) {
            INSTANCE = Room.databaseBuilder(context.applicationContext,
                SpaceDatabase::class.java,
                "space_db.db").build()
        }
    }

    return INSTANCE
}