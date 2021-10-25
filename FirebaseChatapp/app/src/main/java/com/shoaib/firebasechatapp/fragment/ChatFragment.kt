package com.shoaib.firebasechatapp.fragment

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Base64
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.QuerySnapshot
import com.shoaib.firebasechatapp.adapter.ChatAdapter
import com.shoaib.firebasechatapp.databinding.FragmentChatBinding
import com.shoaib.firebasechatapp.model.ChatMessage
import com.shoaib.firebasechatapp.model.User
import com.shoaib.firebasechatapp.utilities.Constants
import com.shoaib.firebasechatapp.utilities.PreferenceManager
import com.shoaib.firebasechatapp.utilities.showToast
import com.shoaib.firebasechatapp.viewmodel.ChatViewModel
import org.json.JSONArray
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap


class ChatFragment : Fragment() {

    private lateinit var chatViewModel: ChatViewModel

    private lateinit var binding: FragmentChatBinding
    private lateinit var receiveUser: User
    private lateinit var chatMessages: ArrayList<ChatMessage>
    private lateinit var chatAdapter: ChatAdapter
    private lateinit var database: FirebaseFirestore
    private lateinit var preferenceManager: PreferenceManager
    private var conversationId: String? = null
    private var isReceiveAvailable = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            receiveUser = it.getSerializable(Constants.KEY_USER) as User
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentChatBinding.inflate(inflater, container, false)
        chatViewModel = ViewModelProvider(requireActivity()).get(ChatViewModel::class.java)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        init()
        loadReceiverDetails()
        setListeners()
        listenMessages()
        listenAvailabilityOfReceiver()
    }

    private fun setListeners() {
        binding.imageBack.setOnClickListener {
            //on back pressed
        }

        binding.layoutSend.setOnClickListener {
            val inputMessage = binding.inputMessage.text.toString()
            sendMessage(inputMessage)
        }
    }

    private fun init() {
        preferenceManager = PreferenceManager(requireContext())
        chatMessages = ArrayList()

        chatAdapter = ChatAdapter(
            chatMessages,
            getBitmapFromEncodedString(receiveUser.image),
            preferenceManager.getString(Constants.KEY_USER_ID)!!
        )

        binding.chatRecyclerview.adapter = chatAdapter
        database = FirebaseFirestore.getInstance()
    }

    private fun sendMessage(inputMessage: String) {
        val message = HashMap<String, Any>()
        message[Constants.KEY_SENDER_ID] = preferenceManager.getString(Constants.KEY_USER_ID)!!
        message[Constants.KEY_RECEIVER_ID] = receiveUser.id!!
        message[Constants.KEY_MESSAGE] = binding.inputMessage.text.toString()
        message[Constants.KEY_TIMESTAMP] = Date()
        database.collection(Constants.KEY_COLLECTION_CHAT).add(message)
        Log.d(TAG, "sendMessage: $conversationId")

//        if (conversationId != null) {
//
//        } else {
//        val conversion = HashMap<String, Any>()
//        conversion[Constants.KEY_SENDER_ID] =
//            preferenceManager.getString(Constants.KEY_USER_ID)!!
//        conversion[Constants.KEY_SENDER_NAME] =
//            preferenceManager.getString(Constants.KEY_NAME)!!
//        conversion[Constants.KEY_SENDER_IMAGE] =
//            preferenceManager.getString(Constants.KEY_IMAGE)!!
//        conversion[Constants.KEY_RECEIVER_ID] = receiveUser.id!!
//        conversion[Constants.KEY_RECEIVER_NAME] = receiveUser.name!!
//        conversion[Constants.KEY_RECEIVER_IMAGE] = receiveUser.image!!
//        conversion[Constants.KEY_LAST_MESSAGE] = binding.inputMessage.text.toString()
//        conversion[Constants.KEY_TIMESTAMP] = Date()
//            addConversion(conversion)
//        }


        var isConversationAvailable = false

        database.collection(Constants.KEY_COLLECTION_CONVERSATIONS)
            .whereEqualTo(Constants.KEY_SENDER_ID, receiveUser.id)
            .whereEqualTo(
                Constants.KEY_RECEIVER_ID,
                preferenceManager.getString(Constants.KEY_USER_ID)
            )
            .get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful && task.result != null && task.result?.documents?.size!! > 0) {
                    val documentSnapshot = task.result!!.documents[0]
                    conversationId = documentSnapshot.id
                    isConversationAvailable = true
                }
            }

        database.collection(Constants.KEY_COLLECTION_CONVERSATIONS)
            .whereEqualTo(Constants.KEY_RECEIVER_ID, receiveUser.id)
            .whereEqualTo(
                Constants.KEY_SENDER_ID,
                preferenceManager.getString(Constants.KEY_USER_ID)
            )
            .get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful && task.result != null && task.result?.documents?.size!! > 0) {
                    val documentSnapshot = task.result!!.documents[0]
                    conversationId = documentSnapshot.id
                    isConversationAvailable = true
                }
                if (isConversationAvailable) {
                    updateConversion(inputMessage)
                } else {
                    val conversion = HashMap<String, Any>()
                    conversion[Constants.KEY_SENDER_ID] =
                        preferenceManager.getString(Constants.KEY_USER_ID)!!
                    conversion[Constants.KEY_SENDER_NAME] =
                        preferenceManager.getString(Constants.KEY_NAME)!!
                    conversion[Constants.KEY_SENDER_IMAGE] =
                        preferenceManager.getString(Constants.KEY_IMAGE)!!
                    conversion[Constants.KEY_RECEIVER_ID] = receiveUser.id!!
                    conversion[Constants.KEY_RECEIVER_NAME] = receiveUser.name!!
                    conversion[Constants.KEY_RECEIVER_IMAGE] = receiveUser.image!!
                    conversion[Constants.KEY_LAST_MESSAGE] = inputMessage
                    conversion[Constants.KEY_TIMESTAMP] = Date()
                    addConversion(conversion)
                }
