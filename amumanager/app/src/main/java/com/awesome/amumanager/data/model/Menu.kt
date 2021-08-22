package com.awesome.amumanager.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Menu(var id: Int?,
                var name: String,
                var image: String,
                var price: String,
                var comment: String,
                var storeId : String) : Parcelable