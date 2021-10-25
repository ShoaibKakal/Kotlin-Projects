package com.shoaib.coroutinepractice1.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
class CommentModel (val postId:Int, val id:Int, val name:String, val email:String, val body:String) : Parcelable