//                if (task.isSuccessful && task.result != null && task.result?.documents?.size!! > 0) {
//                    val documentSnapshot = task.result!!.documents[0]
//                    conversationId = documentSnapshot.id
//
//                }
//                else if(task.result?.documents?.size!! == 0){
//                    val conversion = HashMap<String, Any>()
//                    conversion[Constants.KEY_SENDER_ID] =
//                        preferenceManager.getString(Constants.KEY_USER_ID)!!
//                    conversion[Constants.KEY_SENDER_NAME] =
//                        preferenceManager.getString(Constants.KEY_NAME)!!
//                    conversion[Constants.KEY_SENDER_IMAGE] =
//                        preferenceManager.getString(Constants.KEY_IMAGE)!!
//                    conversion[Constants.KEY_RECEIVER_ID] = receiveUser.id!!
//                    conversion[Constants.KEY_RECEIVER_NAME] = receiveUser.name!!
//                    conversion[Constants.KEY_RECEIVER_IMAGE] = receiveUser.image!!
//                    conversion[Constants.KEY_LAST_MESSAGE] = inputMessage
//                    conversion[Constants.KEY_TIMESTAMP] = Date()
//                    addConversion(conversion)
//
//                }
            }
        if (!isReceiveAvailable) {
            try {
                val tokens = JSONArray()
                tokens.put(receiveUser.token)

                val data = JSONObject()
                data.put(Constants.KEY_USER_ID, preferenceManager.getString(Constants.KEY_USER_ID))
                data.put(Constants.KEY_NAME, preferenceManager.getString(Constants.KEY_NAME))
                data.put(
                    Constants.KEY_FCM_TOKEN,
                    preferenceManager.getString(Constants.KEY_FCM_TOKEN)
                )
                data.put(Constants.KEY_MESSAGE, binding.inputMessage.text.toString())

                val body = JSONObject()
                body.put(Constants.REMOTE_MSG_DATA, data)
                body.put(Constants.REMOTE_MSG_REGISTRATION_IDS, tokens)
                sendNotification(body.toString())
            } catch (e: Exception) {
                requireContext().showToast(e.message!!)
            }
        }
        binding.inputMessage.text = null
    }

    private fun sendNotification(messageBody: String) {
        chatViewModel.getResponse(messageBody)

        chatViewModel.response.observe(viewLifecycleOwner, androidx.lifecycle.Observer {

            if (it != null) {
                val responseJson = JSONObject(chatViewModel.response.value!!)
                val results = responseJson.getJSONArray("results")
                if (responseJson.getInt("failure") == 1) {
                    val error = results[0] as JSONObject
                    requireContext().showToast(error.getString("error"))
                    Log.d(TAG, "sendNotification: ${error.getString("error")}")

                }

                requireContext().showToast("Notification sent Successfully")
                Log.d(TAG, "sendNotification: Sent Successfully")
            }
        })
    }

    private fun listenAvailabilityOfReceiver() {
        database.collection(Constants.KEY_COLLECTION_USERS).document(receiveUser.id!!)
            .addSnapshotListener { value, _ ->

//                Log.d(TAG, "listenAvailabilityOfReceiver: ${value?.getBoolean(Constants.KEY_AVAILABILITY)}")
                if (value != null) {
                    if (value.getBoolean(Constants.KEY_AVAILABILITY) != null) {
                        isReceiveAvailable = value.getBoolean(Constants.KEY_AVAILABILITY)!!
                    }

                    receiveUser.token = value.getString(Constants.KEY_FCM_TOKEN)

                    if (receiveUser.image == null) {
                        receiveUser.image = value.getString(Constants.KEY_IMAGE)
                        chatAdapter.setReceiverProfileImage(getBitmapFromEncodedString(receiveUser.image))
                        chatAdapter.notifyItemRangeChanged(0, chatMessages.size)
                    }

                }
                if (isReceiveAvailable) {
                    binding.textAvailability.visibility = View.VISIBLE
                } else {
                    binding.textAvailability.visibility = View.GONE
                }


            }


    }


    private fun listenMessages() {
        database.collection(Constants.KEY_COLLECTION_CHAT)
            .whereEqualTo(
                Constants.KEY_SENDER_ID,
                preferenceManager.getString(Constants.KEY_USER_ID)
            )
            .whereEqualTo(Constants.KEY_RECEIVER_ID, receiveUser.id)
            .addSnapshotListener { value, error ->
                eventListener(value, error)
            }

        database.collection(Constants.KEY_COLLECTION_CHAT)
            .whereEqualTo(Constants.KEY_SENDER_ID, receiveUser.id)
            .whereEqualTo(
                Constants.KEY_RECEIVER_ID,
                preferenceManager.getString(Constants.KEY_USER_ID)
            )
            .addSnapshotListener { value, error ->
                eventListener(value, error)
            }
    }

    private fun eventListener(value: QuerySnapshot?, error: FirebaseFirestoreException?) {
        if (error != null)
            return
        if (value != null) {
            val count = chatMessages.size
            value.documentChanges.forEach {
                if (it.type == DocumentChange.Type.ADDED) {
                    val senderId = it.document.getString(Constants.KEY_SENDER_ID)
                    val receiverId = it.document.getString(Constants.KEY_RECEIVER_ID)
                    val message = it.document.getString(Constants.KEY_MESSAGE)
                    val dateTime =
                        getReadableDateTime(it.document.getDate(Constants.KEY_TIMESTAMP)!!)
                    val dateObject = it.document.getDate(Constants.KEY_TIMESTAMP)
                    Log.d(TAG, "eventListener: ${message.toString()}")
                    chatMessages.add(
                        ChatMessage(
                            senderId!!,
                            receiverId!!,
                            message!!,
                            dateTime,
                            dateObject!!
                        )
                    )
                }
            }

            chatMessages.sortBy { obj: ChatMessage ->
                obj.dateObject
            }
            if (count == 0) {
                chatAdapter.notifyDataSetChanged()
            } else {
                chatAdapter.notifyItemRangeChanged(chatMessages.size, chatMessages.size)
                binding.chatRecyclerview.smoothScrollToPosition(chatMessages.size - 1)
            }
            binding.chatRecyclerview.visibility = View.VISIBLE
        }
        binding.progressBar.visibility = View.GONE

//        if (conversationId == null) {
//            checkForConversion()
//        }
    }

    private fun getBitmapFromEncodedString(encodedImage: String?): Bitmap? {

        if (encodedImage != null) {
            Base64.decode(encodedImage, Base64.DEFAULT).also {
                return BitmapFactory.decodeByteArray(it, 0, it.size)
            }
        } else {
            return null
        }
    }

    private fun loadReceiverDetails() {
        binding.textName.text = receiveUser.name
    }

    private fun getReadableDateTime(date: Date): String {
        return SimpleDateFormat("MMMM dd, yyyy - hh:mm a", Locale.getDefault()).format(date)
    }


    private fun addConversion(conversion: HashMap<String, Any>) {
        Log.d(TAG, "addConversion: called")
        database.collection(Constants.KEY_COLLECTION_CONVERSATIONS)
            .add(conversion)
            .addOnSuccessListener { documentReference ->
                conversationId = documentReference.id
                Log.d(TAG, "addConversion: ${documentReference.id}")
            }


    }

    private fun updateConversion(message: String) {
        Log.d(TAG, "updateConversion: called")
        val documentReference =
            database.collection(Constants.KEY_COLLECTION_CONVERSATIONS).document(conversationId!!)
        documentReference.update(
            Constants.KEY_LAST_MESSAGE, message,
            Constants.KEY_TIMESTAMP, Date()
        )


    }


    override fun onResume() {
        super.onResume()
        listenAvailabilityOfReceiver()
    }

    override fun onDetach() {
        super.onDetach()

    }


//    private fun checkForConversion() {
//        if (chatMessages.size != 0) {
//            checkForConversionRemotely(
//                preferenceManager.getString(Constants.KEY_SENDER_ID),
//                receiveUser.id!!
//            )
//            checkForConversionRemotely(
//                receiveUser.id!!,
//                preferenceManager.getString(Constants.KEY_USER_ID)!!
//            )
//        }
//    }
//
//    private fun checkForConversionRemotely(senderId: String?, receiverId: String) {
//        database.collection(Constants.KEY_COLLECTION_CONVERSATIONS)
//            .whereEqualTo(Constants.KEY_SENDER_ID, senderId)
//            .whereEqualTo(Constants.KEY_RECEIVER_ID, receiverId)
//            .get()
//            .addOnCompleteListener { task ->
//                if (task.isSuccessful && task.result != null && task.result?.documents?.size!! > 0) {
//                    val documentSnapshot = task.result!!.documents[0]
//                    conversationId = documentSnapshot.id
//
//                }
//            }
//    }

    companion object {

        const val TAG = "chatTag"
    }
}