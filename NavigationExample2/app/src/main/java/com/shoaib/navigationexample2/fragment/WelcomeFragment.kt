package com.shoaib.navigationexample2.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.shoaib.navigationexample2.R
import com.shoaib.navigationexample2.databinding.FragmentWelcomeBinding
import com.shoaib.navigationexample2.model.User

class WelcomeFragment : Fragment(), View.OnClickListener {
    // TODO: Rename and change types of parameters

    private lateinit var user: User
    private lateinit var binding: FragmentWelcomeBinding
    private lateinit var navController: NavController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

            user = requireArguments().getParcelable<User>("user")!!

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentWelcomeBinding.inflate(inflater, container, false)
        return  binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
        binding.textWelcome.text = user.username

        binding.buttonDetails.setOnClickListener(this)

    }

    override fun onClick(v: View?) {

        when(v!!.id)
        {
            R.id.button_details -> {
                val bundle = bundleOf("user" to user)
                navController.navigate(R.id.action_welcomeFragment_to_userDetailsFragment, bundle)
            }
        }
    }


}