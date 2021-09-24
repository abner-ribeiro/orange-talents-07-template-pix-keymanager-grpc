package br.com.zup.pix.client.bcb.detalhaChave

import br.com.zup.TipoConta
import br.com.zup.models.ChavePix
import br.com.zup.models.Conta
import br.com.zup.pix.client.bcb.novaChave.ChavePixTipoBcbRequest
import br.com.zup.pix.client.bcb.novaChave.ContaBcbRequest
import br.com.zup.pix.client.bcb.novaChave.ContaTipoBcbRequest
import br.com.zup.pix.client.bcb.novaChave.DonoBcbRequest
import java.time.LocalDateTime

class DetalhaChavePixBcbResponse (
    val keyType: ChavePixTipoBcbRequest,
    val key: String,
    val bankAccount: ContaBcbRequest,
    val owner: DonoBcbRequest,
    val createdAt: LocalDateTime
    ){

    fun toModel(): ChavePix{
        var tipoConta: TipoConta
        if(bankAccount.accountType == ContaTipoBcbRequest.CACC){
            tipoConta = TipoConta.CONTA_CORRENTE
        }else{
            tipoConta = TipoConta.CONTA_POUPANCA
        }
        return ChavePix(
            clienteId = null,
            tipoChave = null,
            chave = key,
            tipoConta = tipoConta,
            conta = Conta(instituicao = "",
                nomeDoTitular = owner.name,
                cpfDoTitular = owner.taxIdNumber,
                agencia = bankAccount.branch,
                numeroDaConta = bankAccount.accountNumber
            )
        )
    }

}
