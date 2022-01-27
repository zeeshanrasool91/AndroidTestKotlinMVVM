package ae.android.test.ui.activities

import ae.android.test.networking.api.ResultWrapper
import ae.android.test.networking.response.MainResponse


interface MainRepository {
    suspend fun getMostPopularList(): ResultWrapper<MainResponse>
}