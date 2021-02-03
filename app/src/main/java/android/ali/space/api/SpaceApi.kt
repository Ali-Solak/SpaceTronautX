package android.ali.space.api

import android.ali.space.api.modelsRemote.LatestLaunch.LatestLaunchModel
import android.ali.space.api.modelsRemote.PastLaunches.PastLaunches
import android.ali.space.api.modelsRemote.Payload.Payload
import android.ali.space.api.modelsRemote.Rocket.Rocket
import android.ali.space.api.modelsRemote.UpcomingLaunch.UpcomingLaunch
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface SpaceApi {

    @GET("v4/launches/latest")
    suspend fun getLatestLaunch():Response<LatestLaunchModel>

    @GET("v4/rockets")
    suspend fun getRocket():Response<Rocket>

    @GET("v4/payloads")
    suspend fun getPayload():Response<Payload>

    @GET("v4/launches/upcoming")
    suspend fun getUpcomingLaunch():Response<UpcomingLaunch>

    @GET("v4/launches/past")
    suspend fun getPastLaunches():Response<PastLaunches>

}