package ae.android.test.networking.api

import ae.android.test.networking.response.MainResponse
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface ApiMethods {
    @GET("svc/mostpopular/v2/viewed/{period}.json")
    suspend fun getMostPopularList(@Path("period") period: Int,
                                   @Query("api-key") apiKey: String): Response<MainResponse>

}
