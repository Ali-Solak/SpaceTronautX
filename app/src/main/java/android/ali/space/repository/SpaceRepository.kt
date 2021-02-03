package android.ali.space.repository

import android.ali.space.api.modelsRemote.LatestLaunch.LatestLaunchModel
import android.ali.space.api.modelsRemote.PastLaunches.PastLaunches
import android.ali.space.api.modelsRemote.Payload.Payload
import android.ali.space.api.modelsRemote.Rocket.Rocket
import android.ali.space.api.modelsRemote.UpcomingLaunch.UpcomingLaunch
import android.ali.space.database.ModelsLocal.LatestLaunch.LatestLaunch
import android.ali.space.database.ModelsLocal.PastLaunches.PastLaunch
import android.ali.space.database.SpaceDatabase
import androidx.lifecycle.LiveData
import retrofit2.Response

class SpaceRepository(val db: SpaceDatabase) {


    suspend fun getLatestLaunchApi() : Response<LatestLaunchModel>{
        return RetrofitInstance.api.getLatestLaunch()
    }

    suspend fun getRocketApi(): Response<Rocket>{
        return RetrofitInstance.api.getRocket()
    }
    suspend fun getPayload(): Response<Payload>{
        return RetrofitInstance.api.getPayload()
    }

    suspend fun getPastLaunches(): Response<PastLaunches>{
        return RetrofitInstance.api.getPastLaunches()
    }

    suspend fun getUpcomingLaunches(): Response<UpcomingLaunch>{
        return RetrofitInstance.api.getUpcomingLaunch()
    }

    suspend fun getLatestLaunchDb() : LatestLaunch{
        return db.getArticleDao().getLatestLaunch()
    }

    suspend fun upsertLatestLaunchDb(latestLaunch: LatestLaunch){
        return db.getArticleDao().insertLatestLaunch(latestLaunch)
    }

    suspend fun getRocketDb(id: String): android.ali.space.database.ModelsLocal.Rocket.Rocket{
        return db.getArticleDao().getRocket(id)
    }

    suspend fun upsertRocket(rocket: List<android.ali.space.database.ModelsLocal.Rocket.Rocket>){
        return db.getArticleDao().insertRocket(rocket)
    }

    suspend fun getPayloadDb(id: String): android.ali.space.database.ModelsLocal.Payload.Payload{
        return db.getArticleDao().getPayload(id)
    }

    suspend fun UpsertPayload(payload: List<android.ali.space.database.ModelsLocal.Payload.Payload>){
        return db.getArticleDao().insertPayload(payload)
    }

    suspend fun getPastLaunchesDb() : List<PastLaunch>{
        return db.getArticleDao().getPastLaunches()
    }

    suspend fun upsertPastLaunches(pastLaunch: List<PastLaunch>){
        return db.getArticleDao().insertAllPastLaunches(pastLaunch)
    }

    suspend fun getUpcomingLaunchesDb() : List<android.ali.space.database.ModelsLocal.UpcomingLaunches.UpcomingLaunch>{
        return db.getArticleDao().getUpcomingLaunches()
    }

    suspend fun upsertUpcomingLaunches(upcomingLaunch: List<android.ali.space.database.ModelsLocal.UpcomingLaunches.UpcomingLaunch>){
        return db.getArticleDao().insertAllUpcomingLaunches(upcomingLaunch)
    }





}