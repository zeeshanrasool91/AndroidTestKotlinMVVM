package ae.android.test.ui.activities

import ae.android.test.base.AppConstants
import ae.android.test.networking.NetController
import ae.android.test.networking.api.ApiMethods
import ae.android.test.networking.api.ResultWrapper
import ae.android.test.networking.response.MainResponse
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

class MainRepositoryImpl (private val service: ApiMethods, private val dispatcher: CoroutineDispatcher = Dispatchers.Main) :
    MainRepository {
    override suspend fun getMostPopularList(): ResultWrapper<MainResponse> {
        return NetController.callApi(dispatcher) {
            service.getMostPopularList(1,AppConstants.API_KEY)
        }
    }

}