package co.utp.aves

import android.os.Bundle
import android.view.LayoutInflater
import androidx.core.content.ContextCompat
import co.utp.aves.base.BaseActivity
import co.utp.aves.databinding.ActivityBirdBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BirdActivity : BaseActivity<ActivityBirdBinding>(){

    override fun inflateView(inflater: LayoutInflater)=
        ActivityBirdBinding.inflate(inflater)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bottomNavigation()
        binding.toolbarMainActivity.inflateMenu(R.menu.toolbar_quit_menu)
        actionQuitToolbar()
    }

    private fun bottomNavigation(){


    }

    override fun setToolbarTitle(title: String) {
        binding.toolbarMainActivity.title = "Aves de Pereira"
    }

    override fun setToolbarStyle(titleColor: Int?, backgroundColor: Int?) {
        binding.apply {
            titleColor?.let { color ->
                toolbarMainActivity.setTitleTextColor(
                    ContextCompat.getColor(
                        this@BirdActivity,
                        color
                    )
                )
            }
            backgroundColor?.let { color ->
                toolbarMainActivity.setBackgroundColor(
                    ContextCompat.getColor(
                        this@BirdActivity,
                        color
                    )
                )
            }
        }
    }

    private fun actionQuitToolbar(){
        binding.toolbarMainActivity.setOnMenuItemClickListener{
            when (it.itemId) {
                R.id.action_quit -> {
                    finish()
                    true
                }
                else -> false
            }
        }
    }

}