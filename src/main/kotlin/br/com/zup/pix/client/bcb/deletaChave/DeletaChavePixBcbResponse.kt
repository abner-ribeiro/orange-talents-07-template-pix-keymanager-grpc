package br.com.zup.pix.client.bcb.deletaChave

import br.com.zup.models.Conta
import java.time.LocalDateTime

class DeletaChavePixBcbResponse (
    val key: String,
    val participant: String,
    val deletedAt: LocalDateTime
){

}
