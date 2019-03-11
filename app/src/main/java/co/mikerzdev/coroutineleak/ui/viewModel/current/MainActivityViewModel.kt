package co.mikerzdev.coroutineleak.ui.viewModel.current

import android.arch.lifecycle.MutableLiveData
import android.util.Log
import co.mikerzdev.coroutineleak.Constants.COROUTINE_EXEC_TIME
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivityViewModel : BaseViewModel() {

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
            Log.d("ViewModelCoroutine", "Im Alive!!")
            liveData.postValue("im from a safe coroutine!")
        }
    }
}