package com.shoaib.imagefilterapp.fragments

import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.shoaib.imagefilterapp.databinding.FragmentEditImageBinding
import com.shoaib.imagefilterapp.utilities.displayToast
import com.shoaib.imagefilterapp.utilities.show
import com.shoaib.imagefilterapp.viewmodels.EditImageViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel


class EditImageFragment : Fragment() {


    private lateinit var binding: FragmentEditImageBinding
    private val viewModel: EditImageViewModel by viewModel()

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
        binding = FragmentEditImageBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setListeners()
        setupObservers()
        prepareImagePreview()
    }


    private fun setupObservers()
    {
        viewModel.imagePreviewUiState.observe(requireActivity(), {
            val dataState = it?:return@observe
            binding.previewProgressBar.visibility = if (dataState.isLoading) View.VISIBLE else View.GONE
            dataState.bitmap?.let {
                binding.imagePreview.setImageBitmap(it)
                binding.imagePreview.show()
            } ?: kotlin.run {
                dataState.error?.let {
                    requireContext().displayToast(it)
                }
            }
        })
    }

    private fun prepareImagePreview()
    {
        requireArguments().get(StartScreenFragment.KEY_IMAGE_URI)?.let {
            viewModel.prepareImagePreview(it as Uri)
        }

    }

    private fun setListeners() {

    }

    companion object {


    }
}