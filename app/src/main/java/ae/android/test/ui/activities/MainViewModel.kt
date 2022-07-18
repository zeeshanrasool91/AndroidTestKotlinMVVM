package ae.android.test.ui.activities

import ae.android.test.R
import ae.android.test.MyApp
import ae.android.test.base.BaseViewModel
import ae.android.test.networking.NetController
import ae.android.test.networking.api.ResultWrapper
import ae.android.test.networking.response.MainResponse
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.IOException
import java.net.ConnectException

class MainViewModel() : BaseViewModel<ResultWrapper<MainResponse>>() {


    fun callListApi() {
        val handler = CoroutineExceptionHandler { _, exception ->
            //Handle your exception
            exception.printStackTrace()
            val message = MyApp.appContext?.get()?.getString(R.string.internet_connectivity_required)
            val throwable = if (exception is IOException || exception is ConnectException) {
                Throwable(message)
            } else {
                exception
            }
            dataToView.postValue(ResultWrapper.Exception(throwable))
        }
        val service = NetController.apiMethods
        job = viewModelScope.launch(Dispatchers.IO + handler) {
            val repoImpl = MainRepositoryImpl(service, Dispatchers.Main)
            val apiResponse = repoImpl.getMostPopularList()
            dataToView.postValue(apiResponse)
        }
    }

}