package io.github.arshcaria.kerberos

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.andrognito.patternlockview.PatternLockView
import com.andrognito.patternlockview.listener.PatternLockViewListener
import com.andrognito.patternlockview.utils.PatternLockUtils
import kotlinx.android.synthetic.main.activity_authentication.*
import org.jetbrains.anko.*

class AuthenticationActivity : AppCompatActivity() {

    private val TAG = AuthenticationActivity::class.java.simpleName

    private val savedPattern = "642"

    private val patternLockListener = object : PatternLockViewListener {
        override fun onStarted() {}

        override fun onCleared() {}

        override fun onProgress(progressPattern: MutableList<PatternLockView.Dot>?) {}

        override fun onComplete(pattern: MutableList<PatternLockView.Dot>?) {
            val currentPattern = PatternLockUtils.patternToString(patternLockView, pattern)

            if (savedPattern == currentPattern) {
                toast("Patterns match!")
                patternLockView.clearPattern()
                startActivity(intentFor<MainActivity>().singleTop())
                finish()
            } else {
                longToast("Patterns mismatch!")
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_authentication)

        patternLockView.addPatternLockListener(patternLockListener)

        if (BuildConfig.DEBUG) {
            startActivity(intentFor<MainActivity>().singleTop())
            finish()
        }
    }
}
