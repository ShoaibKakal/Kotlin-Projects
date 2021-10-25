package com.shoaib.roomdbkotlin.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.shoaib.roomdbkotlin.FruitApplication
import com.shoaib.roomdbkotlin.data.Fruit
import com.shoaib.roomdbkotlin.databinding.FragmentAddFruitBinding
import com.shoaib.roomdbkotlin.viewmodels.FruitViewModel

class AddFruitFragment : Fragment() {

    private lateinit var binding: FragmentAddFruitBinding
    private var fruit: Fruit? = null


    private val fruitViewModel: FruitViewModel by activityViewModels {
        FruitViewModel.FruitViewModelFactory((activity?.application as FruitApplication).fruitDatabase.fruitDao())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentAddFruitBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fruitViewModel.addFruit(Fruit(1, "Apple", 123, 5.0))
        fruitViewModel.addFruit(Fruit(2, "Banana", 103, 2.1))
        fruitViewModel.addFruit(Fruit(2, "Orange", 103, 2.1))

    }
}