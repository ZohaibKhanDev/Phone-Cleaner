package com.example.phonecleaner.presentation.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.phonecleaner.domain.usecase.ResultState
import com.example.phonecleaner.presentation.viewmodel.MainViewModel
import org.koin.compose.koinInject

@Composable
fun HomeScreen() {
    val viewModel: MainViewModel = koinInject()
    val state by viewModel.allStorageCleaner.collectAsState()
    var isLoading by remember {
        mutableStateOf(false)
    }
    var storageData by remember {
        mutableStateOf<Boolean?>(null)
    }
    when (state) {
        is ResultState.Error -> {
            isLoading = false
            val error = (state as ResultState.Error).error
            Text(text = error.toString())
        }

        ResultState.Loading -> {
            isLoading = true
        }

        is ResultState.Success -> {
            isLoading = false
            val success = (state as ResultState.Success).success
            storageData = success
        }
    }

    var score by remember { mutableStateOf(100) }
    var isAnimating by remember { mutableStateOf(true) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AnimatedShield(score, isAnimating) { isAnimating = false }

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = {
            score = (0..100).random()
            isAnimating = true
        }) {
            Text("Generate Score")
        }
    }
}