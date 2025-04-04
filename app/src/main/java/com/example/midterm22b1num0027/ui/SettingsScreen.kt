package com.example.midterm22b1num0027.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.midterm22b1num0027.datastore.DisplayMode
import com.example.midterm22b1num0027.datastore.DisplaySettingsRepository
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
                title = { Text("Settings") },
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
