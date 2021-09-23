package br.com.zup.pix.client.itau

import br.com.zup.models.Conta
import br.com.zup.pix.client.bcb.TipoContaBcbResponse
import br.com.zup.pix.client.bcb.TitularContaBcbResponse

class ContaResponse(
    val tipo: TipoContaBcbResponse,
    val instituicao: InstituicaoResponse,
    val agencia: String,
    val numero: String,
    val titular: TitularContaBcbResponse
) {
    fun toModel(): Conta{
        return Conta(
            instituicao = instituicao.nome,
            nomeDoTitular = titular.nome,
            cpfDoTitular = titular.cpf,
            agencia = agencia,
            numeroDaConta = numero
        )
    }
}
