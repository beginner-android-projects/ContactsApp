package com.ryde.assignment.nyinyi.contactsapp.ui

import android.os.Bundle
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import com.ryde.assignment.nyinyi.contactsapp.R
import com.ryde.assignment.nyinyi.contactsapp.navigator.ApplicationNavigator
import com.ryde.assignment.nyinyi.contactsapp.navigator.Screens
import com.ryde.assignment.nyinyi.contactsapp.utils.makeStatusBarTransparent
import com.ryde.assignment.nyinyi.contactsapp.utils.setMarginTop
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var navigator: ApplicationNavigator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        makeStatusBarTransparent()
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main_container)) { _, insets ->
            findViewById<FrameLayout>(R.id.main_container).setMarginTop(insets.systemWindowInsetTop)
            insets.consumeSystemWindowInsets()
        }

        if (savedInstanceState == null) {
            navigator.navigateTo(Screens.CONTACT_LIST)
        }

    }

    override fun onBackPressed() {
        super.onBackPressed()

        if (supportFragmentManager.backStackEntryCount == 0) {
            finish()
        }
    }


}