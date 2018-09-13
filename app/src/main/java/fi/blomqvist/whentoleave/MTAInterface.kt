package fi.blomqvist.whentoleave

import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path

interface MTAInterface {

    @GET("/schedule/{feedId}/stop/{stopId}/direction/{direction}")
    fun getSchedule(@Path("feedId") feedId: String, @Path("stopId") stopId: String, @Path("direction") direction: String) : Observable<List<TrainInfo>>
}