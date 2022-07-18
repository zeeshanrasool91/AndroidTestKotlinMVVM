package ae.android.test.ui.activities

import ae.android.test.networking.NetController
import ae.android.test.networking.api.ApiMethods
import ae.android.test.networking.api.ResultWrapper
import ae.android.test.networking.response.MainResponse
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val apiMethods: ApiMethods) : ViewModel() {


    private val _dataFlow: MutableStateFlow<ResultWrapper<MainResponse>> =
        MutableStateFlow(ResultWrapper.Failed(null))

    val dataToView: StateFlow<ResultWrapper<MainResponse>> = _dataFlow

    fun callListApi() = viewModelScope.launch {
        _dataFlow.value = ResultWrapper.Failed(null)
        val repoImpl = MainRepositoryImpl(apiMethods)
        repoImpl.getMostPopularList()
            .catch { e ->
                _dataFlow.value = ResultWrapper.Exception(e)
            }.collectLatest { data ->
                _dataFlow.value = data
            }
    }

}