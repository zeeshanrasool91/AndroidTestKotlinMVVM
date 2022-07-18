package ae.android.test.ui.activities

import ae.android.test.base.AppConstants
import ae.android.test.networking.NetController
import ae.android.test.networking.api.ApiMethods
import ae.android.test.networking.api.ResultWrapper
import ae.android.test.networking.response.MainResponse
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class MainRepositoryImpl(
    private val service: ApiMethods,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) :
    MainRepository {
    override fun getMostPopularList(): Flow<ResultWrapper<MainResponse>> {
        return flow {
            val mainResponse = NetController.callApi(dispatcher) {
                service.getMostPopularList(AppConstants.PERIOD, AppConstants.API_KEY)
            }
            emit(mainResponse)
        }
    }

}