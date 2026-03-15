package com.example.rustorecourse.presentation.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.rustorecourse.R

@Preview
@Composable
fun Toolbar(modifier: Modifier = Modifier){
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
            verticalAlignment = Alignment.CenterVertically
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
        Icon(
            painter = painterResource(id = R.drawable.grid_view_24dp),
            contentDescription = null,
            tint = Color.White
        )
    }
}