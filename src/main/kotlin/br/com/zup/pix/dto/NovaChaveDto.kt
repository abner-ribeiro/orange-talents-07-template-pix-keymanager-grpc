package br.com.zup.pix.dto

import br.com.zup.TipoChave
import br.com.zup.TipoConta
import br.com.zup.models.ChavePix
import br.com.zup.models.Conta
import br.com.zup.pix.annotations.ChavePixPattern
import io.micronaut.core.annotation.Introspected
import java.util.*
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull
import javax.validation.constraints.Size

@Introspected
@ChavePixPattern
data class NovaChaveDto(
    @field: NotBlank val clienteId: String?,
    @field: NotNull val tipoChave: TipoChave?,
    @field: Size(max=77) val chave: String?,
    @field: NotNull val tipoConta: TipoConta?
    ) {
    fun toModel(conta: Conta): ChavePix {
        return ChavePix(
            clienteId = clienteId,
            tipoChave = tipoChave,
            chave = if(tipoChave == TipoChave.ALEATORIA) UUID.randomUUID().toString() else chave,
            tipoConta = tipoConta,
            conta = conta
        )
    }
}