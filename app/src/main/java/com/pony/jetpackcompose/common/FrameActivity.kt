package com.pony.jetpackcompose.common

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.FrameLayout
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import androidx.fragment.app.Fragment
import com.pony.lib.base.BaseComposeActivity
import java.lang.ref.WeakReference

/**
 *Created by pony on 2022/9/14
 *Description->
 */
class FrameActivity : BaseComposeActivity() {

    companion object {
        private const val CONTAINER_ID = 0x100123
        private const val FRAGMENT_CANONICAL_NAME = "fragment_canonical_name"
        private const val TITLE = "frame_title"
        private const val SHOW_TITLE_BAR = "frame_show_title_bar"
        private const val FRAGMENT_TAG = "frame"

        fun open(context: Context, fragmentCanonicalName: String?, title: String? = "", showTitleBar: Boolean = true) {
            val intent = Intent(context, FrameActivity::class.java).apply {
                putExtra(FRAGMENT_CANONICAL_NAME, fragmentCanonicalName)
                putExtra(TITLE, title)
                putExtra(SHOW_TITLE_BAR, showTitleBar)
            }
            context.startActivity(intent)
        }
    }

    private var mFragmentCache: WeakReference<Fragment>? = null

    override fun supportTitleComponent(): Boolean = intent.getBooleanExtra(SHOW_TITLE_BAR, true)

    @Composable
    override fun CreateContent(savedInstanceState: Bundle?) {
        setTopAppbarTitle(intent.getStringExtra(TITLE))
        AndroidView(factory = {
            FrameLayout(it).apply { id = CONTAINER_ID }
        }, modifier = Modifier.fillMaxSize(), update = { container ->
            var fragment: Fragment? = null
            savedInstanceState?.let {
                fragment = supportFragmentManager.getFragment(it, FRAGMENT_TAG)
            }
            if (fragment == null) {
                fragment = getFromIntent()
            }
            fragment?.let {
                supportFragmentManager.beginTransaction()
                    .replace(container.id, it)
                    .commitAllowingStateLoss()
                mFragmentCache = WeakReference(it)
            }
        })
    }

    private fun getFromIntent(): Fragment? {
        val fragmentName: String? = intent.getStringExtra(FRAGMENT_CANONICAL_NAME)
        if (fragmentName.isNullOrEmpty()) return null
        return try {
            val clz = Class.forName(fragmentName)
            val fragment = clz.newInstance() as Fragment
            fragment.arguments = intent.extras
            fragment
        } catch (e: Exception) {
            null
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        mFragmentCache?.get()?.let {
            supportFragmentManager.putFragment(outState, FRAGMENT_TAG, it)
        }
    }
}