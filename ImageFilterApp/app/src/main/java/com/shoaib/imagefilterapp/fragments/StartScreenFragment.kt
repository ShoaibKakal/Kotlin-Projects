package com.shoaib.imagefilterapp.fragments

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import com.shoaib.imagefilterapp.R
import com.shoaib.imagefilterapp.databinding.FragmentStartScreenBinding
import org.koin.android.ext.android.bind

class StartScreenFragment : Fragment() {


    private lateinit var binding: FragmentStartScreenBinding
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
        binding = FragmentStartScreenBinding.inflate(inflater, container, false )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setListeners()
    }

    private fun setListeners()
    {
        binding.buttonEditNewImage.setOnClickListener {
            Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                .also {
                    it.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                    startActivityForResult(it, REQUEST_CODE_PICK_IMAGE)
                }
        }

        binding.buttonViewSavedImages.setOnClickListener {

        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if(requestCode == REQUEST_CODE_PICK_IMAGE && resultCode == RESULT_OK)
        {
            data?.data.let {imageUri ->

                val bundle = bundleOf(KEY_IMAGE_URI to imageUri)
                findNavController().navigate(R.id.action_startScreenFragment_to_editImageFragment, bundle)

            }
        }
    }


    companion object {

        private const val REQUEST_CODE_PICK_IMAGE = 1
         const val KEY_IMAGE_URI = "imageUri"
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            StartScreenFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }
}