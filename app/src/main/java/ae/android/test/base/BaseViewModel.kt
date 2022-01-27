package ae.android.test.base

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.Job


open class BaseViewModel<T> : ViewModel() {

    internal var job: Job? = null
    private lateinit var resultWrapper: SingleLiveEvent<T>
    val dataToView: SingleLiveEvent<T>
        get() {
            if (!::resultWrapper.isInitialized) {
                resultWrapper = SingleLiveEvent<T>()
            }
            return resultWrapper
        }

    private lateinit var loaderWrapper: SingleLiveEvent<Boolean>
    val loader: SingleLiveEvent<Boolean>
        get() {
            if (!::loaderWrapper.isInitialized) {
                loaderWrapper = SingleLiveEvent()
            }
            return loaderWrapper
        }

    override fun onCleared() {
        super.onCleared()
        job?.cancel()
    }
}