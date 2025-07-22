package com.example.lifetrack

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
//import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import com.example.lifetrack.ui.navigation.AppNavigation
import com.example.lifetrack.ui.theme.LifeTrackTheme
import com.example.lifetrack.model.network.SyncEngine
import kotlinx.coroutines.launch

//@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val syncEngine by lazy { SyncEngine.createDefault() }

    @RequiresApi(Build.VERSION_CODES.S)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val scope = CoroutineScope(SupervisorJob() + Dispatchers.Main)
        scope.launch {
            syncEngine.startSync()
        }
        setContent {
            LifeTrackTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    AppNavigation(scope = scope)
                }
            }
        }
    }

    override fun onDestroy() {
        syncEngine.stopSync()
        super.onDestroy()
    }
}
