package com.shoaib.bankmvvm.fragment

import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.shoaib.bankmvvm.R
import com.shoaib.bankmvvm.databinding.FragmentWithdrawMoneyBinding
import com.shoaib.bankmvvm.viewModel.MoneySharedViewModel


class WithdrawMoneyFragment : Fragment() {


    private var binding: FragmentWithdrawMoneyBinding? = null
    lateinit var amount_et: EditText
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
         binding = FragmentWithdrawMoneyBinding.inflate(inflater, container, false)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        amount_et = binding!!.depositAmountOutline.editText!!
        binding!!.btnWithdraw.setOnClickListener { withdrawBtn(it) }


    }

    private fun withdrawBtn(it: View?) {
        if (!TextUtils.isEmpty(amount_et.text.toString()) && amount_et.text.toString().toDouble() > 0 )
        {
            if(moneySharedViewModel.currentBalance.value!! >= amount_et.text.toString().toDouble()){
                moneySharedViewModel.withdraw(amount_et.text.toString().toDouble())
                findNavController().navigate(R.id.action_withdrawMoneyFragment_to_receiptWithdrawFragment)
            }
            else{
                Toast.makeText(requireContext(), "insufficient Balance${moneySharedViewModel.currentBalance.value.toString()}", Toast.LENGTH_SHORT).show()
            }
        }
    }
}