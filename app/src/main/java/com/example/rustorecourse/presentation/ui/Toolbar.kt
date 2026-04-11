package com.example.rustorecourse.presentation.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.rustorecourse.R

@Composable
fun Toolbar(
    appId: String,
    onLogoClick: () -> Unit,
    onHeartClick: () -> Unit,
    modifier: Modifier = Modifier
){
    val viewModel: ToolbarViewModel = hiltViewModel()
    val showWishListIcon = appId.isNotEmpty()

    LaunchedEffect(appId) {
        if (showWishListIcon){
            viewModel.setAppId(appId)
        }
    }

    val wishlistState by viewModel.wishlistState.collectAsStateWithLifecycle()


    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .fillMaxWidth()
            .background(Color(0xFF0077FF))
            .size(60.dp)
            .padding(16.dp)
    ){
        Row(
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically,
            modifier = modifier.clickable{ onLogoClick() }
        ) {
            Icon(
                painter = painterResource(id = R.drawable.rustore_icon),
                contentDescription = null,
                tint = Color.Unspecified,
                modifier = modifier.size(24.dp)
            )
            Text(
                text = "RuStore",
                color = Color.White
            )
        }
        Row(
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.CenterVertically,
            modifier = modifier.padding()
        ){
            if (showWishListIcon){
                when (wishlistState) {
                    is ToolbarViewModel.WishlistState.Loading -> {
                        Box(
                            modifier = Modifier.size(24.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator(
                                modifier = Modifier.size(16.dp),
                                color = Color.White,
                                strokeWidth = 2.dp
                            )
                        }
                    }
                    is ToolbarViewModel.WishlistState.Success -> {
                        val isInWishlist = (wishlistState as ToolbarViewModel.WishlistState.Success).isInWishlist
                        Icon(
                            painter = painterResource(id = R.drawable.outline_bookmark_heart_24),
                            contentDescription = if (isInWishlist) "Удалить из списка желаний" else "Добавить в список желаний",
                            tint = if (isInWishlist) Color.Red else Color.White,
                            modifier = Modifier
                                .size(24.dp)
                                .clickable {
                                    viewModel.toggleWishlist()
                                    onHeartClick()
                                }
                        )
                    }
                    is ToolbarViewModel.WishlistState.Error -> {
                        Icon(
                            painter = painterResource(id = R.drawable.outline_bookmark_heart_24),
                            contentDescription = "Ошибка загрузки",
                            tint = Color.Yellow,
                            modifier = Modifier
                                .size(24.dp)
                                .clickable {
                                    viewModel.setAppId(appId)
                                }
                        )
                    }
                    is ToolbarViewModel.WishlistState.Initial -> {
                        Icon(
                            painter = painterResource(id = R.drawable.outline_bookmark_heart_24),
                            contentDescription = "Wishlist",
                            tint = Color.White.copy(alpha = 0.5f),
                            modifier = Modifier.size(24.dp)
                        )
                    }
                }
                Spacer(Modifier.width(15.dp))
            }
            Icon(
                painter = painterResource(id = R.drawable.grid_view_24dp),
                contentDescription = null,
                tint = Color.White
            )
        }
    }
}