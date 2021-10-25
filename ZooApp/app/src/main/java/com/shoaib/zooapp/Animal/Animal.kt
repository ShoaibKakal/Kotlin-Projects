package com.shoaib.zooapp.Animal

class Animal {

    var image: Int? = null
    var name: String? = null
    var des: String? = null
    var isKiller: Boolean? = null

    constructor(image: Int?, name: String?, des: String?, isKiller: Boolean?) {
        this.image = image
        this.name = name
        this.des = des
        this.isKiller = isKiller
    }
}