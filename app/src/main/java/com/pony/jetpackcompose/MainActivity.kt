package com.pony.jetpackcompose

import android.os.Bundle
import android.widget.FrameLayout
import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.material.Divider
import androidx.compose.material.TabRow
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.fragment.app.Fragment
import com.pony.jetpackcompose.home.fragment.*
import com.pony.lib.base.BaseComposeActivity

class MainActivity : BaseComposeActivity() {

    companion object {
        private const val CONTAINER_ID = 0x002023
    }

    private val tabs = arrayOf("首页", "直播", "智投", "自选", "我的")

    private val tabSelectIcons = arrayOf(
        R.mipmap.home_tab_home_pressed,
        R.mipmap.home_tab_circle_pressed,
        R.mipmap.home_tab_course_pressed,
        R.mipmap.home_tab_quote_pressed,
        R.mipmap.home_tab_mine_pressed
    )
    private val tabUnselectIcons = arrayOf(
        R.mipmap.home_tab_home_normal,
        R.mipmap.home_tab_circle_normal,
        R.mipmap.home_tab_course_normal,
        R.mipmap.home_tab_quote_normal,
        R.mipmap.home_tab_mine_normal
    )

    private var currentShowingFragment: Fragment? = null

    override fun supportTitleComponent(): Boolean = false

    private var selectIndex by mutableStateOf(0)

    @Composable
    override fun CreateContent(savedInstanceState: Bundle?) {
        Column(modifier = Modifier.fillMaxSize()) {
            AndroidView(
                factory = {
                    FrameLayout(this@MainActivity).apply {
                        id = CONTAINER_ID
                    }
                }, modifier = Modifier
                    .fillMaxWidth()
                    .weight(1.0f)
            )
            TabRow(
                selectedTabIndex = selectIndex,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                backgroundColor = Color.White,
                indicator = {

                },
                divider = {
                    Divider(color = Color.Transparent)
                },
                tabs = {
                    tabs.forEachIndexed { index, _ ->
                        HomeTabComponent(selectIndex = selectIndex, index = index)
                    }
                }
            )
        }
        LaunchedEffect(key1 = selectIndex, block = {
            when (selectIndex) {
                0 -> {
                    switchFragment(HomeFragment::class.java.name)
                }
                1 -> {
                    switchFragment(LiveFragment::class.java.name)
                }
                2 -> {
                    switchFragment(CourseFragment::class.java.name)
                }
                3 -> {
                    switchFragment(QuoteFragment::class.java.name)
                }
                4 -> {
                    switchFragment(MineFragment::class.java.name)
                }
            }
        })
    }

    @Composable
    private fun HomeTabComponent(selectIndex: Int, index: Int) {
        val isSelect = selectIndex == index
        Column(
            modifier = Modifier
                .fillMaxSize()
                .pointerInput(Unit) {
                    detectTapGestures {
                        this@MainActivity.selectIndex = index
                    }
                }, verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(painter = painterResource(id = if (isSelect) tabSelectIcons[index] else tabUnselectIcons[index]),
                contentDescription = tabs[index], modifier = Modifier.size(25.dp))
            Text(text = tabs[index], color = colorResource(id = if (isSelect) R.color.color_ea4c43 else R.color.color_333333), fontSize = 14.sp)
        }
    }

    private fun switchFragment(fragmentName: String) {
        val fm = supportFragmentManager
        val transaction = fm.beginTransaction()
        currentShowingFragment?.let {
            transaction.hide(it)
        }
        var targetFragment = fm.findFragmentByTag(fragmentName)
        if (targetFragment != null) {
            transaction.show(targetFragment)
        } else {
            targetFragment = fm.fragmentFactory.instantiate(classLoader, fragmentName)
            transaction.add(CONTAINER_ID, targetFragment, fragmentName)
        }
        transaction.commitAllowingStateLoss()
        currentShowingFragment = targetFragment
    }
}

