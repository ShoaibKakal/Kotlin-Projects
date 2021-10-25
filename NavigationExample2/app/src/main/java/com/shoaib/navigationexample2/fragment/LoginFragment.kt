package com.shoaib.navigationexample2.fragment


import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.shoaib.navigationexample2.R
import com.shoaib.navigationexample2.databinding.FragmentLoginBinding
import com.shoaib.navigationexample2.model.User



class LoginFragment : Fragment(), View.OnClickListener {

    lateinit var binding: FragmentLoginBinding
    lateinit var navController: NavController
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
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
        binding.buttonLogin.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.button_login -> {
                if (!TextUtils.isEmpty(binding.inputUsername.text.toString()) && !TextUtils.isEmpty(
                        binding.inputPassword.text.toString()
                    )
                ) {
                    Toast.makeText(
                        requireActivity(),
                        binding.inputUsername.text.toString(),
                        Toast.LENGTH_SHORT
                    ).show()
                    val bundle = bundleOf(
                        "user" to User(
                            binding.inputUsername.text.toString(),
                            binding.inputPassword.text.toString()
                        )
                    )
                    navController.navigate(R.id.action_loginFragment_to_welcomeFragment, bundle)

                }
            }
        }
    }
}