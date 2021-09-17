package br.com.zup
import br.com.zup.repositorio.ChavePixRepository
import io.grpc.ManagedChannel
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

@MicronautTest(transactional = false)
internal class RegistraChaveTest(
    val chavePixRepository: ChavePixRepository,
    val grpcClient: KeyManagerRegistraServiceGrpc.KeyManagerRegistraServiceBlockingStub
) {
//
//    @Inject
//    lateinit var application: EmbeddedApplication<*>

    @Test
    fun `deve cadastrar chave pix`() {

    }

    @Factory
    class Clients {
        @Bean
        fun blockingStub(@GrpcChannel(GrpcServerChannel.NAME) channel: ManagedChannel):KeyManagerRegistraServiceGrpc.KeyManagerRegistraServiceBlockingStub{
            return KeyManagerRegistraServiceGrpc.newBlockingStub(channel)
        }
    }
}
