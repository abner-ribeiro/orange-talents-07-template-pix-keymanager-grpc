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
    @field: Size(max=77) var chave: String? =  UUID.randomUUID().toString(),
    @field: NotNull @field: Enumerated(EnumType.STRING) val tipoConta: TipoConta?,
    @Embedded
    val conta: Conta
) {
    @field: Id @field: GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null
    override fun toString(): String {
        return "ChavePix(clienteId=$clienteId, tipoChave=$tipoChave, chave=$chave, tipoConta=$tipoConta, conta=$conta, id=$id)"
    }

    fun atualizaChaveAleatoria(chave: String): Boolean {
        if (this.tipoChave == TipoChave.ALEATORIA) {
            this.chave = chave
            return true
        }
        return false
    }

}