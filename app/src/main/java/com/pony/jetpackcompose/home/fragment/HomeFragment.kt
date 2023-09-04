package com.pony.jetpackcompose.home.fragment

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.pony.lib.base.BaseComposeFragment
import com.pony.jetpackcompose.home.composeui.FragmentContainer
import com.pony.lib.composable.Banner

/**
 * Created by pony on 2023/7/5
 * Description->首页
 */
class HomeFragment : BaseComposeFragment() {

    @Composable
    override fun CreateContentComponent() {
        Column(modifier = Modifier.fillMaxSize()) {
            Banner(
                modifier = Modifier.height(200.dp),
                urls = listOf(
                    "https://www.2008php.com/2014_Website_appreciate/2014-08-06/201408061758169ZDPh9ZDPh.jpg",
                    "https://n.sinaimg.cn/sinacn09/100/w1600h900/20180620/79c9-heauxwa0679357.jpg",
                    "https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fsafe-img.xhscdn.com%2Fbw1%2F665aed2d-daab-4efc-b0e3-40838096df23%3FimageView2%2F2%2Fw%2F1080%2Fformat%2Fjpg&refer=http%3A%2F%2Fsafe-img.xhscdn.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=auto?sec=1691222474&t=b8c923bd3a57f59a7de17d8e485e2173",
                    "https://img0.baidu.com/it/u=4082838275,3521132179&fm=253&fmt=auto&app=138&f=JPEG?w=800&h=500"
                )
            )
            FragmentContainer("首页")
        }

    }

}