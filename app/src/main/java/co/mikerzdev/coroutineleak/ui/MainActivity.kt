package co.mikerzdev.coroutineleak.ui

import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import co.mikerzdev.coroutineleak.Constants.COROUTINE_EXEC_TIME
import co.mikerzdev.coroutineleak.R
import co.mikerzdev.coroutineleak.ui.viewModel.BaseActivity
import co.mikerzdev.coroutineleak.ui.viewModel.current.MainActivityViewModel
import co.mikerzdev.coroutineleak.ui.viewModel.current.bind
import co.mikerzdev.coroutineleak.ui.viewModel.safe.MainActivitySafeViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : BaseActivity() {

    // TODO: try to propagate exception over the coroutine THIS IS NEEDED because if something crashes it could not be HANDLED!

    private lateinit var vm : MainActivityViewModel
    private lateinit var vmSafe : MainActivitySafeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        vm = ViewModelProviders.of(this).get(MainActivityViewModel::class.java)

        vmSafe = ViewModelProviders.of(this).get(MainActivitySafeViewModel::class.java)
        lifecycle.addObserver(vmSafe)


        vmSafe.liveData.bind(this) {
            text_result.text = it
            Toast.makeText(this@MainActivity, "im done :)", Toast.LENGTH_LONG).show()

        }

        vm.liveData.bind(this) {
            text_result.text = it
            Toast.makeText(this@MainActivity, "im done :)", Toast.LENGTH_LONG).show()

        }

        button_vmsafe_nonleak.setOnClickListener {
            resetText()
            vmSafe.doSomethingWithCoroutineSafe()
            launchResultActivity()
        }

        button_vmsafe_leak.setOnClickListener {
            resetText()
            vmSafe.doSomethingWithCoroutine()
            launchResultActivity()
        }

        button_vm_nonleak.setOnClickListener {
            resetText()
            vm.doSomethingWithCoroutineSafe()
            launchResultActivity()
        }

        button_vm_leak.setOnClickListener {
            resetText()
            vm.doSomethingWithCoroutine()
            launchResultActivity()
        }

        button_leak.setOnClickListener {
            leakingOperation()
        }

        button_nonleak.setOnClickListener {
            nonLeakingOperation()
        }

        text_result.setOnClickListener {
            resetText()
        }
    }


    fun leakingOperation() {
        GlobalScope.launch(Dispatchers.Main) {
            Log.d("ViewModelCoroutine", "coroutine started...")
            delay(COROUTINE_EXEC_TIME)
            Log.d("ViewModelCoroutine", "coroutine finished...")
            text_result.text = "leaking"
            launchToast()
        }
    }

    fun nonLeakingOperation() {
        launch {
            Log.d("ViewModelCoroutine", "coroutine started...")
            delay(COROUTINE_EXEC_TIME)
            Log.d("ViewModelCoroutine", "coroutine finished...")
            text_result.text = "safe"
            launchToast()
        }
    }

    fun launchResultActivity() = startActivity(Intent(this, ResultActivity::class.java))

    fun launchToast() =  Toast.makeText(this@MainActivity, "im done :)", Toast.LENGTH_LONG).show()

    fun resetText() { text_result.text = "yet another title" }
}
