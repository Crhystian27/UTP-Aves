package co.utp.aves.presentation.bird

import android.annotation.SuppressLint
import android.graphics.Rect
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Space
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ItemDecoration
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import co.utp.aves.R
import co.utp.aves.base.BaseFragment
import co.utp.aves.databinding.FragmentBirdBinding
import co.utp.aves.presentation.BirdEvent
import co.utp.aves.presentation.BirdViewModel
import co.utp.aves.presentation.bird.adapter.BirdAdapter
import co.utp.aves.presentation.model.Ave
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class BirdFragment : BaseFragment<FragmentBirdBinding, BirdViewModel>() {

    override val viewModel: BirdViewModel by viewModels()


    override fun inflateView(
        inflater: LayoutInflater,
        container: ViewGroup?
    )= FragmentBirdBinding.inflate(inflater, container,false)


    override fun setUI() {
        setToolbarActivity(getString(R.string.title),
            R.color.white,
            R.color.utp_blue
        )
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getBirds()
    }

    override fun setListeners() {
        super.setListeners()
        //todo implement click and bird detail
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun setAdapter(birds: List<Ave>){
        with(binding.rvBird){
            if(adapter == null){
                layoutManager = StaggeredGridLayoutManager(
                    2,
                    LinearLayoutManager.VERTICAL
                )

                adapter = BirdAdapter()
            }
            setHasFixedSize(true)
            adapter!!.setHasStableIds(true)
            (adapter as? BirdAdapter)?.submitList(birds)
            adapter?.notifyDataSetChanged()

        }
    }

    override fun observe() {
        viewModel.event.observe(viewLifecycleOwner){ event ->
            when (event) {
                is BirdEvent.ListBird -> {
                    setAdapter(event.result)
                }
                else -> {}
            }
        }
    }


}