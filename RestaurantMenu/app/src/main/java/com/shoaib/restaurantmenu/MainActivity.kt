package com.shoaib.restaurantmenu

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.GridView
import android.widget.ImageView
import android.widget.TextView
import com.shoaib.restaurantmenu.model.Food

class MainActivity : AppCompatActivity() {
    var foodList = ArrayList<Food>()
    var foodAdapter: FoodAdapter? = null
    lateinit var gridView: GridView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        gridView = findViewById(R.id.gridView)


        foodList.add(Food("Coffee", R.drawable.coffee_pot, getString(R.string.lorum_ipsum)))
        foodList.add(Food("Espresso", R.drawable.espresso, getString(R.string.lorum_ipsum)))
        foodList.add(Food("French Fries", R.drawable.french_fries, getString(R.string.lorum_ipsum)))
        foodList.add(Food("Honey", R.drawable.honey, getString(R.string.lorum_ipsum)))
        foodList.add(Food("Sugar Cubes", R.drawable.sugar_cubes, getString(R.string.lorum_ipsum)))
        foodList.add(Food("Strawberry ice cream", R.drawable.strawberry_ice_cream, getString(R.string.lorum_ipsum)))

        foodAdapter = FoodAdapter(foodList, this)

        gridView.adapter = foodAdapter
    }


    class FoodAdapter: BaseAdapter{
        var listOfFood = ArrayList<Food>()
        var context: Context? = null

        constructor(listOfFood: ArrayList<Food>, context: Context?) : super() {
            this.listOfFood = listOfFood
            this.context = context
        }

        override fun getCount(): Int {
            return listOfFood.size
        }

        override fun getItem(position: Int): Any {
            return listOfFood[position]
        }

        override fun getItemId(position: Int): Long {
            return position.toLong()
        }

        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {

            var inflater = context!!.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            var view = inflater.inflate(R.layout.layout_food_item, null)

            val image = view.findViewById<ImageView>(R.id.imageViewItem)
            val name = view.findViewById<TextView>(R.id.textItemName)

            val food = listOfFood[position]
            image.setImageResource(food.image!!)
            name.text = food.name

            return view
        }


    }
}