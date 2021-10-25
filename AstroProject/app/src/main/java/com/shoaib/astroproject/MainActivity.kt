package com.shoaib.astroproject

import android.content.Intent
import android.os.Bundle
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.viewpager2.widget.ViewPager2
import com.shoaib.astroproject.adapter.onBoardingAdapter
import com.shoaib.astroproject.databinding.ActivityMainBinding
import com.shoaib.astroproject.model.OnBoardingItem

class MainActivity : AppCompatActivity() {
    lateinit var mainBinding: ActivityMainBinding
    lateinit var onBoardingAdapter: onBoardingAdapter
    lateinit var itemArrayList: ArrayList<OnBoardingItem>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mainBinding.root)
        itemArrayList = ArrayList()

        itemArrayList.add(
            OnBoardingItem(
                R.drawable.klipartz,
                "There are two series of dying sunflowers. The first was painted in Paing on the ground."
            )
        )

        itemArrayList.add(
            OnBoardingItem(
                R.drawable.klipartz2,
                "The first day or so we all pointed to our countries. The third or fourth day we were pointing to our continents. By the fifth day we were only aware of warth"
            )
        )



        itemArrayList.add(
            OnBoardingItem(
                R.drawable.glaxay1,
                "THE STARS DON’T LOOK BIGGER, BUT THEY DO LOOK BRIGHTER."
            )
        )

        itemArrayList.add(
            OnBoardingItem(
                R.drawable.klipartz3,
                "THE DREAM IS ALIVE."
            )
        )


        onBoardingAdapter = onBoardingAdapter(itemArrayList)
        onBoardingAdapter.notifyDataSetChanged()
        mainBinding.onBoardingScreensViewPager.adapter = onBoardingAdapter
        setCurrentOnboardingIndicator(0)
        mainBinding.onBoardingScreensViewPager.registerOnPageChangeCallback(object :
            ViewPager2.OnPageChangeCallback() {

            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                setCurrentOnboardingIndicator(position)
            }
        })

        setupOnboardingIndicator()


        mainBinding.explorationBtn.setOnClickListener {
            val intent = Intent(this, NavActivity::class.java)
            startActivity(intent)
        }
    }


    private fun setCurrentOnboardingIndicator(position: Int) {
        var childCount = mainBinding.indicatorsLL.childCount

        for (i in 0 until childCount) {
            val imageView: ImageView = mainBinding.indicatorsLL.getChildAt(i) as ImageView
            if (i == position) {
                imageView.setImageDrawable(
                    ContextCompat.getDrawable(
                        applicationContext,
                        R.drawable.on_boarding_indicator_active
                    )
                )
            } else {
                imageView.setImageDrawable(
                    ContextCompat.getDrawable(
                        applicationContext,
                        R.drawable.on_boarding_indicator_inactive
                    )
                )
            }

        }


    }


    private fun setupOnboardingIndicator() {
        val indicators = arrayOfNulls<ImageView>(onBoardingAdapter.itemCount)
        val layoutParams = LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        layoutParams.setMargins(5, 0, 5, 0)
        for (i in indicators.indices) {
            indicators[i] = ImageView(applicationContext)
            indicators[i]!!.setImageDrawable(
                ContextCompat.getDrawable(
                    applicationContext,
                    R.drawable.on_boarding_indicator_inactive
                )
            )
            indicators[i]!!.layoutParams = layoutParams
            mainBinding.indicatorsLL.addView(indicators[i])
        }
    }
}