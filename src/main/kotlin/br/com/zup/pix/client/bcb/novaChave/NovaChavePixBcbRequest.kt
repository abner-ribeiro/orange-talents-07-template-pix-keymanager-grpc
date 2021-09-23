package br.com.zup.pix.client.bcb.novaChave

import br.com.zup.models.ChavePix
import br.com.zup.models.Conta

class NovaChavePixBcbRequest(
    val keyType: ChavePixTipoBcbRequest,
    val key: String,
    val bankAccount: ContaBcbRequest,
    val owner: DonoBcbRequest
) {
    companion object {
        fun of(chave: ChavePix): NovaChavePixBcbRequest {
            return NovaChavePixBcbRequest(
                keyType = ChavePixTipoBcbRequest.by(chave.tipoChave!!),
                key = chave.chave!!,
                bankAccount = ContaBcbRequest(
                    participant = Conta.ITAU_UNIBANCO_ISPB,
                    branch = chave.conta.agencia,
                    accountNumber = chave.conta.numeroDaConta,
                    accountType = ContaTipoBcbRequest.by(chave.tipoConta!!)
                ),
                owner = DonoBcbRequest(
                    type = DonoTipo.NATURAL_PERSON,
                    name = chave.conta.nomeDoTitular,
                    taxIdNumber = chave.conta.cpfDoTitular
                )
            )
        }
    }
}
