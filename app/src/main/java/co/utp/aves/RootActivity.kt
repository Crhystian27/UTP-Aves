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
        installSplashScreen()
        super.onCreate(savedInstanceState)
    }

    override fun setUI() {
        /*with(binding){
            Glide.with(this@RootActivity)
                .load(R.raw.bird_utp)
                .into(splashBird)

        }*/
        startActivity(Intent(this@RootActivity, BirdActivity::class.java))
        finish()
    }
}