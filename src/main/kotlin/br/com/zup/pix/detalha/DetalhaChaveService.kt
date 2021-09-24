package br.com.zup.pix.detalha

import br.com.zup.models.ChavePix
import br.com.zup.pix.client.bcb.BancoCentralClient
import br.com.zup.pix.dto.DetalhaChaveDto
import br.com.zup.pix.remove.ChaveNaoEncontradaException
import br.com.zup.repositorio.ChavePixRepository
import io.micronaut.http.HttpStatus
import io.micronaut.validation.Validated
import jakarta.inject.Singleton
import javax.validation.Valid
@Singleton
class DetalhaChaveService(
    val chavePixRepository: ChavePixRepository,
    val bcbClient: BancoCentralClient
) {
    fun validaEDetalha(detalhaChaveDto: DetalhaChaveDto): ChavePix{
        if(detalhaChaveDto.isEmpty())
            throw FiltroInvalidoException()

        if(detalhaChaveDto.isPixIdEmpty()){
            val possivelChave = chavePixRepository.findByChave(detalhaChaveDto.chave)
            if(possivelChave.isPresent){
                return possivelChave.get()
            }else{
                val chaveDetalhadaResponse = bcbClient.findByKey(detalhaChaveDto.chave)
                if(chaveDetalhadaResponse.status != HttpStatus.OK)
                    throw IllegalStateException("Erro ao detalhar chave Pix pelo Banco Central do Brasil (BCB)")

                return chaveDetalhadaResponse.body().toModel()
            }
        }

        val possivelChave = chavePixRepository.findByIdAndClienteId(
            detalhaChaveDto.pixId.pixId.toLong(),
            detalhaChaveDto.pixId.clienteId
        )

        if(possivelChave.isEmpty)
            throw ChaveNaoEncontradaException()

        return possivelChave.get()
    }
}
