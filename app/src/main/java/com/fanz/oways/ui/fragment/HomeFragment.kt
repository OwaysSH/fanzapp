package com.fanz.oways.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.fanz.oways.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>() {

    private var draftPlayersFragment: DraftPlayersFragment? = null
    override val inflateOnBreadingBinding: (LayoutInflater, ViewGroup?, Boolean) -> FragmentHomeBinding
        get() = FragmentHomeBinding::inflate


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
    }

    private fun initView() {

        bindingSafe?.selectTeamBtn?.setOnClickListener {
            draftPlayersFragment?.dismissAllowingStateLoss()
            draftPlayersFragment = DraftPlayersFragment.newInstance()
            draftPlayersFragment?.isCancelable = true
            draftPlayersFragment?.show(
                childFragmentManager,
                DraftPlayersFragment.TAG
            )
        }
    }

}
