package br.com.zup.pix.client

data class ClienteResponse(
    val id: String,
    val nome: String,
    val cpf: String,
    val instituicao: InstituicaoResponse
) {

}
