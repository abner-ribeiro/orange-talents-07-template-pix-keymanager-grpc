package br.com.zup

import br.com.zup.models.ChavePix
import br.com.zup.repositorio.ChavePixRepository
import io.grpc.ManagedChannel
import io.grpc.Status
import io.grpc.StatusRuntimeException
import io.micronaut.context.annotation.Bean
import io.micronaut.context.annotation.Factory
import io.micronaut.grpc.annotation.GrpcChannel
import io.micronaut.grpc.server.GrpcServerChannel
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import org.junit.Assert
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

@MicronautTest(transactional = false, rebuildContext = true)
internal class RemoveChaveTest(
    val chavePixRepository: ChavePixRepository,
    val grpcClient: KeyManagerRemoveServiceGrpc.KeyManagerRemoveServiceBlockingStub
){
    val clienteId = "5260263c-a3c1-4727-ae32-3bdb2538841b"
    @BeforeEach
    internal fun setUp() {
        chavePixRepository.deleteAll()
    }

    @Test
    fun `deve deletar a chave pix`(){
        chavePixRepository.save(ChavePix(clienteId = clienteId,
            tipoChave = TipoChave.EMAIL,
            chave = "abner@teste.com",
            tipoConta = TipoConta.CORRENTE))

        assertTrue(chavePixRepository.existsById(1L))

        grpcClient.removeChave(RemoveChaveRequest.newBuilder()
            .setPixId(1L)
            .setClienteId(clienteId)
            .build())

        assertFalse(chavePixRepository.existsById(1L))
    }

    @Test
    fun `nao deve deletar chave caso nao encontre usuario`(){
        val error = assertThrows<StatusRuntimeException>{
            grpcClient.removeChave(RemoveChaveRequest.newBuilder()
                .setClienteId("abc")
                .setPixId(1L)
                .build())
        }

        assertEquals(Status.NOT_FOUND.code, error.status.code)
        Assert.assertEquals("Cliente com esse clientId não encontrado", error.status.description)
    }

    @Test
    fun `nao deve deletar chave inexistente`(){
        val error = assertThrows<StatusRuntimeException>{
            grpcClient.removeChave(RemoveChaveRequest.newBuilder()
                .setClienteId(clienteId)
                .setPixId(1L)
                .build())
        }

        assertEquals(Status.NOT_FOUND.code, error.status.code)
        Assert.assertEquals("Chave pix não encontrada", error.status.description)
    }

    @Test
    fun `nao deve deletar caso nao seja enviado um clienteId`(){
        chavePixRepository.save(ChavePix(clienteId = clienteId,
            tipoChave = TipoChave.EMAIL,
            chave = "abner@teste.com",
            tipoConta = TipoConta.CORRENTE))

        assertTrue(chavePixRepository.existsById(1L))

        val error = assertThrows<StatusRuntimeException>{
            grpcClient.removeChave(RemoveChaveRequest.newBuilder()
                .setPixId(1L)
                .build())
        }
        assertEquals(Status.INVALID_ARGUMENT.code, error.status.code)
    }

    @Factory
    class Clients {
        @Bean
        fun blockingStub(@GrpcChannel(GrpcServerChannel.NAME) channel: ManagedChannel):KeyManagerRemoveServiceGrpc.KeyManagerRemoveServiceBlockingStub{
            return KeyManagerRemoveServiceGrpc.newBlockingStub(channel)
        }
    }

}