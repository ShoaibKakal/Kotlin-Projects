package com.shoaib.zooapp

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.ListView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.commit
import com.shoaib.zooapp.Animal.Animal

class MainActivity : AppCompatActivity() {

    lateinit var animalList: ArrayList<Animal>
    var animalAdapter: AnimalAdapter? = null
    lateinit var listViewAnimal: ListView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        listViewAnimal = findViewById(R.id.listView_Animal)
        animalList = ArrayList()
        animalList.add(
            Animal(
                R.drawable.baboon,
                "Baboon",
                getString(R.string.baboon_description),
                false
            )
        )
        animalList.add(Animal(R.drawable.bulldog, "Bull Dog", getString(R.string.bull_desc), true))
        animalList.add(Animal(R.drawable.panda, "Panda", getString(R.string.panda_desc), true))
        animalList.add(
            Animal(
                R.drawable.white_tiger,
                "White Tiger",
                getString(R.string.whiteTiger_desc),
                false
            )
        )
        animalList.add(
            Animal(
                R.drawable.swallow_bird,
                "Swallon Bird",
                getString(R.string.swallon_bird_desc),
                true
            )
        )
        animalList.add(Animal(R.drawable.zebra, "Zebra", getString(R.string.zebra_desc), false))




        animalAdapter = AnimalAdapter(animalList, this, this)
        listViewAnimal.adapter = animalAdapter
    }

    fun deleteView(position: Int) {
        animalList.removeAt(position)
        animalAdapter!!.notifyDataSetChanged()
    }

    inner class AnimalAdapter : BaseAdapter {
        var context: Context
        var fragmentActivity: FragmentActivity? = null
        var listOfAnimals: ArrayList<Animal>? = null

        constructor(
            listOfAnimals: ArrayList<Animal>?,
            context: Context,
            fragmentActivity: FragmentActivity
        ) : super() {
            this.listOfAnimals = listOfAnimals
            this.context = context
            this.fragmentActivity = fragmentActivity
        }

        override fun getCount(): Int {
            return listOfAnimals!!.size
        }

        override fun getItem(position: Int): Any {
            return listOfAnimals?.get(position)!!
        }

        override fun getItemId(position: Int): Long {
            return position.toLong()
        }

        @SuppressLint("ViewHolder")
        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
            val animal = listOfAnimals?.get(position)

            val inflater =
                context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            val rowView: View
            if (animal?.isKiller!!) {
                rowView = inflater.inflate(R.layout.layout_killer_animal_ticket, parent, false)
            } else {
                rowView = inflater.inflate(R.layout.animal_ticket, parent, false)
            }
            val imageAnimal = rowView.findViewById<ImageView>(R.id.image_animal)
            val textNameAnimal = rowView.findViewById<TextView>(R.id.text_animal_name)
            val textAnimalDesc = rowView.findViewById<TextView>(R.id.text_animal_description)

            imageAnimal.setImageResource(animal.image!!)
            textNameAnimal.text = animal.name
            textAnimalDesc.text = animal.des


            rowView.setOnClickListener {


                deleteView(position)
                fragmentActivity?.supportFragmentManager?.commit {
                    setCustomAnimations(
                        R.anim.slide_in,
                        R.anim.fade_out,
                        R.anim.fade_in,
                        R.anim.slide_out
                    )
                    val animal = listOfAnimals?.get(position)
                    val bundle = bundleOf("image" to animal?.image, "name" to animal?.name, "desc" to animal?.des)
                    replace<AnimalDescriptionFragment>(R.id.animal_desc_fragment_contianer, args = bundle)
                    addToBackStack("animal_desc_frag")
                }


                // deleting view

            }



            return rowView
        }


    }
}