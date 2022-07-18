package ae.android.test.ui.activities

import ae.android.test.networking.NetController
import ae.android.test.networking.api.ResultWrapper
import ae.android.test.networking.response.MainResponse
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class MainViewModel() : ViewModel() {


    private val _dataFlow: MutableStateFlow<ResultWrapper<MainResponse>> =
        MutableStateFlow(ResultWrapper.Failed(null))

    val dataToView: StateFlow<ResultWrapper<MainResponse>> = _dataFlow

    fun callListApi() = viewModelScope.launch {
        val service = NetController.apiMethods
        _dataFlow.value = ResultWrapper.Failed(null)
        val repoImpl = MainRepositoryImpl(service)
        repoImpl.getMostPopularList()
            .catch { e ->
                _dataFlow.value = ResultWrapper.Exception(e)
            }.collectLatest { data ->
                _dataFlow.value = data
            }
    }

}