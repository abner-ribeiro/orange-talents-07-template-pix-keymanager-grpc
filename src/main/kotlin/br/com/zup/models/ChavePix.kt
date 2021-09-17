package br.com.zup.models

import br.com.zup.TipoChave
import br.com.zup.TipoConta
import java.util.*
import javax.persistence.*
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull
import javax.validation.constraints.Size

@Entity
class ChavePix(
    @field: NotBlank val clienteId: String?,
    @field: NotNull val tipoChave: TipoChave?,
    @field: Size(max=77) val chave: String? =  UUID.randomUUID().toString(),
    @field: NotNull @field: Enumerated(EnumType.STRING) val tipoConta: TipoConta?
) {
    @field: Id @field: GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null
}