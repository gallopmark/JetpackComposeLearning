package com.pony.jetpackcompose.home.fragment

import androidx.compose.runtime.Composable
import com.pony.lib.base.BaseComposeFragment
import com.pony.jetpackcompose.home.composeui.FragmentContainer

/**
 * Created by pony on 2023/7/5
 * Description->
 */
class LiveFragment : BaseComposeFragment(){

    @Composable
    override fun CreateContentComponent() {
        FragmentContainer(text = "直播")
    }

}