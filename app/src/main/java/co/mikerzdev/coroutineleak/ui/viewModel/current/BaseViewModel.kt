package co.mikerzdev.coroutineleak.ui.viewModel.current

import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModel
import kotlinx.coroutines.*
import java.lang.ref.WeakReference

abstract class BaseViewModel : ViewModel() {
   private val jobs = ArrayList<WeakReference<Job>>()

   override fun onCleared() {
      jobs.forEach { it.get()?.cancel() }
   }

   fun launch(block: suspend CoroutineScope.() -> Unit): Job {
      val job = GlobalScope.launch(Dispatchers.Main, block = block)
      this.jobs += WeakReference(job)
      return job
   }

}

fun <T> LiveData<T>.bind(owner: LifecycleOwner, observer: (T) -> Unit) {
   this.observe(owner, Observer<T> { it?.run { observer(it) } })
}