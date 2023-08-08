package co.utp.aves

import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import co.utp.aves.base.BaseActivity
import co.utp.aves.databinding.ActivityRootBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RootActivity : BaseActivity<ActivityRootBinding>() {

    override fun inflateView(inflater: LayoutInflater) = ActivityRootBinding.inflate(inflater)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        window.decorView.systemUiVisibility = (
                View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        or View.SYSTEM_UI_FLAG_FULLSCREEN
                        or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                )


        object : CountDownTimer(3200L, 3200L) {
            override fun onTick(millisUntilFinished: Long) {
                // No se usa en este caso
            }

            override fun onFinish() {
                // El temporizador ha terminado, inicia la siguiente actividad
                startActivity(Intent(this@RootActivity, BirdActivity::class.java))
                finish()
            }
        }.start()
    }
}