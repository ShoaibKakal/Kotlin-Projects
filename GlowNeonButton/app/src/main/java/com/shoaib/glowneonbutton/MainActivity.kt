package com.shoaib.glowneonbutton

import android.graphics.Color
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.constraintlayout.widget.ConstraintLayout
import com.smb.glowbutton.GlowButton

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val myGlowButton = GlowButton(this)
        val params = ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.MATCH_PARENT,
            ConstraintLayout.LayoutParams.WRAP_CONTENT)
        params.setMargins(16, 8, 16, 0)
        myGlowButton.apply {
            layoutParams = params
            setCornerRadius(5)
            glowAnimationDuration = 500 //Increase at your own risk. Long animations are annoying. but whatever...I can't tell you what to do...
            rippleAnimationDuration = 1500 //Relax! It's milliseconds, not hours!
            backColor = Color.WHITE
            glowColor = Color.WHITE
            rippleColor = Color.GRAY //I wouldn't change the ripple color if I were you. But go crazy if you have to.
            setTextSize(16) //Enter desired size in dp (or sp, whatever!)
            setTextColor(Color.BLACK)
            disabledTextColor = Color.DKGRAY
            drawableTint = Color.BLACK
            setDrawablePadding(16)
            text = "I Am A Glow Button" //There is no `AllCaps` attribute, so do it yourself. Don't be lazy!
            textStyle = Typeface.BOLD_ITALIC
        }
    }

}