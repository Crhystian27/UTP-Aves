package co.utp.aves.presentation.aboutus_detail

import android.view.LayoutInflater
import android.view.ViewGroup
import android.webkit.*
import androidx.fragment.app.viewModels
import co.utp.aves.R
import co.utp.aves.base.BaseFragment
import co.utp.aves.databinding.FragmentAboutUsDetailBinding
import co.utp.aves.presentation.BirdViewModel
import co.utp.aves.presentation.model.AboutUs
import co.utp.aves.utils.ABOUT_US_ITEM
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AboutUsDetailFragment : BaseFragment<FragmentAboutUsDetailBinding, BirdViewModel>() {


    override val viewModel: BirdViewModel by viewModels()

    var aboutUs: AboutUs? = null

    override fun getBundleArgs() {
        arguments?.let {
            aboutUs = it.getParcelable(ABOUT_US_ITEM)
        }
    }

    override fun inflateView(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentAboutUsDetailBinding.inflate(inflater, container, false)

    override fun setUI() {
        aboutUs?.Name?.let {
            setToolbarActivity(
                it,
                R.color.white,
                R.color.utp_blue
            )
        }

        aboutUs?.LinkedIn?.let { initWeb(it) }

    }


    private fun initWeb(url: String){
        with(binding.webView){

            webChromeClient = object : WebChromeClient() {}
            webViewClient = object : WebViewClient() {}

            CookieManager.getInstance().removeAllCookies(null)

            settings.javaScriptEnabled = true
            settings.domStorageEnabled = true
            settings.cacheMode = WebSettings.LOAD_NO_CACHE
            settings.mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW

            loadUrl(url)
        }
    }

    override fun observe() {
    }
}