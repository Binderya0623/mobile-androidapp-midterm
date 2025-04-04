package com.example.midterm22b1num0027.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.midterm22b1num0027.datastore.DisplayMode
import com.example.midterm22b1num0027.datastore.DisplaySettingsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    displaySettingsRepository: DisplaySettingsRepository,
    onNavigateBack: () -> Unit
) {
    val scope = rememberCoroutineScope()
    val displayModeFlow = displaySettingsRepository.displayModeFlow
    val displayMode by displayModeFlow.collectAsState(initial=DisplayMode.BOTH)
    Scaffold(
        topBar = {
            TopAppBar(
                title = {Text("Settings")},
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier.fillMaxSize().padding(paddingValues).padding(16.dp)
        ) {
            Text("Select Display Mode", style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.height(16.dp))
            DisplayMode.values().forEach { mode ->
                Row(
                    modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RadioButton(
                        selected = (displayMode==mode),
                        onClick = {scope.launch{displaySettingsRepository.setDisplayMode(mode)}}
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(text = mode.mode.replaceFirstChar {it.uppercase()})
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SettingsScreenPreview() {
    val displaySettingsRepository = DisplaySettingsRepository(LocalContext.current)
    SettingsScreen(
        displaySettingsRepository = displaySettingsRepository,
        onNavigateBack = {}
    )
}
