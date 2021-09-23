package br.com.zup.pix.client.bcb.deletaChave

import br.com.zup.models.Conta

class DeletaChavePixBcbRequest(
    val key: String,
    val participant: String = Conta.ITAU_UNIBANCO_ISPB,
) {

}
