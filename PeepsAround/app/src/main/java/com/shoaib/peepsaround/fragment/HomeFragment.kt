package com.shoaib.peepsaround.fragment

import android.media.MediaRouter
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*
import com.shoaib.peepsaround.R
import com.shoaib.peepsaround.adapter.UserCardAdapter
import com.shoaib.peepsaround.data.UserTestData
import com.shoaib.peepsaround.model.TestModel
import com.shoaib.peepsaround.model.User
import java.util.*
import kotlin.collections.ArrayList

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [HomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class HomeFragment : Fragment() {

    private lateinit var recyclerviewUsersCard: RecyclerView
    private lateinit var userCardAdapter: UserCardAdapter
    private var userList = ArrayList<User>()

    private lateinit var mDatabase: FirebaseDatabase
    private lateinit var mRef: DatabaseReference
    private lateinit var childEventListener: ChildEventListener
    private lateinit var valueEventListener: ValueEventListener

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
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerviewUsersCard = view.findViewById(R.id.recyclerview_user_cards)



        valueEventListener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {

                userList.clear()
                snapshot.children.forEach {
                    val user = it.getValue<User>(User::class.java)
                    Log.d(TAG, "onDataChange: ${user?.username}")
                    Log.d(TAG, "onDataChange: ${user?.interests}")
                    Log.d(TAG, "onDataChange: ${user?.currentLocation?.latitude?.toString()}")
                    Log.d(TAG, "onDataChange: ${user?.currentLocation?.longitude?.toString()}")

                    userList.add(user!!)
                    userCardAdapter = UserCardAdapter(requireContext(), userList)
                    recyclerviewUsersCard.adapter = userCardAdapter
                    userCardAdapter.notifyDataSetChanged()
                }

                recyclerviewUsersCard.setHasFixedSize(true)

//                user.forEach { s, user ->
//                    Log.d(TAG, "onDataChange: username: ${user.username}")
//                    Log.d(TAG, "onDataChange: interest: ${user.interests.toString()}")
//                    Log.d(TAG, "onDataChange: interest: ${user.currentLocation.toString()}")
//                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.d(TAG, "onCancelled: ${error.message}")
            }

        }

        mRef.addValueEventListener(valueEventListener)


        //fetching data from firebase
        childEventListener = object : ChildEventListener {
            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                val user: HashMap<String, User> = snapshot.value as HashMap<String, User>
                Log.d(TAG, "onChildAdded: ${user.get("username")}")
                Log.d(TAG, "onChildAdded: ${user.get("interests")}")
                Log.d(TAG, "onChildAdded: ${user.get("currentLocation")}")
            }

            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
                TODO("Not yet implemented")
            }

            override fun onChildRemoved(snapshot: DataSnapshot) {
                TODO("Not yet implemented")
            }

            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {
                TODO("Not yet implemented")
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        }
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    companion object {

        const val TAG = "HomeFrag"
    }

}