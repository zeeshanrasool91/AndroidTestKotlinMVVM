package ae.android.test.base

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow


open class BaseViewModel : ViewModel() {

    private var job: Job? = null

    protected val mutableDataFlow: MutableStateFlow<Any> =
        MutableStateFlow(Any::class.java)

    val dataToView: StateFlow<Any> = mutableDataFlow

    override fun onCleared() {
        super.onCleared()
        job?.cancel()
    }
}