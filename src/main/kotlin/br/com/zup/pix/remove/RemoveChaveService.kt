package br.com.zup.pix.remove

import br.com.zup.pix.client.ClienteResponse
import br.com.zup.pix.client.ErpItauClient
import br.com.zup.pix.dto.RemoveChaveDto
import br.com.zup.pix.registra.ClienteNaoEncontradoException
import br.com.zup.repositorio.ChavePixRepository
import io.micronaut.http.HttpResponse
import io.micronaut.validation.Validated
import jakarta.inject.Singleton
import java.lang.Exception
import javax.validation.Valid

@Validated
@Singleton
class RemoveChaveService(val chavePixRepository: ChavePixRepository,
                         val erpItauClient: ErpItauClient
) {
    fun validaERemove(@Valid removeChaveDto: RemoveChaveDto){
        try {
            val clientResponse: HttpResponse<ClienteResponse> = erpItauClient.consulta(removeChaveDto.clienteId!!)
        }catch(e: Exception){
            throw ClienteNaoEncontradoException()
        }

        if(!chavePixRepository.existsById(removeChaveDto.pixId))
            throw ChaveNaoEncontradaException()

        chavePixRepository.deleteById(removeChaveDto.pixId)
    }
}