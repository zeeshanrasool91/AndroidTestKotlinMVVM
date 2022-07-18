package ae.android.test.ui.activities

import ae.android.test.networking.api.ResultWrapper
import ae.android.test.networking.response.MainResponse
import kotlinx.coroutines.flow.Flow


interface MainRepository {
    fun getMostPopularList(): Flow<ResultWrapper<MainResponse>>
}