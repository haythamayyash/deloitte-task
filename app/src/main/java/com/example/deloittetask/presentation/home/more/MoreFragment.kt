package com.example.deloittetask.presentation.home.more

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.deloittetask.BaseFragment
import com.example.deloittetask.databinding.FragmentMoreBinding


class MoreFragment : BaseFragment() {

    private var _binding: FragmentMoreBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModels<MoreViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentMoreBinding.inflate(inflater, container, false)
        return _binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getArticles()
    }

    companion object {
        fun newInstance() = MoreFragment()
    }
}
