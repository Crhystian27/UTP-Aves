package co.utp.aves


import android.view.LayoutInflater
import android.view.View
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import co.utp.aves.base.BaseActivity
import co.utp.aves.databinding.ActivityBirdBinding
import co.utp.aves.utils.hide
import co.utp.aves.utils.show
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BirdActivity : BaseActivity<ActivityBirdBinding>() {

    override fun inflateView(inflater: LayoutInflater) =
        ActivityBirdBinding.inflate(inflater)

    override fun setToolbarTitle(title: String) {
        binding.toolbarMainActivity.title = title
    }

    override fun setUI() {
        binding.apply {
            val navController = findNavController(R.id.bird_nav_host_fragment)
            bottomNavMenu.setupWithNavController(navController)

            // Hide bottom nav on screens which don't require it
            lifecycleScope.launchWhenCreated {
                navController.addOnDestinationChangedListener { _, destination, _ ->
                    when (destination.id) {
                        R.id.navigation_bird, R.id.navigation_camera -> {

                            binding.toolbarMainActivity.visibility = View.VISIBLE
                            bottomNavMenu.show()
                        }

                        R.id.navigation_about_us -> {
                            binding.toolbarMainActivity.visibility = View.GONE
                            bottomNavMenu.show()
                        }

                        else -> {
                            binding.toolbarMainActivity.visibility = View.GONE
                            bottomNavMenu.hide()
                        }
                    }

                }
            }
        }
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
}