package br.com.zup.pix.lista

import br.com.zup.*
import br.com.zup.models.ChavePix
import br.com.zup.pix.toDto
import br.com.zup.repositorio.ChavePixRepository
import com.google.protobuf.Timestamp
import io.grpc.Status
import io.grpc.stub.StreamObserver
import jakarta.inject.Singleton
import org.slf4j.LoggerFactory
import java.lang.IllegalStateException
import java.time.ZoneId
import javax.validation.ConstraintViolationException

@Singleton
class ListaChave(val chavePixRepository: ChavePixRepository): KeyManagerListaServiceGrpc.KeyManagerListaServiceImplBase() {

    private val logger = LoggerFactory.getLogger(ListaChave::class.java)

    override fun listaChave(request: ListaChaveRequest, responseObserver: StreamObserver<ListaChaveResponse>) {
        val chaves = chavePixRepository.findAllByClienteId(request.clienteId)
        var chavesResponse: MutableList<ChaveResponse> = mutableListOf()

        chaves.forEach { chavePix ->
            chavesResponse.add(ChaveResponse.newBuilder()
                .setPixId(chavePix.id.toString())
                .setClienteId(chavePix.clienteId)
                .setTipoChave(chavePix.tipoChave)
                .setChave(chavePix.chave)
                .setTipoConta(chavePix.tipoConta)
                .setCriadaEm(
                    chavePix?.criadaEm?.let {
                        val createdAt = it.atZone(ZoneId.of("UTC")).toInstant()
                        Timestamp.newBuilder()
                            .setSeconds(createdAt.epochSecond)
                            .setNanos(createdAt.nano)
                            .build()
                    })
                .build())
        }
        val chavesResponseList = ListaChaveResponse.newBuilder().addAllChaves(chavesResponse).build()
        responseObserver?.onNext(chavesResponseList)
        responseObserver?.onCompleted()
    }
}