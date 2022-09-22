package co.utp.aves

import android.os.Bundle
import android.view.LayoutInflater
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import co.utp.aves.base.BaseActivity
import co.utp.aves.databinding.ActivityBirdBinding

class BirdActivity : BaseActivity<ActivityBirdBinding>(){


    override fun inflateView(inflater: LayoutInflater)=
        ActivityBirdBinding.inflate(inflater)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    private fun bottomNavigation(){
        binding.bottomNavMenu.setOnItemReselectedListener { menu ->
            when (menu.title) {

            }
        }
    }

}