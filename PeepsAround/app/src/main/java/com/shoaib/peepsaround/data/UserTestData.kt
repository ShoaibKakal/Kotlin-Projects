package com.shoaib.peepsaround.data

class UserTestData(val username:String?, val interests:ArrayList<String>?, val latitude:Double, val longitude:Double) {




    public fun data():ArrayList<UserTestData>
    {
        val arraylist: ArrayList<UserTestData> = ArrayList()
        arraylist.add(UserTestData("Shoaib", arrayListOf("Coding", "Football", "Movies"), 48.2161875,75.0789421))
        arraylist.add(UserTestData("Amish", arrayListOf( "Football", "Cooking"), 36.5506228,-91.6091536))
        arraylist.add(UserTestData("Adil", arrayListOf("Sports"), 28.285009,70.5649954))
        arraylist.add(UserTestData("Iqra", arrayListOf("Reading", "Cooking", "Teaching", "Programming", "Movies"), 48.2161875,-75.0789421))
        return arraylist
    }
}