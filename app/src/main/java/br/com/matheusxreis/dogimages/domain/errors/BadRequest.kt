package br.com.matheusxreis.dogimages.domain.errors

class BadRequest: Exception() {
    override val message: String?
        get() = super.message
}