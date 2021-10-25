package com.shoaib.bankmvvm.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.shoaib.bankmvvm.R
import com.shoaib.bankmvvm.databinding.FragmentTransferMoneyBinding


class TransferMoneyFragment : Fragment() {

    lateinit var binding: FragmentTransferMoneyBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentTransferMoneyBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnTransfer.setOnClickListener { transferBtn(it) }
    }

    private fun transferBtn(it: View?) {
        findNavController().navigate(R.id.action_transferMoneyFragment_to_receiptTransferFragment)
        Toast.makeText(requireContext(), "Clicked", Toast.LENGTH_LONG).show()
    }

}