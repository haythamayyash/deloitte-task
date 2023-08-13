package com.example.deloittetask.presentation.splash

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.example.deloittetask.MainActivity
import com.example.deloittetask.R
import com.example.deloittetask.presentation.authentication.UserAuthActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RoutingActivity : AppCompatActivity() {

    private val viewModel by viewModels<RoutingViewModel>()

    private val navigateToNextScreenObserver: Observer<RoutingViewModel.Screen?> by lazy {
        Observer { state ->
            navigateToNextScreen(state)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        determineNextScreen()
        observeChanges()
    }

    private fun determineNextScreen() {
        viewModel.openNextScreenAfterDelay()
    }

    private fun observeChanges() {
        viewModel.navigateToNextScreen.observe(this, navigateToNextScreenObserver)
    }

    private fun navigateToNextScreen(state: RoutingViewModel.Screen?) {
        when (state) {
            RoutingViewModel.Screen.Home -> {
                finish()
                MainActivity.startActivity(this)
            }

            RoutingViewModel.Screen.Authentication -> {
                finish()
                UserAuthActivity.startActivity(this)
            }

            else -> {
                //Nothing to do here
            }
        }
    }

    companion object {
        fun startActivity(context: Context) {
            val intent = Intent(context, RoutingActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            context.startActivity(intent)
        }
    }
}