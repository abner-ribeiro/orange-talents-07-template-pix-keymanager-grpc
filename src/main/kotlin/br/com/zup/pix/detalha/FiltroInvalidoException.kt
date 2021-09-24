package br.com.zup.pix.detalha

class FiltroInvalidoException: Exception() {
    override val message: String?
        get() = "Filtro inválido"
}