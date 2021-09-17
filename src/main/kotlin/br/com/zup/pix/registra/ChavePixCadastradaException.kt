package br.com.zup.pix.registra

class ChavePixCadastradaException : Exception() {
    override val message: String?
        get() = "Já existe essa chave pix cadastrada"
}
