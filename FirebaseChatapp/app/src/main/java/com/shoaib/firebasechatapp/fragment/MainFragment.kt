package com.shoaib.firebasechatapp.fragment

import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Base64
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.firebase.firestore.*
import com.google.firebase.messaging.FirebaseMessaging
import com.shoaib.firebasechatapp.adapter.RecentConversationAdapter
import com.shoaib.firebasechatapp.databinding.FragmentMainBinding
import com.shoaib.firebasechatapp.listeners.ConversationListener
import com.shoaib.firebasechatapp.model.ChatMessage
import com.shoaib.firebasechatapp.model.User
import com.shoaib.firebasechatapp.utilities.Constants
import com.shoaib.firebasechatapp.utilities.PreferenceManager
import com.shoaib.firebasechatapp.utilities.showToast


class MainFragment : Fragment(), ConversationListener {

    private lateinit var binding: FragmentMainBinding
    private lateinit var preferenceManager: PreferenceManager
    private lateinit var conversations: ArrayList<ChatMessage>
    private lateinit var conversationsAdapter: RecentConversationAdapter
    private lateinit var database: FirebaseFirestore
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
        binding = FragmentMainBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
        loadUserDetails()
        getToken()
        setListeners()
        listenConversations()
    }

    private fun init() {
        preferenceManager = PreferenceManager(requireContext())
        conversations = ArrayList()
        conversationsAdapter = RecentConversationAdapter(conversations, this)
        binding.conversationRecyclerView.adapter = conversationsAdapter
        database = FirebaseFirestore.getInstance()
    }


    private fun setListeners() {
        binding.imageSignOut.setOnClickListener {
            signOut()
        }
        binding.fabNewChat.setOnClickListener {
            findNavController().navigate(com.shoaib.firebasechatapp.R.id.action_mainFragment_to_usersFragment)
        }
    }

    private fun loadUserDetails() {
        binding.textName.text = preferenceManager.getString(Constants.KEY_NAME)
        val bytes = Base64.decode(preferenceManager.getString(Constants.KEY_IMAGE), Base64.DEFAULT)
        BitmapFactory.decodeByteArray(bytes, 0, bytes.size).also {
            binding.imageProfile.setImageBitmap(it)
        }
    }

    private fun listenConversations() {
        database.collection(Constants.KEY_COLLECTION_CONVERSATIONS)
            .whereEqualTo(
                Constants.KEY_SENDER_ID,
                preferenceManager.getString(Constants.KEY_USER_ID)
            )
            .addSnapshotListener { value, error ->
                eventListener(value, error)
            }

        database.collection(Constants.KEY_COLLECTION_CONVERSATIONS)
            .whereEqualTo(
                Constants.KEY_RECEIVER_ID,
                preferenceManager.getString(Constants.KEY_USER_ID)
            )
            .addSnapshotListener { value, error ->
                eventListener(value, error)
            }


    }

    private fun eventListener(value: QuerySnapshot?, error: FirebaseFirestoreException?) {
        if (error != null) {
            return
        }
        if (value != null) {
            for (documentChange in value.documentChanges) {
                if (documentChange.type == DocumentChange.Type.ADDED) {
                    val senderId = documentChange.document.getString(Constants.KEY_SENDER_ID)
                    val receiverId = documentChange.document.getString(Constants.KEY_RECEIVER_ID)
                    val chatMessage = ChatMessage()
                    chatMessage.senderId = senderId!!
                    chatMessage.receiverId = receiverId!!
                    if (preferenceManager.getString(Constants.KEY_USER_ID)?.equals(senderId)!!) {
                        chatMessage.conversionImage =
                            documentChange.document.getString(Constants.KEY_RECEIVER_IMAGE)
                        chatMessage.conversionName =
                            documentChange.document.getString(Constants.KEY_RECEIVER_NAME)
                        chatMessage.conversionId =
                            documentChange.document.getString(Constants.KEY_RECEIVER_ID)
                    } else {
                        chatMessage.conversionImage =
                            documentChange.document.getString(Constants.KEY_SENDER_IMAGE)
                        chatMessage.conversionName =
                            documentChange.document.getString(Constants.KEY_SENDER_NAME)
                        chatMessage.conversionId =
                            documentChange.document.getString(Constants.KEY_SENDER_ID)
                    }

                    chatMessage.message =
                        documentChange.document.getString(Constants.KEY_LAST_MESSAGE)!!
                    chatMessage.dateObject =
                        documentChange.document.getDate(Constants.KEY_TIMESTAMP)!!
                    conversations.add(chatMessage)
                } else if (documentChange.type == DocumentChange.Type.MODIFIED) {
                    for (i in 0..conversations.size) {
                        val senderId = documentChange.document.getString(Constants.KEY_SENDER_ID)
                        val receiverId =
                            documentChange.document.getString(Constants.KEY_RECEIVER_ID)
                        if (conversations[i].senderId == senderId && conversations[i].receiverId == receiverId) {
                            conversations[i].message =
                                documentChange.document.getString(Constants.KEY_LAST_MESSAGE)!!
                            conversations[i].dateObject =
                                documentChange.document.getDate(Constants.KEY_TIMESTAMP)!!
                            break;
                        }
                    }
                }
            }

            conversations.sortByDescending {
                it.dateObject
            }


            conversationsAdapter.notifyDataSetChanged()
            binding.conversationRecyclerView.smoothScrollToPosition(0)
            binding.conversationRecyclerView.visibility = View.VISIBLE
            binding.progressBar.visibility = View.GONE
        }
    }

    private fun getToken() {
        FirebaseMessaging.getInstance().token.addOnSuccessListener {
            updateToken(it)
        }
    }

    private fun updateToken(token: String) {
        preferenceManager.putString(Constants.KEY_FCM_TOKEN, token)
        val database = FirebaseFirestore.getInstance()
        val documentReference = database.collection(Constants.KEY_COLLECTION_USERS)
            .document(preferenceManager.getString(Constants.KEY_USER_ID)!!)
        documentReference.update(Constants.KEY_FCM_TOKEN, token)
            .addOnFailureListener {
                requireContext().showToast("Unable to update token")
            }
    }

    private fun signOut() {
        requireContext().showToast("Signing out...")
        val database = FirebaseFirestore.getInstance()
        val documentReference = database.collection(Constants.KEY_COLLECTION_USERS)
            .document(preferenceManager.getString(Constants.KEY_USER_ID)!!)

        val updates = HashMap<String, Any>()
        updates.put(Constants.KEY_FCM_TOKEN, FieldValue.delete())
        documentReference.update(updates)
            .addOnSuccessListener {
                preferenceManager.clear()
                findNavController().navigate(com.shoaib.firebasechatapp.R.id.action_mainFragment_to_signInFragment)
            }
            .addOnFailureListener {
                requireContext().showToast("Unable to sign out")
            }

    }

    companion object {

    }

    override fun onConversationClicker(user: User) {

        findNavController().navigate(
            com.shoaib.firebasechatapp.R.id.action_mainFragment_to_chatFragment,
            bundleOf(Constants.KEY_USER to user)
        )

    }
}