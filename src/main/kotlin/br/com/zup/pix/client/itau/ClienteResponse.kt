package br.com.zup.pix.client.itau

data class ClienteResponse(
    val id: String,
    val nome: String,
    val cpf: String,
    val instituicao: InstituicaoResponse
) {

}
