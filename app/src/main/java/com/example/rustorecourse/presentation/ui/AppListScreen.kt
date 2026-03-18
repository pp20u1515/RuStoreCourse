package com.example.rustorecourse.presentation.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
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
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.getValue
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.collectAsState
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
import androidx.hilt.navigation.compose.hiltViewModel
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
fun MainScreenContent(
    onItemClick: (AppDetailsItem) -> Unit
){
    val viewModel: AppListScreenViewModel = hiltViewModel()
    val appListScreenState by viewModel.appListScreenState.collectAsState()

    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

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
                    scope.launch {
                        snackbarHostState.showSnackbar(
                            message = "Вы нажали на логотип RuStore",
                            duration = SnackbarDuration.Short
                        )
                    }
                }
            )

            when (appListScreenState) {
                is AppListScreenViewModel.AppListScreenState.Loading -> {
                    LoadingScreen()
                }
                is AppListScreenViewModel.AppListScreenState.Success -> {
                    AppListContent(
                        apps = (appListScreenState as AppListScreenViewModel.AppListScreenState.Success).listOfApps,
                        onItemClick = onItemClick
                    )
                }
                is AppListScreenViewModel.AppListScreenState.Error -> {
                    ErrorScreen(
                        message = (appListScreenState as AppListScreenViewModel.AppListScreenState.Error).message
                    )
                }
            }
        }
    }
}

@Composable
fun AppListContent(
    apps: List<AppDetailsItem>,
    onItemClick: (AppDetailsItem) -> Unit
) {
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
            modifier = Modifier.fillMaxSize()
        ) {
            items(apps, key = { it.appName }) { app ->
                AppDetails(
                    modifier = Modifier.fillMaxWidth(),
                    appDetails = app,
                    onItemClick = onItemClick
                )
            }
        }
    }
}

@Composable
fun LoadingScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}

@Composable
fun ErrorScreen(
    message: String
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = message,
                color = Color.Red,
                modifier = Modifier.padding(16.dp)
            )
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