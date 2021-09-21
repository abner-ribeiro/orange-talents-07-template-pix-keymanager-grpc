package br.com.zup.pix

import br.com.zup.NovaChaveRequest
import br.com.zup.RemoveChaveRequest
import br.com.zup.TipoChave
import br.com.zup.TipoConta
import br.com.zup.pix.dto.NovaChaveDto
import br.com.zup.pix.dto.RemoveChaveDto

fun NovaChaveRequest.toDto(): NovaChaveDto {
    return NovaChaveDto(
        clienteId = clienteId,
        tipoChave = if(tipoChave == TipoChave.CHAVE_DESCONHECIDA) null else tipoChave,
        chave = chave,
        tipoConta = if(tipoConta == TipoConta.CONTA_DESCONHECIDA) null else tipoConta
    )
}
fun RemoveChaveRequest.toDto(): RemoveChaveDto{
    return RemoveChaveDto(
        clienteId = clienteId,
        pixId = pixId
    )
}