package br.com.zup.pix.registra

import br.com.zup.models.ChavePix
import br.com.zup.pix.client.bcb.*
import br.com.zup.pix.client.bcb.novaChave.*
import br.com.zup.pix.client.itau.ContaResponse
import br.com.zup.pix.client.itau.ErpItauClient
import br.com.zup.pix.dto.NovaChaveDto
import br.com.zup.repositorio.ChavePixRepository
import io.micronaut.http.HttpResponse
import io.micronaut.http.HttpStatus
import io.micronaut.validation.Validated
import jakarta.inject.Singleton
import org.slf4j.LoggerFactory
import java.lang.Exception
import java.lang.IllegalStateException
import javax.transaction.Transactional
import javax.validation.Valid

@Validated
@Singleton
class NovaChaveService (val chavePixRepository: ChavePixRepository,
                        val erpItauClient: ErpItauClient,
                        val bcbClient: BancoCentralClient
){
    val logger = LoggerFactory.getLogger(NovaChaveService::class.java)

    @Transactional
    fun validaECadastra(@Valid novaChave: NovaChaveDto): ChavePix{
        logger.info(novaChave.clienteId!!)
        var contaResponse:HttpResponse<ContaResponse>
        try {
            contaResponse = erpItauClient.consultaConta(novaChave.clienteId!!, novaChave.tipoConta!!.name)
        }catch(e: Exception){
            throw ClienteNaoEncontradoException()
        }
        if(chavePixRepository.existsByChave(novaChave.chave)){
            throw ChavePixCadastradaException()
        }

        val chave = novaChave.toModel(contaResponse.body().toModel())

        val chavePixResponse: HttpResponse<NovaChavePixBcbResponse> = bcbClient.create(NovaChavePixBcbRequest.of(chave))

        if(chavePixResponse.status != HttpStatus.CREATED){
            throw IllegalStateException("Erro ao registrar chave Pix no Banco Central do Brasil (BCB)")
        }
        chave.atualizaChaveAleatoria(chavePixResponse.body().key)

        chavePixRepository.save(chave)

        return chave
    }
}