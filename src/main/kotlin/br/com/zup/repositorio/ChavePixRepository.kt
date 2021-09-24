package br.com.zup.repositorio

import br.com.zup.models.ChavePix
import io.micronaut.data.annotation.Repository
import io.micronaut.data.jpa.repository.JpaRepository
import java.util.*

@Repository
interface ChavePixRepository : JpaRepository<ChavePix, Long> {
    fun existsByChave(chave: String?): Boolean
    fun findByIdAndClienteId(pixId: Long, clienteId: String): Optional<ChavePix>
    fun findByChave(chave: String?): Optional<ChavePix>
    fun findAllByClienteId(clienteId: String): List<ChavePix>
}