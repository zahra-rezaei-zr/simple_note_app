package com.example.noteappkt.ui

import android.content.Context
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import com.example.noteappkt.MainActivity
import com.example.noteappkt.R
import com.example.noteappkt.databinding.FragmentNoteBinding
import com.example.noteappkt.model.Note
import com.example.noteappkt.room.entities.NoteEntity
import com.example.noteappkt.viewmodel.AppViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NoteFragment : Fragment() {

    private val viewModel: AppViewModel by viewModels()
    private lateinit var binding: FragmentNoteBinding
    private var savedColor = "#a38bf3"
    private lateinit var mainActivity: MainActivity
    private lateinit var navController: NavController
    private var pinned = false
    private var isUpdating = false
    private lateinit var noteEntity: NoteEntity


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_note, container, false)
        binding.singleNote = this

        getData()

        return binding.root
    }

    private fun getData() {
        if (arguments != null) {
            noteEntity = arguments?.getParcelable("dataModel")!!

            binding.titleEdtx.setText(noteEntity.noteModel.title)
            binding.noteEdtx.setText(noteEntity.noteModel.description)

            pinned = noteEntity.noteModel.pinned
            savedColor = noteEntity.noteModel.color
            isUpdating=true


            hideAllCheck()
            colorCheckToVisible(noteEntity.noteModel.color)

        }
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupToolbar(view)
    }

    private fun setupToolbar(view: View) {

        navController = Navigation.findNavController(view)
        val appBarConfiguration = AppBarConfiguration.Builder(R.id.noteFragment).build()
        val toolbar: Toolbar = view.findViewById(R.id.toolbar)
        NavigationUI.setupWithNavController(toolbar, navController, appBarConfiguration)

        mainActivity.setSupportActionBar(toolbar)
        mainActivity.supportActionBar?.setDisplayHomeAsUpEnabled(true)


        navController.addOnDestinationChangedListener { controller, destination, arguments ->
            {

                if (destination.id == R.id.noteFragment)
                    toolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back)

            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.note_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.pinitem ->
                if (!pinned) {
                    item.icon = ContextCompat.getDrawable(
                        requireActivity(), R.drawable.baseline_push_pin_black_24dp
                    )
                    pinned = !pinned
                    true
                } else {
                    item.icon = ContextCompat.getDrawable(
                        requireActivity(), R.drawable.ic_pin
                    )
                    pinned = !pinned
                    true
                }
            android.R.id.home -> {
                mainActivity.onBackPressed()
                true
            }
            else ->
                super.onOptionsItemSelected(item)
        }
    }


    fun onAddNoteClick(view: View) {

        if (isUpdating){

            binding.apply {
                if (this.titleEdtx.text.isNullOrBlank())
                    Snackbar.make(this.mainCoord, "Pls Enter Your Title...", Snackbar.LENGTH_SHORT)
                        .show()
                else {
                    if (this.noteEdtx.text.isNullOrBlank()) {
                        Snackbar.make(this.mainCoord, "Pls Enter Your Note...", Snackbar.LENGTH_SHORT)
                            .show()
                    } else {
                       noteEntity.noteModel.title=this.titleEdtx.text.toString()
                       noteEntity.noteModel.description=this.noteEdtx.text.toString()
                        noteEntity.noteModel.pinned = pinned
                        noteEntity.noteModel.color = savedColor


                        viewModel.updateNoteDatabase(noteEntity)
                        Navigation.findNavController(view)
                            .navigate(R.id.action_noteFragment_to_homeFragment)
                    }
                }
            }
        }else{
            binding.apply {
                if (this.titleEdtx.text.isNullOrBlank())
                    Snackbar.make(this.mainCoord, "Pls Enter Your Title...", Snackbar.LENGTH_SHORT)
                        .show()
                else {
                    if (this.noteEdtx.text.isNullOrBlank()) {
                        Snackbar.make(this.mainCoord, "Pls Enter Your Note...", Snackbar.LENGTH_SHORT)
                            .show()
                    } else {
                        val title = this.titleEdtx.text.toString()
                        val note = this.noteEdtx.text.toString()
                        val color = savedColor


                        val noteModel = Note(title, note, color, pinned)
                        viewModel.insertToDataBase(noteModel)
                        Navigation.findNavController(view)
                            .navigate(R.id.action_noteFragment_to_homeFragment)
                    }
                }
            }
        }
    }

    fun onColorViewClick(check: View) {
        hideAllCheck()
        check.visibility = View.VISIBLE

        binding.apply {
            when (check.id) {
                this.check1.id -> savedColor = "#a38bf3"
                this.check2.id -> savedColor = "#fb7792"
                this.check3.id -> savedColor = "#5ed8a3"
                this.check4.id -> savedColor = "#6bc6f2"
                this.check5.id -> savedColor = "#d98de2"
                this.check6.id -> savedColor = "#f8ac4a"
            }
        }
    }

    private fun hideAllCheck() {
        binding.apply {
            this.check1.visibility = View.INVISIBLE
            this.check2.visibility = View.INVISIBLE
            this.check3.visibility = View.INVISIBLE
            this.check4.visibility = View.INVISIBLE
            this.check5.visibility = View.INVISIBLE
            this.check6.visibility = View.INVISIBLE
        }
    }


    // visible exact color according to card click
    private fun colorCheckToVisible(color: String) {
        binding.apply {
            when (color) {
                "#a38bf3" -> this.check1.visibility = View.VISIBLE
                "#fb7792" -> this.check2.visibility = View.VISIBLE
                "#5ed8a3" -> this.check3.visibility = View.VISIBLE
                "#6bc6f2" -> this.check4.visibility = View.VISIBLE
                "#d98de2" -> this.check5.visibility = View.VISIBLE
                "#f8ac4a" -> this.check6.visibility = View.VISIBLE
            }
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainActivity = context as MainActivity
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        super.onPrepareOptionsMenu(menu)
        val item = menu.findItem(R.id.pinitem)
        if (pinned)
            item.icon=ContextCompat.getDrawable(requireActivity(),R.drawable.baseline_push_pin_black_24dp)

        else
            item.icon=ContextCompat.getDrawable(requireActivity(),R.drawable.ic_pin)

    }

}