package com.awesome.amumanager.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Store(
    var id: Int?,
    var name: String,
    var image: String,
    var manager_uid : String,
    var lat: String?,
    var lng: String?,
    var place: String,
    var place_detail: String,
    var kind:String,
    var count: Int?,
    var point: Float?,
    var is_opened: String) : Parcelable
