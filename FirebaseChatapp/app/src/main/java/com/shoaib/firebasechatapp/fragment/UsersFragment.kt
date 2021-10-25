package com.shoaib.firebasechatapp.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.firebase.firestore.FirebaseFirestore
import com.shoaib.firebasechatapp.R
import com.shoaib.firebasechatapp.adapter.UsersAdapter
import com.shoaib.firebasechatapp.databinding.FragmentUsersBinding
import com.shoaib.firebasechatapp.listeners.UserListener
import com.shoaib.firebasechatapp.model.User
import com.shoaib.firebasechatapp.utilities.Constants
import com.shoaib.firebasechatapp.utilities.PreferenceManager


class UsersFragment : Fragment(), UserListener {


    private lateinit var binding: FragmentUsersBinding
    private lateinit var preferenceManager: PreferenceManager
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
        binding = FragmentUsersBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        preferenceManager = PreferenceManager(requireContext())
        setListeners()
        getUsers()
    }

    private fun setListeners() {
        binding.imageBack.setOnClickListener {
            findNavController().navigate(R.id.action_usersFragment_to_mainFragment)

        }
    }

    private fun getUsers() {
        loading(true)
        FirebaseFirestore.getInstance().also {
            it.collection(Constants.KEY_COLLECTION_USERS).get()
                .addOnCompleteListener { task ->
                    loading(false)
                    val currentUserId = preferenceManager.getString(Constants.KEY_USER_ID)
                    if (task.isSuccessful && task.result != null) {
                        val users = ArrayList<User>()
                        for (snapShot in task.result!!) {
                            if (currentUserId.equals(snapShot.id)) {
                                continue
                            }
                            val name = snapShot.getString(Constants.KEY_NAME)
                            val email = snapShot.getString(Constants.KEY_EMAIL)
                            val image = snapShot.getString(Constants.KEY_IMAGE)
                            val token = snapShot.getString(Constants.KEY_FCM_TOKEN)
                            val id = snapShot.id
                            val user = User(name!!, image!!, email!!, token, id)

                            users.add(user)
                        }

                        if (users.size > 0) {
                            val usersAdapter = UsersAdapter(requireContext(), this, users)
                            binding.usersRecyclerView.adapter = usersAdapter
                            binding.usersRecyclerView.visibility = View.VISIBLE
                        } else {
                            showErrorMessage()
                        }
                    } else {
                        showErrorMessage()
                    }
                }
        }
    }

    private fun loading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }

    private fun showErrorMessage() {
        binding.textErrorMessage.text = String.format("%s", "No user Available")
        binding.textErrorMessage.visibility = View.VISIBLE
    }

    companion object {
    }

    override fun onUserClicked(user: User) {
        findNavController().navigate(
            R.id.action_usersFragment_to_chatFragment,
            bundleOf(Constants.KEY_USER to user)
        )

    }
}