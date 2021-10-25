package com.shoaib.astroproject.model

open class OnBoardingItem {

    var image = 0
    var description = ""

    constructor(image: Int,  description: String) {
        this.image = image
        this.description = description
    }
}