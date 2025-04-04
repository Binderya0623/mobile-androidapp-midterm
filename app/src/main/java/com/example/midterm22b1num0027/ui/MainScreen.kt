package com.example.midterm22b1num0027.ui

import android.content.res.Configuration
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.midterm22b1num0027.data.Word
import com.example.midterm22b1num0027.datastore.DisplayMode

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun MainScreen(
    words: List<Word>,
    currentIndex: Int,
    displayMode: DisplayMode,
    onPrevious: () -> Unit,
    onNext: () -> Unit,
    onAdd: () -> Unit,
    onEdit: (Word) -> Unit,
    onDelete: (Word) -> Unit,
    onSettings: () -> Unit
) {
    val currentWord = if (words.isNotEmpty()) words[currentIndex] else null
    var showMongolian by remember {mutableStateOf(false)}
    var showForeign by remember {mutableStateOf(false)}
    var showDeleteDialog by remember {mutableStateOf(false)}

    LaunchedEffect(currentWord, displayMode) {
        showMongolian = false
        showForeign = false
    }

    if (showDeleteDialog && currentWord != null)
    {
        AlertDialog(
            onDismissRequest = { showDeleteDialog = false },
            title = {Text("Delete Word")},
            text = {Text("Are you sure you want to delete this word?")},
            confirmButton = {
                TextButton(onClick = {
                    onDelete(currentWord)
                    showDeleteDialog=false
                }) {Text("Yes")}
            },
            dismissButton = {
                TextButton(onClick = {showDeleteDialog = false}) {Text("No")}
            }
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {Text("Word Memorizer")},
                actions = {
                    IconButton(onClick = onSettings) {
                        Icon(
                            imageVector = Icons.Default.Settings,
                            contentDescription = "Settings"
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        val configuration = LocalConfiguration.current
        val isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE

        /************************ LANDSCAPE ************************/
        if (isLandscape)
        {
            Box(
                modifier = Modifier.fillMaxSize().padding(paddingValues).padding(16.dp)
            ) {
                Column(
                    modifier = Modifier.align(Alignment.Center),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    if (currentWord!=null) {
                        when (displayMode) {

                            DisplayMode.BOTH -> {
                                Text(
                                    text = currentWord.english,
                                    style = MaterialTheme.typography.headlineMedium,
                                    textAlign = TextAlign.Center,
                                    modifier = Modifier.fillMaxWidth().combinedClickable(
                                            onClick = {},
                                            onLongClick = {onEdit(currentWord)}
                                        )
                                )
                                Spacer(modifier = Modifier.height(16.dp))
                                Text(
                                    text = currentWord.mongolian,
                                    style = MaterialTheme.typography.headlineSmall,
                                    textAlign = TextAlign.Center,
                                    modifier = Modifier.fillMaxWidth().combinedClickable(
                                            onClick = {},
                                            onLongClick = {onEdit(currentWord)}
                                        )
                                )
                            }

                            DisplayMode.FOREIGN -> {
                                Text(
                                    text = currentWord.english,
                                    style = MaterialTheme.typography.headlineMedium,
                                    textAlign = TextAlign.Center,
                                    modifier = Modifier.fillMaxWidth().combinedClickable(
                                            onClick = {},
                                            onLongClick = {onEdit(currentWord)}
                                        )
                                )
                                Spacer(modifier = Modifier.height(16.dp))
                                if (showMongolian) {
                                    Text(
                                        text = currentWord.mongolian,
                                        style = MaterialTheme.typography.headlineSmall,
                                        textAlign = TextAlign.Center,
                                        modifier = Modifier.fillMaxWidth().combinedClickable(
                                                onClick = {},
                                                onLongClick = {onEdit(currentWord)}
                                            )
                                    )
                                } else {
                                    Text(
                                        text = "Tap to reveal",
                                        style = MaterialTheme.typography.bodyLarge,
                                        textAlign = TextAlign.Center,
                                        modifier = Modifier.fillMaxWidth().combinedClickable(
                                                onClick = {showMongolian = true},
                                                onLongClick = {onEdit(currentWord)}
                                            )
                                    )
                                }
                            }


                            DisplayMode.MONGOLIAN -> {
                                if (showForeign) {
                                    Text(
                                        text = currentWord.english,
                                        style = MaterialTheme.typography.headlineMedium,
                                        textAlign = TextAlign.Center,
                                        modifier = Modifier.fillMaxWidth().combinedClickable(
                                                onClick = {},
                                                onLongClick = {onEdit(currentWord)}
                                            )
                                    )
                                } else {
                                    Text(
                                        text = "Tap to reveal",
                                        style = MaterialTheme.typography.bodyLarge,
                                        textAlign = TextAlign.Center,
                                        modifier = Modifier.fillMaxWidth().combinedClickable(
                                                onClick = { showForeign = true },
                                                onLongClick = {onEdit(currentWord)}
                                            )
                                    )
                                }
                                Spacer(modifier = Modifier.height(16.dp))
                                Text(
                                    text = currentWord.mongolian,
                                    style = MaterialTheme.typography.headlineSmall,
                                    textAlign = TextAlign.Center,
                                    modifier = Modifier.fillMaxWidth().combinedClickable(
                                            onClick = {},
                                            onLongClick = {onEdit(currentWord)}
                                        )
                                )
                            }
                        }
                    } else {
                        Text(
                            "No words available. Please add a word :3",
                            style = MaterialTheme.typography.bodyLarge,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }

                //buttons - 1 column
                Column(
                    modifier = Modifier.fillMaxHeight().align(Alignment.CenterEnd).padding(8.dp),
                    verticalArrangement = Arrangement.Center
                ) {
                    OutlinedButton(onClick = onPrevious, enabled = words.isNotEmpty()) {Text("Previous")}
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedButton(onClick = onAdd) {Text("Add")}
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedButton(
                        onClick = { currentWord?.let { onEdit(it) } },
                        enabled = currentWord != null
                    ) {Text("Edit")}
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedButton(
                        onClick = { showDeleteDialog = true },
                        enabled = currentWord != null
                    ) {Text("Delete")}
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedButton(onClick = onNext, enabled = words.isNotEmpty()) {Text("Next")}
                }
            }
        }
        /************************ PORTRAIT ************************/
        else
        {
            Column(
                modifier = Modifier.fillMaxSize().padding(paddingValues).padding(16.dp),
                verticalArrangement = Arrangement.SpaceBetween,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                if (currentWord != null) {
                    when (displayMode) {
                        DisplayMode.BOTH -> {
                            Text(
                                text = currentWord.english,
                                style = MaterialTheme.typography.headlineMedium,
                                textAlign = TextAlign.Center,
                                modifier = Modifier.fillMaxWidth().combinedClickable(
                                        onClick = {},
                                        onLongClick = {onEdit(currentWord)}
                                    )
                            )
                            Spacer(modifier = Modifier.height(16.dp))
                            Text(
                                text = currentWord.mongolian,
                                style = MaterialTheme.typography.headlineSmall,
                                textAlign = TextAlign.Center,
                                modifier = Modifier.fillMaxWidth().combinedClickable(
                                        onClick = {},
                                        onLongClick = {onEdit(currentWord)}
                                    )
                            )
                        }
                        DisplayMode.FOREIGN -> {
                            Text(
                                text = currentWord.english,
                                style = MaterialTheme.typography.headlineMedium,
                                textAlign = TextAlign.Center,
                                modifier = Modifier.fillMaxWidth().combinedClickable(
                                        onClick = {},
                                        onLongClick = {onEdit(currentWord)}
                                    )
                            )
                            Spacer(modifier = Modifier.height(16.dp))
                            if (showMongolian) {
                                Text(
                                    text = currentWord.mongolian,
                                    style = MaterialTheme.typography.headlineSmall,
                                    textAlign = TextAlign.Center,
                                    modifier = Modifier.fillMaxWidth().combinedClickable(
                                            onClick = {},
                                            onLongClick = {onEdit(currentWord)}
                                        )
                                )
                            } else {
                                Text(
                                    text = "Tap to reveal",
                                    style = MaterialTheme.typography.bodyLarge,
                                    textAlign = TextAlign.Center,
                                    modifier = Modifier.fillMaxWidth().combinedClickable(
                                            onClick = {showMongolian = true},
                                            onLongClick = {onEdit(currentWord)}
                                        )
                                )
                            }
                        }
                        DisplayMode.MONGOLIAN -> {
                            if (showForeign) {
                                Text(
                                    text = currentWord.english,
                                    style = MaterialTheme.typography.headlineMedium,
                                    textAlign = TextAlign.Center,
                                    modifier = Modifier.fillMaxWidth().combinedClickable(
                                            onClick = {},
                                            onLongClick = {onEdit(currentWord)}
                                        )
                                )
                            } else {
                                Text(
                                    text = "Tap to reveal",
                                    style = MaterialTheme.typography.bodyLarge,
                                    textAlign = TextAlign.Center,
                                    modifier = Modifier.fillMaxWidth().combinedClickable(
                                            onClick = {showForeign = true},
                                            onLongClick = {onEdit(currentWord)}
                                        )
                                )
                            }
                            Spacer(modifier = Modifier.height(16.dp))
                            Text(
                                text = currentWord.mongolian,
                                style = MaterialTheme.typography.headlineSmall,
                                textAlign = TextAlign.Center,
                                modifier = Modifier.fillMaxWidth().combinedClickable(
                                        onClick = {},
                                        onLongClick = {onEdit(currentWord)}
                                    )
                            )
                        }
                    }
                } else {
                    Text(
                        "No words available. Please add a word :3",
                        style = MaterialTheme.typography.bodyLarge,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
                //buttons - 2 row
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        OutlinedButton(onClick = onAdd) {Text("Add")}
                        OutlinedButton(
                            onClick = {currentWord?.let { onEdit(it) }},
                            enabled = currentWord != null
                        ) {Text("Edit")}
                        OutlinedButton(
                            onClick = {showDeleteDialog = true },
                            enabled = currentWord != null
                        ) {Text("Delete")}
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        OutlinedButton(onClick = onPrevious, enabled = words.isNotEmpty()) {Text("Previous")}
                        OutlinedButton(onClick = onNext, enabled = words.isNotEmpty()) {Text("Next")}
                    }
                }
            }
        }
    }
}


@Preview(showBackground = true, name = "No Words")
@Composable
fun PreviewMainScreenEmpty() {
    MainScreen(
        words = emptyList(),
        currentIndex = 0,
        displayMode = DisplayMode.BOTH,
        onPrevious = {},
        onNext = {},
        onAdd = { println("Add Word Clicked") },
        onEdit = { println("Edit Clicked: $it") },
        onDelete = { println("Delete Clicked: $it") },
        onSettings = { println("Settings Clicked") }
    )
}

@Preview(showBackground = true, name = "Single Word")
@Composable
fun PreviewMainScreenSingleWord() {
    MainScreen(
        words = listOf(Word(english = "Hello", mongolian = "Сайн уу")),
        currentIndex = 0,
        displayMode = DisplayMode.BOTH,
        onPrevious = {},
        onNext = {},
        onAdd = { println("Add Word Clicked") },
        onEdit = { println("Edit Clicked: $it") },
        onDelete = { println("Delete Clicked: $it") },
        onSettings = { println("Settings Clicked") }
    )
}

@Preview(showBackground = true, name = "Multiple Words")
@Composable
fun PreviewMainScreenMultipleWords() {
    MainScreen(
        words = listOf(
            Word(english = "Apple", mongolian = "Алим"),
            Word(english = "Orange", mongolian = "Жүрж"),
            Word(english = "Banana", mongolian = "Банана")
        ),
        currentIndex = 1,
        displayMode = DisplayMode.FOREIGN,
        onPrevious = { println("Previous Clicked") },
        onNext = { println("Next Clicked") },
        onAdd = { println("Add Word Clicked") },
        onEdit = { println("Edit Clicked: $it") },
        onDelete = { println("Delete Clicked: $it") },
        onSettings = { println("Settings Clicked") }
    )
}

@Preview(showBackground = true, name = "Mongolian Display Mode")
@Composable
fun PreviewMainScreenMongolianMode() {
    MainScreen(
        words = listOf(
            Word(english = "Car", mongolian = "Машин"),
            Word(english = "Plane", mongolian = "Онгоц")
        ),
        currentIndex = 0,
        displayMode = DisplayMode.MONGOLIAN,
        onPrevious = {},
        onNext = {},
        onAdd = { println("Add Word Clicked") },
        onEdit = { println("Edit Clicked: $it") },
        onDelete = { println("Delete Clicked: $it") },
        onSettings = { println("Settings Clicked") }
    )
}