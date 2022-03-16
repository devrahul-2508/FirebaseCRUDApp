package com.example.firebasecrudapp.modals

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Course(
    var courseName:String? = null,
    var coursePrice:String? = null,
    var bestSuitedFor:String? = null,
    var imageLink:String? = null,
    var courseLink:String? = null,
    var courseDescription:String? = null,
    var courseID:String?=null
):Parcelable