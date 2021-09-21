package br.com.zup.pix.remove

class ChaveNaoEncontradaException: Exception() {
    override val message: String?
        get() = "Chave pix não encontrada"
}