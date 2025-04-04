package com.example.midterm22b1num0027.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.midterm22b1num0027.data.Word
import com.example.midterm22b1num0027.datastore.DisplayMode
import com.example.midterm22b1num0027.datastore.DisplaySettingsRepository
import com.example.midterm22b1num0027.viewmodel.WordViewModel

sealed class Screen(val route: String) {
    object Main : Screen("main")
    object Detail : Screen("detail")
    object Settings : Screen("settings")
}

@Composable
fun AppNavigation(
    wordViewModel: WordViewModel,
    displaySettingsRepository: DisplaySettingsRepository
) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Screen.Main.route) {
        composable(Screen.Main.route) {
            var currentIndex = remember {mutableStateOf(0)}
            val words = wordViewModel.words.collectAsState().value
            val displayMode = displaySettingsRepository.displayModeFlow.collectAsState(initial = DisplayMode.BOTH).value

            MainScreen(
                words = words,
                currentIndex = currentIndex.value,
                displayMode = displayMode,
                onPrevious = {
                    if (words.isNotEmpty())
                    {
                        currentIndex.value = if (currentIndex.value>0) currentIndex.value-1 else words.size-1
                    }
                },
                onNext = {
                    if (words.isNotEmpty())
                    {
                        currentIndex.value = (currentIndex.value+1)%words.size
                    }
                },
                onAdd = {
                    wordViewModel.editingWord = null
                    navController.navigate(Screen.Detail.route)
                },
                onEdit = {word ->
                    wordViewModel.editingWord = word
                    navController.navigate(Screen.Detail.route)
                },
                onDelete = {word -> wordViewModel.deleteWord(word)},
                onSettings = {navController.navigate(Screen.Settings.route)}
            )
        }
        composable(Screen.Detail.route) {
            DetailScreen(
                word = wordViewModel.editingWord,
                onSave = { english, mongolian ->
                    val editingWord = wordViewModel.editingWord
                    if (editingWord == null)
                    {
                        wordViewModel.addWord(Word(english = english, mongolian = mongolian))
                    } else
                    {
                        wordViewModel.updateWord(editingWord.copy(english = english, mongolian = mongolian))
                    }
                    wordViewModel.editingWord = null
                    navController.navigate(Screen.Main.route) {
                        popUpTo(Screen.Main.route) { inclusive = true }
                    }
                },
                onCancel = {
                    wordViewModel.editingWord = null
                    navController.navigate(Screen.Main.route)
                }
            )
        }
        composable(Screen.Settings.route) {
            SettingsScreen(
                displaySettingsRepository = displaySettingsRepository,
                onNavigateBack = { navController.navigate(Screen.Main.route) }
            )
        }
    }
}
