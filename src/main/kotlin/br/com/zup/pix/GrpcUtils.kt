package br.com.zup.pix

import br.com.zup.NovaChaveRequest
import br.com.zup.TipoChave
import br.com.zup.TipoConta
import br.com.zup.pix.dto.NovaChaveDto

fun NovaChaveRequest.toDto(): NovaChaveDto {
    return NovaChaveDto(
        clienteId = clienteId,
        tipoChave = if(tipoChave == TipoChave.CHAVE_DESCONHECIDA) null else tipoChave,
        chave = chave,
        tipoConta = if(tipoConta == TipoConta.CONTA_DESCONHECIDA) null else tipoConta
    )
}