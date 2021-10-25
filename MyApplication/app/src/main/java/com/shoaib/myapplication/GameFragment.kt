package com.shoaib.myapplication

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil

class GameFragment : Fragment() {

    data class Question(val text: String, val answer: List<String>)

    private val questions: MutableList<Question> = mutableListOf(
        Question(text = "What is Android JetPack? ", answer = listOf("All of these", "Tools", "Documentation", "Libraries")),
        Question(text = "What is the base class for layouts?", answer = listOf("ViewGroup", "ViewSet", "ViewCollection", "ViewRoot")),
        Question("Capital of Pakistan is?", listOf("Islamabad", "Quetta", "Lahore", "Karachi")),
        Question("Pakistan got independence on? ", listOf("1947", "1940", "1847", "1950")),
        Question("Capital of India? ", listOf("Delhi", "Mumbai", "Chennai", "Marastara")),
        Question("Capital of China", listOf("Shangai", "Xin", "Yountong", "Xangai"))
    )

    lateinit var currentQuestion: Question
    lateinit var answer: MutableList<String>
    private var questionIndex = 0
    private val numQuestions = Math.min((questions.size+1)/2, 3)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding = DataBindingUtil.inflate<FragmentGameBinding>()
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_game, container, false)
    }
}