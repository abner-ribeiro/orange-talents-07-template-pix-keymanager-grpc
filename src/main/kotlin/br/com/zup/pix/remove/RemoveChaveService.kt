package br.com.zup.pix.remove

import br.com.zup.pix.client.bcb.BancoCentralClient
import br.com.zup.pix.client.bcb.deletaChave.DeletaChavePixBcbRequest
import br.com.zup.pix.client.itau.ClienteResponse
import br.com.zup.pix.client.itau.ErpItauClient
import br.com.zup.pix.dto.RemoveChaveDto
import br.com.zup.pix.registra.ClienteNaoEncontradoException
import br.com.zup.repositorio.ChavePixRepository
import io.micronaut.http.HttpResponse
import io.micronaut.http.HttpStatus
import io.micronaut.validation.Validated
import jakarta.inject.Singleton
import java.lang.Exception
import javax.validation.Valid

@Validated
@Singleton
class RemoveChaveService(val chavePixRepository: ChavePixRepository,
                         val erpItauClient: ErpItauClient,
                         val bcbClient: BancoCentralClient
) {
    fun validaERemove(@Valid removeChaveDto: RemoveChaveDto){
        try {
            val clientResponse: HttpResponse<ClienteResponse> = erpItauClient.consulta(removeChaveDto.clienteId!!)
        }catch(e: Exception){
            throw ClienteNaoEncontradoException()
        }

        val possivelChave = chavePixRepository.findByIdAndClienteId(
            removeChaveDto.pixId,
            removeChaveDto.clienteId
        )
        if(possivelChave.isEmpty)
            throw ChaveNaoEncontradaException()

        val chave = possivelChave.get()

        val bcbResponse = bcbClient.delete(chave.chave!!, DeletaChavePixBcbRequest(chave.chave!!))

        if (bcbResponse.status != HttpStatus.OK) {
            throw IllegalStateException("Erro ao remover chave Pix no Banco Central do Brasil (BCB)")
        }

        chavePixRepository.deleteById(removeChaveDto.pixId)
    }
}