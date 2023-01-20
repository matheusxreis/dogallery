package br.com.matheusxreis.dogallery.domain.errors

class NotFound: Exception() {
    override val message: String?
        get() = super.message
}