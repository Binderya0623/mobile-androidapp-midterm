package com.example.midterm22b1num0027

import android.Manifest
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.ViewModelProvider
import com.example.midterm22b1num0027.datastore.DisplaySettingsRepository
import com.example.midterm22b1num0027.repository.WordRepository
import com.example.midterm22b1num0027.ui.AppNavigation
import com.example.midterm22b1num0027.ui.theme.midterm22b1num0027Theme
import com.example.midterm22b1num0027.viewmodel.WordViewModel
import com.example.midterm22b1num0027.worker.*

class MainActivity : ComponentActivity() {
    private lateinit var wordViewModel: WordViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("MainActivity", "App started - Checking notification permissions")
        /************************ NOTIF PERMISSION ************************/
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            requestPermission()
        }
        /************************ VIEW MODEL ************************/
        val wordRepository = WordRepository(applicationContext)

        wordViewModel = ViewModelProvider(
            this, object : ViewModelProvider.Factory {
                override fun <T : androidx.lifecycle.ViewModel> create(modelClass: Class<T>): T {
                    @Suppress("UNCHECKED_CAST")
                    return WordViewModel(wordRepository) as T
                }
            }
        )[WordViewModel::class.java]

        val displaySettingsRepository = DisplaySettingsRepository(applicationContext)
        setContent {
            midterm22b1num0027Theme {
                AppNavigation(
                    wordViewModel = wordViewModel,
                    displaySettingsRepository = displaySettingsRepository
                )
            }
        }

        val hhe = LifecycleEventObserver {_, event ->
            when (event) {
                Lifecycle.Event.ON_STOP -> {scheduleReminder(this)}
                Lifecycle.Event.ON_RESUME -> {cancelReminder(this)}
                else -> {}
            }}

        lifecycle.addObserver(hhe)
    }

    /************************ UTILS ************************/
    private fun requestPermission() {
        val requestPermissionLauncher =
            registerForActivityResult(ActivityResultContracts.RequestPermission()) {x ->
                if (x) {Log.d("midterm22b1num0027:MainActivity", "Notification permission: OK")}
                else {Log.w("midterm22b1num0027:MainActivity", "Notification permission: DENIED")}
            }
        requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
    }
}