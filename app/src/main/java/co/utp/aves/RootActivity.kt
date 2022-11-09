package co.utp.aves

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import co.utp.aves.base.BaseActivity
import co.utp.aves.databinding.ActivityRootBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RootActivity : BaseActivity<ActivityRootBinding>() {

    override fun inflateView(inflater: LayoutInflater) = ActivityRootBinding.inflate(inflater)

    override fun onCreate(savedInstanceState: Bundle?) {
        val screenSplash = installSplashScreen()
        super.onCreate(savedInstanceState)


        Thread.sleep(1500)
        screenSplash.setKeepOnScreenCondition{false}
        startActivity(Intent(this@RootActivity, BirdActivity::class.java))
        finish()

    }
}