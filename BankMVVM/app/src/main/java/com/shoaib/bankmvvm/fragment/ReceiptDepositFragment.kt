package com.shoaib.bankmvvm.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.shoaib.bankmvvm.R
import com.shoaib.bankmvvm.databinding.FragmentReceiptDepositBinding
import com.shoaib.bankmvvm.viewModel.MoneySharedViewModel
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class ReceiptDepositFragment : Fragment() {

    lateinit var binding: FragmentReceiptDepositBinding
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
        binding = FragmentReceiptDepositBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val formatter = SimpleDateFormat("dd MMM(kk:mm:ss a)", Locale.getDefault())
        val calendar = Calendar.getInstance()

        val amountDeposited =requireArguments().getString("amountDeposited")
        binding.textSummary.text = getString(
            R.string.deposit_summary,
            amountDeposited,
            formatter.format(calendar.time),
            moneySharedViewModel.currentBalance.value.toString()
        )
    }
}