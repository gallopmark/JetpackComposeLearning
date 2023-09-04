package com.pony.jetpackcompose.home.composeui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.sp
import com.pony.jetpackcompose.R

/**
 * Created by pony on 2023/7/5
 * Description->
 */
@Composable
fun FragmentContainer(text: String) {
    Box(modifier = Modifier.fillMaxSize().background(color = Color.Cyan), contentAlignment = Alignment.Center) {
        Text(text = text, color = colorResource(id = R.color.color_333333), fontSize = 40.sp)
    }
}