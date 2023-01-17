package br.com.matheusxreis.dogimages.domain.errors

class NotFound: Exception() {
    override val message: String?
        get() = super.message
}