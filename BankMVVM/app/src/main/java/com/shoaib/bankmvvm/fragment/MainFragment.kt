package com.shoaib.bankmvvm.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.shoaib.bankmvvm.R
import com.shoaib.bankmvvm.databinding.FragmentMainBinding
import com.shoaib.bankmvvm.viewModel.MoneySharedViewModel

class MainFragment : Fragment() {

    lateinit var binding: FragmentMainBinding
    private val moneySharedViewModel: MoneySharedViewModel by activityViewModels()
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
        binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Toast.makeText(requireContext(), moneySharedViewModel.currentBalance.value.toString(), Toast.LENGTH_SHORT).show()
        Toast.makeText(requireContext(), moneySharedViewModel.customerName.value.toString(), Toast.LENGTH_SHORT).show()
        binding.btnDeposit.setOnClickListener { depositBtn(it) }
        binding.btnWithdraw.setOnClickListener { withdrawBtn(it) }
        binding.btnTransfer.setOnClickListener { transferBtn(it) }
        binding.btnCheckBalance.setOnClickListener { checkBalanceBtn(it) }
    }

    private fun checkBalanceBtn(it: View?) {
        Toast.makeText(requireContext(), "Not Implemented", Toast.LENGTH_LONG).show()
    }


    private fun transferBtn(it: View?) {
        findNavController().navigate(R.id.action_mainFragment_to_transferMoneyFragment)
    }

    private fun withdrawBtn(it: View?) {
        findNavController().navigate(R.id.action_mainFragment_to_withdrawMoneyFragment)
    }

    private fun depositBtn(it: View?) {
        findNavController().navigate(R.id.action_mainFragment_to_depositMoneyFragment)
    }
}