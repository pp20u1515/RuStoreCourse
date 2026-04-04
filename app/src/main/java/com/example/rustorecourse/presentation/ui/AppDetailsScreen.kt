package com.example.rustorecourse.presentation.ui

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.rustorecourse.R

@Composable
fun AppDetailsScreen(appId: String) {
    val viewModel: AppDetailsScreenViewModel = hiltViewModel()
    val appDetailsState = viewModel.appDetailsState.collectAsState().value

    val context = LocalContext.current
    val underDevelopmentText = stringResource(R.string.under_developement)

    var descriptionCollapsed by remember { mutableStateOf(false) }

    when (val state = appDetailsState) {
        is AppDetailsScreenViewModel.AppDetailsState.Loading -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }

        is AppDetailsScreenViewModel.AppDetailsState.Success -> {
            val app = state.app

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(vertical = 8.dp)
            ) {
                Spacer(Modifier.height(8.dp))

                AppDetailsHeader(
                    app = app,
                    modifier = Modifier.padding(horizontal = 16.dp),
                )

                Spacer(Modifier.height(16.dp))

                InstallButton(
                    onClick = {
                        Toast.makeText(context, underDevelopmentText, Toast.LENGTH_SHORT).show()
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                )

                Spacer(Modifier.height(12.dp))

                ScreenshotsList(
                    screenshotUrlList = app.screenshotUrlList,
                    contentPadding = PaddingValues(horizontal = 16.dp),
                )

                Spacer(Modifier.height(12.dp))

                AppDescription(
                    description = app.description,
                    collapsed = descriptionCollapsed,
                    onReadMoreClick = {
                        descriptionCollapsed = true
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                )

                Spacer(Modifier.height(12.dp))

                HorizontalDivider(
                    modifier = Modifier.padding(horizontal = 16.dp),
                    color = MaterialTheme.colorScheme.outlineVariant,
                )

                Spacer(Modifier.height(12.dp))

                Developer(
                    name = app.developer,
                    onClick = {
                        Toast.makeText(context, underDevelopmentText, Toast.LENGTH_SHORT).show()
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 16.dp),
                )
            }
        }

        is AppDetailsScreenViewModel.AppDetailsState.Error -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Ошибка загрузки",
                        style = MaterialTheme.typography.headlineSmall,
                        color = MaterialTheme.colorScheme.error
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = state.message,
                        modifier = Modifier.padding(16.dp)
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Button(
                        onClick = { viewModel.loadApp(appId) }
                    ) {
                        Text("Повторить")
                    }
                }
            }
        }
    }
}