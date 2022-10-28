package co.utp.aves.presentation.bird

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import co.utp.aves.R
import co.utp.aves.base.BaseActivity
import co.utp.aves.base.BaseFragment
import co.utp.aves.databinding.FragmentBirdBinding
import co.utp.aves.presentation.BirdEvent
import co.utp.aves.presentation.BirdViewModel
import co.utp.aves.presentation.bird.adapter.BirdAdapter
import co.utp.aves.presentation.bird.adapter.BirdClick
import co.utp.aves.presentation.model.Ave
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class BirdFragment : BaseFragment<FragmentBirdBinding, BirdViewModel>(), BirdClick {

    override val viewModel: BirdViewModel by viewModels()

    private var pressedTime: Long = 0

    override fun inflateView(
        inflater: LayoutInflater,
        container: ViewGroup?
    )= FragmentBirdBinding.inflate(inflater, container,false)


    override fun setUI() {
        setToolbarActivity(getString(R.string.title_bird_fragment),
            R.color.white,
            R.color.utp_blue
        )
    }


    private fun handleBack() {
        (requireActivity() as? BaseActivity<*>)?.hideBack()
        requireActivity().onBackPressedDispatcher.addCallback(
            this,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    if(pressedTime+ 2000 > System.currentTimeMillis()){
                        requireActivity().finish()
                    }else {
                        Toast.makeText(context, getString(R.string.back_pressed), Toast.LENGTH_SHORT).show()
                    }
                    pressedTime = System.currentTimeMillis()
                }
            })


    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getBirds()
    }

    override fun setListeners() {
        binding.apply {
            var delete = false
            tietSearchBird.addTextChangedListener ( object : TextWatcher{
                override fun beforeTextChanged(
                    s: CharSequence?, start: Int, count: Int, after: Int) {}

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    delete = before > count
                }

                override fun afterTextChanged(charSequence: Editable?) {
                    if(delete)
                        viewModel.searchBird(charSequence?.toString(),
                            null)
                    else
                        viewModel.searchBird(
                            charSequence?.toString(),
                            (rvBird.adapter as? BirdAdapter)?.currentList
                        )
                }
            } )
        }
        handleBack()
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun setAdapter(birds: List<Ave>) {
        with(binding.rvBird) {

            if (adapter == null) {
                layoutManager = StaggeredGridLayoutManager(
                    2,
                    LinearLayoutManager.VERTICAL
                )

                adapter = BirdAdapter(this@BirdFragment)
            }

            (adapter as? BirdAdapter)?.submitList(birds)

        }
    }

    override fun observe() {
        viewModel.event.observe(viewLifecycleOwner) { event ->
            when (event) {
                is BirdEvent.ListBird -> {
                    setAdapter(event.listBird)
                }
                else -> {}
            }
        }
    }

    override fun onClick(ave: Ave) {
        findNavController().navigate(
            BirdFragmentDirections.actionBirdToBirdDetail(
                ave
            )
        )
    }


}