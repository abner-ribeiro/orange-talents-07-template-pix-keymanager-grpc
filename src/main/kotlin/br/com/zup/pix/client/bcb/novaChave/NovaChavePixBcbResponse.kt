package br.com.zup.pix.client.bcb.novaChave

import br.com.zup.pix.client.bcb.novaChave.ChavePixTipoBcbRequest
import br.com.zup.pix.client.bcb.novaChave.ContaBcbRequest
import br.com.zup.pix.client.bcb.novaChave.DonoBcbRequest
import java.time.LocalDateTime

class NovaChavePixBcbResponse(
    val keyType: ChavePixTipoBcbRequest,
    val key: String,
    val bankAccount: ContaBcbRequest,
    val owner: DonoBcbRequest,
    val createdAt: LocalDateTime
) {

}
