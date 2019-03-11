package co.mikerzdev.coroutineleak.ui.viewModel.safe

import android.arch.lifecycle.MutableLiveData
import android.util.Log
import co.mikerzdev.coroutineleak.Constants.COROUTINE_EXEC_TIME
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivitySafeViewModel : BaseSafeViewModel() {

    val liveData =  MutableLiveData<String>()

    fun doSomethingWithCoroutine() {
        GlobalScope.launch(Dispatchers.Main) {
            Log.d("ViewModelCoroutine", "coroutine started...")
            delay(COROUTINE_EXEC_TIME)
            Log.d("ViewModelCoroutine", "Im Alive!!")
            liveData.postValue("im from a bad coroutine!")
        }
    }

    fun doSomethingWithCoroutineSafe() {
        launch {
            Log.d("ViewModelCoroutine", "coroutine started...")
            delay(COROUTINE_EXEC_TIME)
            Log.d("ViewModelCoroutine", "Im Alive!! and safe")
            liveData.postValue("im from a safe coroutine!")
        }
    }
}