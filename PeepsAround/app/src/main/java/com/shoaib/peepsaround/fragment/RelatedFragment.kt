package com.shoaib.peepsaround.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*
import com.shoaib.peepsaround.R
import com.shoaib.peepsaround.adapter.UserCardAdapter
import com.shoaib.peepsaround.model.CurrentLocation
import com.shoaib.peepsaround.model.User

class RelatedFragment : Fragment() {

    private lateinit var mDatabase: FirebaseDatabase
    private lateinit var mRef: DatabaseReference
    private lateinit var valueEventListener: ValueEventListener
    private lateinit var userCardAdapter: UserCardAdapter
    private lateinit var relatedUserRecyclerView: RecyclerView
    private var relatedUserList: ArrayList<User> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mDatabase = FirebaseDatabase.getInstance() //database reference
        mRef = mDatabase.getReference("users") //database root node reference
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_related, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        relatedUserRecyclerView = view.findViewById(R.id.recyclerView_related_users)

        val currentUser: User =
            User("Adil", arrayListOf("Football", "Coding"), CurrentLocation(9.8745, 9.25645))

        valueEventListener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {

                relatedUserList.clear()
                snapshot.children.forEach {
                    val user = it.getValue<User>(User::class.java)
                    Log.d(HomeFragment.TAG, "onDataChange: ${user?.username}")
                    Log.d(HomeFragment.TAG, "onDataChange: ${user?.interests}")
                    Log.d(
                        HomeFragment.TAG,
                        "onDataChange: ${user?.currentLocation?.latitude?.toString()}"
                    )
                    Log.d(
                        HomeFragment.TAG,
                        "onDataChange: ${user?.currentLocation?.longitude?.toString()}"
                    )


                    currentUser.interests.forEach { cUser:String->
                        if(user?.interests?.contains(cUser)!! && !relatedUserList.contains(user))
                        {
                            relatedUserList.add(user)
                        }
                    }

                    userCardAdapter = UserCardAdapter(requireContext(), relatedUserList)
                    relatedUserRecyclerView.adapter = userCardAdapter
                    userCardAdapter.notifyDataSetChanged()
                }

                relatedUserRecyclerView.setHasFixedSize(true)

//                user.forEach { s, user ->
//                    Log.d(TAG, "onDataChange: username: ${user.username}")
//                    Log.d(TAG, "onDataChange: interest: ${user.interests.toString()}")
//                    Log.d(TAG, "onDataChange: interest: ${user.currentLocation.toString()}")
//                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.d(HomeFragment.TAG, "onCancelled: ${error.message}")
            }

        }

        mRef.addValueEventListener(valueEventListener)



    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment RelatedFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            RelatedFragment().apply {
                arguments = Bundle().apply {

                }
            }

    }

}
