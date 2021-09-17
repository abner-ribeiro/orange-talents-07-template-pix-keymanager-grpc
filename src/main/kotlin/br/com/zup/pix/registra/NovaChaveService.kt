package br.com.zup.pix.registra

import br.com.zup.models.ChavePix
import br.com.zup.pix.dto.NovaChaveDto
import br.com.zup.repositorio.ChavePixRepository
import io.micronaut.validation.Validated
import jakarta.inject.Singleton
import org.slf4j.LoggerFactory
import javax.validation.ConstraintViolationException
import javax.validation.Valid

@Validated
@Singleton
class NovaChaveService (val chavePixRepository: ChavePixRepository){
    val logger = LoggerFactory.getLogger(NovaChaveService::class.java)

    fun validaECadastra(@Valid novaChave: NovaChaveDto): ChavePix{
        if(chavePixRepository.existsByChave(novaChave.chave)){
            throw ChavePixCadastradaException()
        }

        val chave = novaChave.toModel()

        chavePixRepository.save(chave)

        return chave
    }
}