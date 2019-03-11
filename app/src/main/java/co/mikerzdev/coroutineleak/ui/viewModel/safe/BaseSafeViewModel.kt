package co.mikerzdev.coroutineleak.ui.viewModel.safe

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LifecycleObserver
import android.arch.lifecycle.OnLifecycleEvent
import android.arch.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlin.coroutines.CoroutineContext

abstract class BaseSafeViewModel: ViewModel(), CoroutineScope, LifecycleObserver {
    private var job: Job? = null
    override val coroutineContext: CoroutineContext
        get() {
            if (job == null || job?.isCancelled == true) job = Job()
            return Dispatchers.Main + job!!
        }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    fun onPause() {
        job?.cancel()
    }
}