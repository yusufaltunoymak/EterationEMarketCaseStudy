package com.altunoymak.eterationemarketcasestudy.data.remote.model


import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class ProductResponseItem(
    @SerializedName("brand")
    val brand: String?,
    @SerializedName("createdAt")
    val createdAt: String?,
    @SerializedName("description")
    val description: String?,
    @SerializedName("id")
    val id: String?,
    @SerializedName("image")
    val image: String?,
    @SerializedName("model")
    val model: String?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("price")
    val price: String?
) : Serializable