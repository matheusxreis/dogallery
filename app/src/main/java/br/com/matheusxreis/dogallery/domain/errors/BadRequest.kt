package br.com.matheusxreis.dogallery.domain.errors

class BadRequest: Exception() {
    override val message: String?
        get() = super.message
}