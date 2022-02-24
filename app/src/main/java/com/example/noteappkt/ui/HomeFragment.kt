package com.example.noteappkt.ui

import android.graphics.Rect
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import com.example.noteappkt.adapter.CardClickListener
import com.example.noteappkt.adapter.PinnedRVAdapter
import com.example.noteappkt.adapter.UpcomingRVAdapter
import com.example.noteappkt.databinding.FragmentHomeBinding
import com.example.noteappkt.room.entities.NoteEntity
import com.example.noteappkt.viewmodel.AppViewModel
import dagger.hilt.android.AndroidEntryPoint
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ItemDecoration
import com.example.noteappkt.R


@AndroidEntryPoint
class HomeFragment : Fragment(), CardClickListener {

    private lateinit var binding: FragmentHomeBinding
    private val viewModel: AppViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
        binding.fragmentHome = this

        binding.upcomingRv.addItemDecoration(SpacesItemDecoration(resources.getDimensionPixelSize(R.dimen.space)))

        setupPinnedRecyclerview()
        setupUpcomingRecyclerview()

        return binding.root
    }

    private fun setupUpcomingRecyclerview() {

        viewModel.liveData.observe(viewLifecycleOwner) { dataList ->
            val data: ArrayList<NoteEntity> = ArrayList()

            dataList.forEach {
                if (!it.noteModel.pinned) {
                    data.add(it)
                }
            }
            if (data.isEmpty())
                binding.textView3.visibility = View.VISIBLE
            else
                binding.textView3.visibility = View.GONE
            binding.upcomingRv.adapter = UpcomingRVAdapter(data, this)
            StaggeredGridLayoutManager(
                2, // span count
                StaggeredGridLayoutManager.VERTICAL // orientation
            ).apply {
                // specify the layout manager for recycler view
                binding.upcomingRv.layoutManager = this
            }
        }
    }

    private fun setupPinnedRecyclerview() {
        viewModel.liveData.observe(viewLifecycleOwner) { dataList ->
            val data: ArrayList<NoteEntity> = ArrayList()

            dataList.forEach {
                if (it.noteModel.pinned) {
                    data.add(it)
                }
            }
            if (data.isEmpty())
                binding.pinnedCon.visibility = View.GONE
            else
                binding.pinnedCon.visibility = View.VISIBLE
            binding.pinnedRv.adapter = PinnedRVAdapter(data, this)
        }
    }


    fun fabOnClick(view: View) {
        view.findNavController().navigate(R.id.action_homeFragment_to_noteFragment)
    }

    override fun onItemClickListener(noteEntity: NoteEntity) {

        val bundle = bundleOf("dataModel" to noteEntity)
        Navigation.findNavController(binding.root).navigate(R.id.action_homeFragment_to_noteFragment,bundle)
    }


    override fun onMenuItemClickListener(imageFilterButton: View, noteEntity: NoteEntity) {
    val popMenu = PopupMenu(requireActivity(), imageFilterButton)
    popMenu.setOnMenuItemClickListener(PopupMenu.OnMenuItemClickListener {
        when(it.itemId){
            R.id.delete ->{
                deleteNoteFromDb(noteEntity)
                true
            }
            else -> return@OnMenuItemClickListener false
        }
    })
    popMenu.inflate(R.menu.action)
    popMenu.show()
}

private fun deleteNoteFromDb(noteEntity: NoteEntity) {
    viewModel.deleteNote(noteEntity)
}

    private class SpacesItemDecoration(private val space: Int) : ItemDecoration() {
        override fun getItemOffsets(
            outRect: Rect,
            view: View,
            parent: RecyclerView,
            state: RecyclerView.State
        ) {
            val position = parent.getChildAdapterPosition(view)
            if (position == 1) outRect.top = space * 2 else outRect.top = space
        }
    }
}