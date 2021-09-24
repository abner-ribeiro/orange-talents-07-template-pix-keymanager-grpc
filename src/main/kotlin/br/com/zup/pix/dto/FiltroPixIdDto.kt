package br.com.zup.pix.dto

import io.micronaut.core.annotation.Introspected

@Introspected
data class FiltroPixIdDto(
    val clienteId: String,
    val pixId: String
){

}
