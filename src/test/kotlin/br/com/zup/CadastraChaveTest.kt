package br.com.zup
import br.com.zup.models.ChavePix
import br.com.zup.pix.registra.ChavePixCadastradaException
import br.com.zup.repositorio.ChavePixRepository
import io.grpc.ManagedChannel
import io.grpc.Status
import io.grpc.StatusRuntimeException
import io.grpc.stub.AbstractBlockingStub
import io.micronaut.context.annotation.Bean
import io.micronaut.context.annotation.Factory
import io.micronaut.grpc.annotation.GrpcChannel
import io.micronaut.grpc.server.GrpcServerChannel
import io.micronaut.runtime.EmbeddedApplication
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import jakarta.inject.Inject
import org.junit.Assert.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.assertThrows
import java.util.*

@MicronautTest(transactional = false)
internal class CadastraChaveTest(
    val chavePixRepository: ChavePixRepository,
    val grpcClient: KeyManagerRegistraServiceGrpc.KeyManagerRegistraServiceBlockingStub
) {
    val clienteId = "5260263c-a3c1-4727-ae32-3bdb2538841b"
    @BeforeEach
    internal fun setUp() {
        chavePixRepository.deleteAll()
    }

    @Test
    fun `deve cadastrar chave pix`() {
        val response: NovaChaveResponse = grpcClient.cadastraChave(NovaChaveRequest.newBuilder()
            .setClienteId(clienteId)
            .setTipoChave(TipoChave.EMAIL)
            .setChave("abner@teste.com")
            .setTipoConta(TipoConta.CORRENTE)
            .build())

        assertNotNull(response.pixId)
        assertTrue(chavePixRepository.existsByChave("abner@teste.com"))
    }

    @Test
    fun `nao deve cadastrar chave pix repetida`(){
        chavePixRepository.save(ChavePix(clienteId = clienteId,
            tipoChave = TipoChave.EMAIL,
            chave = "abner@teste.com",
            tipoConta = TipoConta.CORRENTE))

        val error = assertThrows<StatusRuntimeException> {
            grpcClient.cadastraChave(NovaChaveRequest.newBuilder()
                .setClienteId(clienteId)
                .setTipoChave(TipoChave.EMAIL)
                .setChave("abner@teste.com")
                .setTipoConta(TipoConta.CORRENTE)
                .build())
        }
        assertEquals(Status.ALREADY_EXISTS.code, error.status.code)
        assertEquals("Já existe essa chave pix cadastrada", error.status.description)
    }

    @Test
    fun `nao deve cadastrar chave pix vazia`() {
        val error = assertThrows<StatusRuntimeException> {
            grpcClient.cadastraChave(NovaChaveRequest.newBuilder()
                .setClienteId(clienteId)
                .setTipoChave(TipoChave.EMAIL)
                .setChave("")
                .setTipoConta(TipoConta.CORRENTE)
                .build())
        }
        assertEquals(Status.INVALID_ARGUMENT.code, error.status.code)
    }

    @Test
    fun `nao deve cadastrar chave pix no formato invalido`() {
        val error = assertThrows<StatusRuntimeException> {
            grpcClient.cadastraChave(NovaChaveRequest.newBuilder()
                .setClienteId(clienteId)
                .setTipoChave(TipoChave.EMAIL)
                .setChave("abc")
                .setTipoConta(TipoConta.CORRENTE)
                .build())
        }
        assertEquals(Status.INVALID_ARGUMENT.code, error.status.code)
        assertEquals("validaECadastra.novaChave: Chave pix informada está no formato inválido", error.status.description)
    }

    @Test
    fun `nao deve cadastrar chave pix caso nao encontre usuario`() {
        val error = assertThrows<StatusRuntimeException> {
            grpcClient.cadastraChave(NovaChaveRequest.newBuilder()
                .setClienteId("abc")
                .setTipoChave(TipoChave.EMAIL)
                .setChave("teste@teste.com")
                .setTipoConta(TipoConta.CORRENTE)
                .build())
        }
        assertEquals(Status.NOT_FOUND.code, error.status.code)
        assertEquals("Cliente com esse clientId não encontrado", error.status.description)
    }
    @Factory
    class Clients {
        @Bean
        fun blockingStub(@GrpcChannel(GrpcServerChannel.NAME) channel: ManagedChannel):KeyManagerRegistraServiceGrpc.KeyManagerRegistraServiceBlockingStub{
            return KeyManagerRegistraServiceGrpc.newBlockingStub(channel)
        }
    }
}
