package br.com.zup.pix.registra

import br.com.zup.KeyManagerRegistraServiceGrpc
import br.com.zup.NovaChaveRequest
import br.com.zup.NovaChaveResponse
import br.com.zup.models.ChavePix
import br.com.zup.pix.toDto
import io.grpc.Status
import io.grpc.stub.StreamObserver
import jakarta.inject.Singleton
import org.slf4j.LoggerFactory
import javax.validation.ConstraintViolationException

@Singleton
class RegistraChave(val service: NovaChaveService): KeyManagerRegistraServiceGrpc.KeyManagerRegistraServiceImplBase() {

    private val logger = LoggerFactory.getLogger(RegistraChave::class.java)

    override fun cadastraChave(request: NovaChaveRequest, responseObserver: StreamObserver<NovaChaveResponse>?) {
        val dtoTeste = request.toDto()
        var chave: ChavePix? = null
        try {
            chave = service.validaECadastra(dtoTeste)
        }catch (e: ConstraintViolationException) {
            responseObserver?.onError(
                Status.INVALID_ARGUMENT
                    .withDescription(e.message)
                    .asRuntimeException()
            )
        }catch(e: ChavePixCadastradaException){
            responseObserver?.onError(
                Status.ALREADY_EXISTS
                    .withDescription(e.message)
                    .asRuntimeException()
            )
        }catch (e: Exception){
            responseObserver?.onError(Status.INTERNAL
                .withDescription(e.message)
                .asRuntimeException())
        }
        responseObserver?.onNext(NovaChaveResponse.newBuilder()
            .setPixId(chave?.id!!)
            .build())
        responseObserver?.onCompleted()
    }
}