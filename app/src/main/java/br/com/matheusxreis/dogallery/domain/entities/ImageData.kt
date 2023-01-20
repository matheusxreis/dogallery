package br.com.matheusxreis.dogallery.domain.entities

import com.google.gson.annotations.SerializedName

data class ImageData(
    @SerializedName("message")
    var url:String
)
