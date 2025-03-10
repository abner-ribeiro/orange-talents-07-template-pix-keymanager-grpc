package br.com.zup.pix.dto

import io.micronaut.core.annotation.Introspected
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull

@Introspected
data class RemoveChaveDto (
    @field: NotBlank val clienteId: String,
    @field: NotNull val pixId: Long
        ){

}
