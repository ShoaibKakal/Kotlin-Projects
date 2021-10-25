package com.shoaib.restaurantmenu.model

class Food {
    var name: String? = null
    var image: Int? = null
    var des: String? = null


    constructor(name: String, image: Int, des: String) {
        this.name = name
        this.image = image
        this.des = des
    }
}