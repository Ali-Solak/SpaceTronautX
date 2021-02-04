package android.ali.space.ui.ViewModel

import android.ali.space.api.modelsRemote.LatestLaunch.LatestLaunchModel
import android.ali.space.api.modelsRemote.PastLaunches.PastLaunches
import android.ali.space.database.ModelsLocal.LatestLaunch.LatestLaunch
import android.ali.space.database.ModelsLocal.PastLaunches.PastLaunch
import android.ali.space.database.ModelsLocal.Payload.Payload
import android.ali.space.database.ModelsLocal.Rocket.Rocket
import android.ali.space.database.ModelsLocal.UpcomingLaunches.UpcomingLaunch
import android.ali.space.database.getDatabase
import android.ali.space.repository.SpaceRepository
import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Response
import java.lang.Exception

class SpaceViewModel(app: Application) :
    AndroidViewModel(app) {


    init {
        refreshPayloads()
        refreshRockets()
        refreshPastLaunches()
        refreshUpcomingLaunches()
        refreshlatestLaunch()
    }

    val db = getDatabase(app)
    val spaceRepo = SpaceRepository(db)

    val latestLaunchLive = MutableLiveData<LatestLaunch>()

    val errorMessage = MutableLiveData<String>()

    var upcomingLaunches = MutableLiveData<List<UpcomingLaunch>>()

    val pastLaunches = MutableLiveData<List<PastLaunch>>()

    val rocket = MutableLiveData<Rocket>()

    val payload = MutableLiveData<Payload>()


    fun refreshlatestLaunch() = viewModelScope.launch {
        try {
            withContext(Dispatchers.IO) {
                var latestLaunch: LatestLaunch
                val response: Response<LatestLaunchModel> = spaceRepo.getLatestLaunchApi()

                if (response.isSuccessful) {

                    Log.d("HERETHIS", response.body().toString())

                    response.body()?.let { resultResponse ->

                        latestLaunch = LatestLaunch(
                            "1",
                            resultResponse.date_local,
                            resultResponse.details,
                            resultResponse.links.flickr.original,
                            resultResponse.links.patch.small,
                            resultResponse.name,
                            resultResponse.payloads.first(),
                            resultResponse.rocket,
                            resultResponse.links.youtube_id,
                        )
                        latestLaunchLive.postValue(latestLaunch)
                        spaceRepo.upsertLatestLaunchDb(latestLaunch)
                    }
                } else {
                    Log.d("RefreshLL", "Failed to fetch")
                }

            }
        } catch (e: Exception) {

            Log.d("RefreshErrorLL", e.message.toString())
        }
    }

    fun refreshUpcomingLaunches() = viewModelScope.launch {

        try {
            withContext(Dispatchers.IO) {
                var upcomingLaunch: MutableList<UpcomingLaunch> = mutableListOf<UpcomingLaunch>()
                val response: Response<android.ali.space.api.modelsRemote.UpcomingLaunch.UpcomingLaunch> =
                    spaceRepo.getUpcomingLaunches()

                Log.d("HERETHIS", response.body().toString())

                if (response.isSuccessful) {
                    response.body()?.let { resultResponse ->

                        upcomingLaunch = resultResponse.map { it ->
                            UpcomingLaunch(
                                it.id,
                                it.date_local,
                                it.details,
                                it.name,
                                it.links.patch.small,
                                it.payloads.firstOrNull(),
                                it.rocket
                            )
                        }.toMutableList()

                        spaceRepo.upsertUpcomingLaunches(upcomingLaunch)
                    }
                } else {
                    Log.d("RefreshUP", "Failed to fetch")
                }

            }
        } catch (e: Exception) {

            Log.d("RefreshUP", e.message.toString())
        }

    }

    fun refreshPastLaunches() = viewModelScope.launch {
        try {
            withContext(Dispatchers.IO) {
                var pastLaunches: MutableList<PastLaunch> = mutableListOf<PastLaunch>()
                val response: Response<PastLaunches> = spaceRepo.getPastLaunches()

                Log.d("HERETHIS", response.body().toString())

                if (response.isSuccessful) {
                    response.body()?.let { resultResponse ->

                        pastLaunches = resultResponse.map { it ->
                            PastLaunch(
                                it.id,
                                it.date_local,
                                it.details,
                                it.links.flickr.original,
                                it.links.patch.small,
                                it.name,
                                it.payloads.first(),
                                it.rocket,
                                it.success,
                                it.links.youtube_id
                            )
                        }.toMutableList()

                        spaceRepo.upsertPastLaunches(pastLaunches)
                    }
                } else {
                    Log.d("RefreshPL", "Failed to fetch")
                }
            }
        } catch (e: Exception) {
            Log.d("RefreshPL ", e.message.toString())
        }
    }

    fun refreshRockets() = viewModelScope.launch {
        try {
            withContext(Dispatchers.IO) {
                var rockets: MutableList<Rocket> = mutableListOf<Rocket>()
                val response: Response<android.ali.space.api.modelsRemote.Rocket.Rocket> =
                    spaceRepo.getRocketApi()

                Log.d("HERETHIS", response.body().toString())

                if (response.isSuccessful) {
                    response.body()?.let { resultResponse ->

                        rockets = resultResponse.map { it ->
                            Rocket(
                                it.id,
                                it.company,
                                it.cost_per_launch,
                                it.country,
                                it.description,
                                it.first_flight,
                                it.flickr_images,
                                it.height.meters,
                                it.mass.kg,
                                it.name,
                                it.stages,
                                it.landing_legs.number,
                                it.success_rate_pct,
                                it.type
                            )
                        }.toMutableList()

                        spaceRepo.upsertRocket(rockets)
                    }
                } else {
                    Log.d("RefreshRO", "Failed to fetch")
                }

            }
        } catch (e: Exception) {
            Log.d("RefreshRo ", e.message.toString())
        }
    }

    fun refreshPayloads() = viewModelScope.launch {
        try {
            withContext(Dispatchers.IO) {
                var payloads: MutableList<Payload> = mutableListOf<Payload>()
                val response: Response<android.ali.space.api.modelsRemote.Payload.Payload> =
                    spaceRepo.getPayload()

                Log.d("HERETHIS", response.body().toString())

                if (response.isSuccessful) {
                    response.body()?.let { resultResponse ->

                        payloads = resultResponse.map { it ->
                            Payload(
                                it.id,
                                it.customers,
                                it.manufacturers,
                                it.mass_kg,
                                it.reused,
                                it.type,
                                it.name,
                                it.nationalities
                            )
                        }.toMutableList()

                        spaceRepo.UpsertPayload(payloads)
                    }
                } else {
                    Log.d("RefreshPAY", "Failed to fetch")
                }
            }
        } catch (e: Exception) {
            Log.d("RefreshPAY ", e.message.toString())
        }
    }

    fun getLatestLaunchFromDb() = viewModelScope.launch {
        withContext(Dispatchers.IO) {
            latestLaunchLive.postValue(spaceRepo.getLatestLaunchDb())
        }
    }

    fun getUpcomingLaunchesFromDb() = viewModelScope.launch {
        withContext(Dispatchers.IO) {
            upcomingLaunches.postValue(spaceRepo.getUpcomingLaunchesDb())
        }
    }

    fun getRocketFromDb(id: String) = viewModelScope.launch {
        withContext(Dispatchers.IO) {
            rocket.postValue(spaceRepo.getRocketDb(id))
        }
    }

    fun getPayloadFromDb(id: String) = viewModelScope.launch {
        withContext(Dispatchers.IO) {
            payload.postValue(spaceRepo.getPayloadDb(id))
        }
    }

    fun getPastLaunchesFromDb() = viewModelScope.launch {
        withContext(Dispatchers.IO) {
            pastLaunches.postValue(spaceRepo.getPastLaunchesDb())
        }
    }


}
