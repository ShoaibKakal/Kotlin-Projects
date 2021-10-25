package com.shoaib.bankmvvm.fragment

import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.shoaib.bankmvvm.R
import com.shoaib.bankmvvm.databinding.FragmentDepositMoneyBinding
import com.shoaib.bankmvvm.viewModel.MoneySharedViewModel


class DepositMoneyFragment : Fragment() {


    private val moneySharedViewModel: MoneySharedViewModel by activityViewModels()
    private lateinit var amount_et: EditText
    lateinit var binding: FragmentDepositMoneyBinding
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
        binding = FragmentDepositMoneyBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        amount_et = binding.depositAmountOutline.editText!!
        binding.btnDeposit.setOnClickListener { depositBtn(it) }
    }

    private fun depositBtn(it: View?) {

        if(!TextUtils.isEmpty(amount_et.text.toString()) && amount_et.text.toString().toDouble() > 0 ){
            moneySharedViewModel.deposit(amount_et.text.toString().toDouble())
            val bundle = bundleOf("amountDeposited" to amount_et.text.toString())
            findNavController().navigate(R.id.action_depositMoneyFragment_to_receiptDepositFragment, bundle)
            Toast.makeText(requireContext(), moneySharedViewModel.currentBalance.value.toString(), Toast.LENGTH_SHORT).show()
        }else{
            Toast.makeText(requireContext(), "Enter Valid Number", Toast.LENGTH_SHORT ).show()
        }
    }
}