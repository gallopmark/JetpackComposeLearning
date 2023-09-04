package com.pony.lib.base

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.annotation.DrawableRes
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.view.WindowCompat
import com.google.accompanist.systemuicontroller.rememberSystemUiController

/**
 *Created by pony on 2022/9/14
 *Description->
 */
abstract class BaseComposeActivity : AppCompatActivity() {

    private var titleState by mutableStateOf(true)
    private var titleTextState by mutableStateOf("")
    private var navigateIcon by mutableStateOf(com.pony.lib.R.drawable.ic_arrow_back_24)
    private var onNavigateOnClickAction: (() -> Unit)? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContent {
            CreateContentComponent(savedInstanceState)
        }
    }

    open fun supportTitleComponent() = true

    open fun setTopAppbarTitle(titleText: String?) {
        titleTextState = titleText ?: ""
    }

    open fun setNavigateIcon(@DrawableRes id: Int, onClickAction: (() -> Unit)? = null) {
        navigateIcon = id
        onNavigateOnClickAction = onClickAction
    }

    @Composable
    open fun CreateContentComponent(savedInstanceState: Bundle?) {
        val systemUiController = rememberSystemUiController()
        LaunchedEffect(key1 = Unit, block = {
            systemUiController.setStatusBarColor(Color.Transparent, darkIcons = true)
        })
        Column(modifier = Modifier.fillMaxSize()) {
            AppTitleComponent()
            CreateContent(savedInstanceState)
        }
    }

    @Composable
    fun AppTitleComponent() {
        titleState = supportTitleComponent()
        if (titleState) {
            TopAppBar(
                title = {
                    TitleComponent()
                }, modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                navigationIcon = {
                    NavigateComponent()
                }
            )
        }
    }

    @Composable
    fun TitleComponent() {
        Text(text = titleTextState, fontSize = 20.sp)
    }

    @Composable
    fun NavigateComponent() {
        IconButton(onClick = {
            onNavigateOnClickAction?.invoke() ?: onBackPressedDispatcher.onBackPressed()
        }) {
            Image(painter = painterResource(id = navigateIcon), contentDescription = "back", colorFilter = ColorFilter.tint(color = Color.White))
        }
    }

    @Composable
    abstract fun CreateContent(savedInstanceState: Bundle?)
}