package br.com.zup.pix.registra

class ClienteNaoEncontradoException: Exception() {
    override val message: String?
        get() = "Cliente com esse clientId não encontrado"
}