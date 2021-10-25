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
import com.shoaib.bankmvvm.databinding.FragmentSignInBinding
import com.shoaib.bankmvvm.viewModel.MoneySharedViewModel


class SignIn : Fragment() {


    private var binding: FragmentSignInBinding? = null
    lateinit var userName: EditText
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
        binding = FragmentSignInBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        userName = binding?.nameOutline!!.editText!!


        binding!!.btnSignIn.setOnClickListener { signInBtn(it) }
    }

    private fun signInBtn(it: View?) {

        if (TextUtils.isEmpty(userName.text.toString().trim())) {
            Toast.makeText(requireContext(), "Enter Name", Toast.LENGTH_SHORT).show()
        } else {
            moneySharedViewModel.setCustomerName(userName.text.toString())
            findNavController().navigate(com.shoaib.bankmvvm.R.id.action_signIn_to_mainFragment)
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}