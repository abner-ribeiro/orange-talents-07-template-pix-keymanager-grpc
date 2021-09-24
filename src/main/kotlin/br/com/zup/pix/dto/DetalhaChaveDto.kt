package br.com.zup.pix.dto

import io.micronaut.core.annotation.Introspected

@Introspected
data class DetalhaChaveDto(
    val pixId: FiltroPixIdDto,
    val chave: String
) {
    fun isEmpty(): Boolean{
        if((pixId.clienteId.isBlank() || pixId.pixId.isBlank()) && chave.isBlank())
            return true

        return false
    }

    fun isPixIdEmpty(): Boolean{
        if((pixId.clienteId.isBlank() || pixId.pixId.isBlank()))
            return true

        return false
    }

    fun isChaveEmpty(): Boolean{
        if(chave.isBlank())
            return true

        return false
    }
}