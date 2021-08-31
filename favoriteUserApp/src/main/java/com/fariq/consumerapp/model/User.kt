package com.fariq.consumerapp.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
        var userId : Int? = 0,
        var avatar : String? = "",
        var username : String? = "",
        var name : String? = "",
        var company : String? = "",
        var location : String? = "",
        var followers : Int? = 0,
        var following : Int? = 0,
        var repository : Int? = 0,
) : Parcelable