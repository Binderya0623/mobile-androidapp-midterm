package com.example.midterm22b1num0027.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.midterm22b1num0027.data.Word

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(
    word: Word? = null,
    onSave: (String, String) -> Unit,
    onCancel: () -> Unit
) {
    var english by remember {mutableStateOf(word?.english ?: "")}
    var mongolian by remember {mutableStateOf(word?.mongolian ?: "")}
    Scaffold(
        topBar = {
            TopAppBar(title = { Text(if (word == null) "Add Word" else "Edit Word") })
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier.fillMaxSize().padding(paddingValues).padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            OutlinedTextField(
                value = english,
                onValueChange = {english = it},
                label = { Text("English Word") },
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = mongolian,
                onValueChange = {mongolian = it},
                label = { Text("Mongolian Word") },
                modifier = Modifier.fillMaxWidth()
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Button(onClick = {onSave(english, mongolian)}) {Text("Save")}
                Button(onClick = onCancel) {Text("Cancel")}
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewDetailScreen() {
    DetailScreen(
        word = Word(english = "Hello", mongolian = "Сайн уу"),
        onSave = { english, mongolian ->
            println("Saved: $english - $mongolian")
        },
        onCancel = {println("Cancelled")}
    )
}

@Preview(showBackground = true, name = "Empty")
@Composable
fun PreviewEmptyDetailScreen() {
    DetailScreen(
        word = null,
        onSave = { english, mongolian ->
            println("Saved: $english - $mongolian")
        },
        onCancel = { println("Cancelled") }
    )
}

@Preview(showBackground = true, name = "Long Text")
@Composable
fun PreviewLongTextDetailScreen() {
    DetailScreen(
        word = Word(
            english = "Supercalifragilisticexpialidocious",
            mongolian = "Маш урт үг"
        ),
        onSave = { english, mongolian ->
            println("Saved: $english - $mongolian")
        },
        onCancel = { println("Cancelled") }
    )
}