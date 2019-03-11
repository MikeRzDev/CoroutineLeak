package co.mikerzdev.coroutineleak.ui.viewModel

import android.support.v7.app.AppCompatActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlin.coroutines.CoroutineContext

abstract class BaseActivity : AppCompatActivity(), CoroutineScope {
    private var job: Job? = null
    override val coroutineContext: CoroutineContext
        get() {
            if (job == null || job?.isCancelled == true) job = Job()
            return Dispatchers.Main + job!!
        }

    override fun onPause() {
        super.onPause()
        job?.cancel()
    }
}