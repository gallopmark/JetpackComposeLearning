package com.pony.lib.composable

import android.os.Build
import androidx.annotation.DrawableRes
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.ImageLoader
import coil.compose.AsyncImage
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder
import com.pony.lib.R
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * Created by pony on 2023/7/6
 * Description->
 */
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun Banner(
    modifier: Modifier = Modifier,
    urls: List<String>?,
    delayMillis: Long = 3000L,
    @DrawableRes loadImage: Int = R.mipmap.placeholder,
    isLoop: Boolean = true,
    contentScale: ContentScale = ContentScale.Crop,
    onClick: (String) -> Unit = {},
) {
    Box(modifier = modifier.fillMaxSize()) {
        if (urls.isNullOrEmpty()) {
            Image(
                painter = painterResource(id = loadImage),
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
        } else {
            val virtualCount = Int.MAX_VALUE
            val actualCount = urls.size
            //初始图片下标
            val initialIndex = virtualCount / 2
            val pageState = rememberPagerState(initialPage = initialIndex)
            val coroutineScope = rememberCoroutineScope()
            LaunchedEffect(key1 = Unit, block = {
                coroutineScope.launch {
                    while (true) {
                        if (pageState.canScrollForward) {
                            delay(delayMillis)
                            if (!pageState.isScrollInProgress)
                                pageState.animateScrollToPage(pageState.currentPage + 1)
                        }
                    }
                }
            })
            HorizontalPager(
                pageCount = if (isLoop) virtualCount else actualCount,
                state = pageState,
                modifier = Modifier
                    .fillMaxSize()
            ) { index ->
                val actualIndex = if (isLoop) (index - initialIndex).floorMod(actualCount) else index
                AsyncImage(
                    model = urls[actualIndex],
                    contentDescription = null,
                    contentScale = contentScale,
                    modifier = Modifier
                        .fillMaxSize()
                        .clickable {
                            onClick.invoke(urls[actualIndex])
                        },
                    imageLoader = ImageLoader(LocalContext.current).newBuilder().components {
                        if (Build.VERSION.SDK_INT >= 28) {
                            add(ImageDecoderDecoder.Factory())
                        } else {
                            add(GifDecoder.Factory())
                        }
                    }.build()
                )
            }
            Row(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 10.dp)
                    .wrapContentSize(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                for (i in urls.indices) {
                    val currentPage = pageState.currentPage
                    val actualIndex = if (isLoop) (currentPage - initialIndex).floorMod(actualCount) else currentPage
                    val isSelect = actualIndex == i
                    Box(
                        modifier = Modifier
                            .clip(CircleShape)
                            .background(color = if (isSelect) Color(0xffea4c43) else Color(0xff999999))
                            .size(if (isSelect) 12.dp else 10.dp)
                    )
                    if (i != urls.lastIndex) {
                        Spacer(
                            modifier = Modifier
                                .height(0.dp)
                                .width(5.dp)
                        )
                    }
                }
            }
        }
    }
}


fun Int.floorMod(other: Int): Int = when (other) {
    0 -> this
    else -> this - floorDiv(other = other) * other
}