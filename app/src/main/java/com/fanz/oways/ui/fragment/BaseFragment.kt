package com.fanz.oways.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding

abstract class BaseFragment<B : ViewBinding> : Fragment() {

    var _binding: B? = null
    val binding: B
        get() = _binding!!

    val bindingSafe: B?
        get() = _binding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = inflateOnBreadingBinding.invoke(inflater, container, false)
        return binding.root
    }

    abstract val inflateOnBreadingBinding: (LayoutInflater, ViewGroup?, Boolean) -> B

}