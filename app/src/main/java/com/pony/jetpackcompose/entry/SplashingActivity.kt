package com.pony.jetpackcompose.entry

import android.content.Intent
import android.os.Bundle
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.pony.lib.base.BaseComposeActivity
import com.pony.jetpackcompose.MainActivity
import com.pony.jetpackcompose.R
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * Created by pony on 2023/7/5
 * Description->
 */
class SplashingActivity : BaseComposeActivity() {

    override fun supportTitleComponent(): Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycleScope.launch {
            repeatOnLifecycle(state = Lifecycle.State.RESUMED) {
                delay(3000)
                startMainPage()
            }
        }
    }

    private fun startMainPage() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }

    @Composable
    override fun CreateContent(savedInstanceState: Bundle?) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.BottomCenter) {
            Image(
                painter = painterResource(id = R.mipmap.bg_splash),
                contentDescription = "logo",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.FillHeight
            )
        }
    }
}