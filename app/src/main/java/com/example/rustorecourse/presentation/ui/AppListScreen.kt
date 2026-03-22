package com.example.rustorecourse.presentation.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.rustorecourse.domain.model.AppDetailsItem
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.getValue
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.launch

@Composable
fun AppDetails(
    modifier: Modifier = Modifier,
    appDetails: AppDetailsItem,
    onItemClick: (AppDetailsItem) -> Unit
){
    Row(
        modifier = modifier
            .background(color = Color.White)
            .fillMaxWidth()
            .clickable{ onItemClick(appDetails) },
        verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(appDetails.icon)
                .build(),
            contentDescription = null,
            modifier = Modifier
                .size(90.dp)
                .clip(RoundedCornerShape(8.dp)),
            onError = { println("Ошибка загрузки: ${it.result.throwable}") },
            contentScale = ContentScale.Crop
        )
        Column(modifier = modifier.padding(8.dp)) {
            Text(
                text = appDetails.appName,
                fontSize = 18.sp
            )
            Text(
                text = appDetails.description,
                fontSize = 16.sp
            )
            Text(
                text = appDetails.category,
                fontSize = 14.sp,
                color = Color.Gray
            )
        }
    }
}

@Composable
fun MainScreenContent(onItemClick: () -> Unit){
    val viewModel: AppListScreenViewModel = viewModel()
    val apps by viewModel.apps

    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        viewModel.event.collect { event ->
            when (event) {
                is AppListScreenViewModel.UIEvent.ShowSnackbar -> {
                    scope.launch {
                        snackbarHostState.showSnackbar(
                            message = event.message,
                            duration = SnackbarDuration.Short
                        )
                    }
                }
            }
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(Color(0xFF0077FF))
        ) {
            Toolbar(
                onLogoClick = {
                    viewModel.onLogoClick()
                }
            )
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .clip(
                        RoundedCornerShape(
                            topStart = 20.dp,
                            topEnd = 20.dp,
                            bottomStart = 0.dp,
                            bottomEnd = 0.dp
                        )
                    )
                    .background(Color.White)
            ) {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                    items(apps, key = { it.appName }) { app ->
                        AppDetails(
                            modifier = Modifier
                                .fillMaxWidth(),
                            appDetails = app,
                            onItemClick = {
                                onItemClick()
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun AppListScreen(){
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "mainScreen"
    ) {
        composable("mainScreen") {
            MainScreenContent(
                onItemClick = {
                    navController.navigate("appDetailsScreen")
                }
            )
        }

        composable("appDetailsScreen") {
            AppDetailsScreen()
        }
    }
}