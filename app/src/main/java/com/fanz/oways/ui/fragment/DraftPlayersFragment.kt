package com.fanz.oways.ui.fragment

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.Display
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Toast
import androidx.core.hardware.display.DisplayManagerCompat
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.fanz.oways.R
import com.fanz.oways.databinding.FragmentDraftPlayersBinding
import com.fanz.oways.model.Player
import com.fanz.oways.model.PositionType
import com.fanz.oways.ui.adapter.ClubFilterAdapter
import com.fanz.oways.ui.adapter.EmptyCardPositionSelectionListener
import com.fanz.oways.ui.adapter.PlayerSelectionListener
import com.fanz.oways.ui.adapter.PlayersAdapter
import com.fanz.oways.ui.adapter.SelectedPlayersAdapter
import com.fanz.oways.ui.adapter.SpacesItemDecoration
import com.fanz.oways.viewmodel.DraftTeamViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DraftPlayersFragment : DialogFragment() {
    private var width: Int = 0
    private var selectionAdapter: SelectedPlayersAdapter? = null

    private var clubFilterAdapter: ClubFilterAdapter? = null
    private var adapter: PlayersAdapter? = null
    private var _binding: FragmentDraftPlayersBinding? = null

    private val binding get() = _binding!!

    private val draftTeamViewModel: DraftTeamViewModel by viewModels()

    var selectedCont: Int = 0
    var spentAmount: Int = 0
    companion object {
        const val TAG = "DraftPlayersFragment"
        fun newInstance(
        ): DraftPlayersFragment {
            return DraftPlayersFragment()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDraftPlayersBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setDialogWindowConfig()

        initScreenWidth()
        observers()
        initView()
        draftTeamViewModel.onEvent(DraftTeamViewModel.ClubEvents.GetClubs)
        println("##Player 00000")

       // draftTeamViewModel.onEvent(DraftTeamViewModel.PlayerEvents.GetAllPlayers)

    }

    private fun initScreenWidth() {

        width = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {

            val defaultDisplay =
                DisplayManagerCompat.getInstance(requireContext()).getDisplay(Display.DEFAULT_DISPLAY)
            val displayContext = requireContext().createDisplayContext(defaultDisplay!!)

            displayContext.resources.displayMetrics.widthPixels
        } else {

            val displayMetrics = DisplayMetrics()
            @Suppress("DEPRECATION")
            (requireContext().getSystemService(Context.WINDOW_SERVICE) as? WindowManager)?.defaultDisplay?.getMetrics(
                displayMetrics
            )
            displayMetrics.widthPixels
        }
    }

    private fun observers() {

        draftTeamViewModel.clubList.observe(viewLifecycleOwner){
            if (it.isNullOrEmpty()){
                binding.clubsRecyclerView.visibility = View.GONE
            }else {
                binding.clubsRecyclerView.visibility = View.VISIBLE
                clubFilterAdapter?.addList(it)
            }
        }

        draftTeamViewModel.playerList.observe(viewLifecycleOwner){
            if (it.isNullOrEmpty()){
                binding.rvPlayersList.visibility = View.GONE
            }else {
                binding.rvPlayersList.visibility = View.VISIBLE
                adapter?.addList(it)
                //selectionAdapter?.addList(it)
            }
        }
    }

    private fun initView() {

        binding.selectedPlayersCountTv.text = selectedCont.toString()
        binding.spentAmountTv.text = spentAmount.toString()

        binding.clubsRecyclerView.layoutManager = LinearLayoutManager(binding.root.context, LinearLayoutManager.HORIZONTAL, false)

        clubFilterAdapter = ClubFilterAdapter()
        binding.clubsRecyclerView.adapter = clubFilterAdapter

        val manager = GridLayoutManager(binding.root.context, 2)
       /* manager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                return if ((position == (adapter?.itemCount
                        ?: 0) - 1) && (position.mod(2)) == 0
                )
                    2
                else
                    1
            }
        }*/
        val spacingInPixels = resources.getDimensionPixelSize(R.dimen.dp_8)
        binding.rvPlayersList.addItemDecoration(SpacesItemDecoration(2, spacingInPixels, true))
     //    binding.rvPlayersList.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))

        binding.rvPlayersList.layoutManager = manager

        initAdapter()
        initSelectionAdapter()

    }

    private fun initSelectionAdapter() {
        binding.selectedRecyclerView.layoutManager = LinearLayoutManager(binding.root.context, LinearLayoutManager.HORIZONTAL, false)
        selectionAdapter = SelectedPlayersAdapter(itemWidth = width / 5 , object :EmptyCardPositionSelectionListener{
            override fun select(position: Int, positionType: PositionType) {
                selectionAdapter?.setSelectionAt(position)
                binding.selectedRecyclerView.scrollToPosition(position)
                draftTeamViewModel.onEvent(DraftTeamViewModel.PlayerEvents.GetPlayersByPosition(positionType.positionName))
            }

            override fun completeSelection(isCompleted: Boolean) {
                if (isCompleted) {
                    Toast.makeText(requireContext(), "Your team is ready", Toast.LENGTH_LONG).show()
                }
                adapter?.teamIsComplete(isCompleted)
            }

        })
        binding.selectedRecyclerView.adapter = selectionAdapter

    }

    private fun initAdapter() {

        adapter = PlayersAdapter( object : PlayerSelectionListener{
            override fun select(position: Int, player: Player) {
                selectionAdapter?.setSelectionPlayer(player)
                selectedCont += 1
                binding.selectedPlayersCountTv.text = selectedCont.toString()
                player.price?.let {
                    spentAmount+=it.uppercase().replace("M", "").toIntOrNull()?:0
                    binding.spentAmountTv.text = spentAmount.toString().plus("M")
                }
            }

        })
        binding.rvPlayersList.adapter = adapter
    }

    private fun setDialogWindowConfig() {
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog?.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
        val window = dialog?.window
        val wlp = window!!.attributes
        wlp.gravity = Gravity.BOTTOM
        wlp.flags = wlp.flags and WindowManager.LayoutParams.FLAG_DIM_BEHIND
        window.attributes = wlp

        dialog?.window?.attributes?.windowAnimations = R.style.slide_from_bottom


        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog?.window?.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        dialog?.window?.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
    }

}