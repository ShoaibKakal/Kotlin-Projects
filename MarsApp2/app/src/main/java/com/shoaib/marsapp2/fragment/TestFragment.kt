package com.shoaib.marsapp2.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import coil.load
import com.bumptech.glide.Glide
import com.shoaib.marsapp2.R
import com.shoaib.marsapp2.adapter.ImagesAdapter
import com.shoaib.marsapp2.databinding.FragmentTestBinding
import com.shoaib.marsapp2.model.MarsPhotos
import retrofit2.Retrofit

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [TestFragment.newInstance] factory method to
 * create an instance of this fragment.
 */

private val TAG = "testTag"
class TestFragment : Fragment() {

    private lateinit var testViewModel: TestViewModel
    private lateinit var binding: FragmentTestBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentTestBinding.inflate(LayoutInflater.from(context), container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        testViewModel = ViewModelProvider(requireActivity()).get(TestViewModel::class.java)



        testViewModel.photos.observe(viewLifecycleOwner, Observer {
            val adapter = ImagesAdapter(it)
            binding.recyclerView.adapter = adapter
        })
    }

    companion object {

    }
}