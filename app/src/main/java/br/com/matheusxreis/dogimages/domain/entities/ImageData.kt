package br.com.matheusxreis.dogimages.domain.entities

import com.google.gson.annotations.SerializedName

data class ImageData(
    @SerializedName("message")
    var url:String
)
