package br.com.zup.pix.remove

import br.com.zup.KeyManagerRegistraServiceGrpc
import br.com.zup.KeyManagerRemoveServiceGrpc
import br.com.zup.NovaChaveResponse
import br.com.zup.RemoveChaveRequest
import br.com.zup.pix.registra.ClienteNaoEncontradoException
import br.com.zup.pix.toDto
import com.google.protobuf.Empty
import io.grpc.Status
import io.grpc.stub.StreamObserver
import jakarta.inject.Singleton
import org.slf4j.LoggerFactory
import java.lang.IllegalStateException
import javax.validation.ConstraintViolationException

@Singleton
class RemoveChave(val removeChaveService: RemoveChaveService) : KeyManagerRemoveServiceGrpc.KeyManagerRemoveServiceImplBase() {

    private val logger = LoggerFactory.getLogger(RemoveChave::class.java)

    override fun removeChave(request: RemoveChaveRequest, responseObserver: StreamObserver<Empty>?) {
        val removeChaveDto = request.toDto()

        try{
            removeChaveService.validaERemove(removeChaveDto)
        }catch (e: IllegalStateException){
            responseObserver?.onError(Status.INTERNAL
                .withDescription(e.message)
                .asRuntimeException())
        }
        catch (e: ConstraintViolationException){
            responseObserver?.onError(
                Status.INVALID_ARGUMENT
                    .withDescription(e.message)
                    .asRuntimeException()
            )
        }catch (e: ClienteNaoEncontradoException){
            responseObserver?.onError(
                Status.NOT_FOUND
                    .withDescription(e.message)
                    .asRuntimeException()
            )
        }catch (e: ChaveNaoEncontradaException){
            responseObserver?.onError(
                Status.NOT_FOUND
                    .withDescription(e.message)
                    .asRuntimeException()
            )
        }
        responseObserver?.onNext(Empty.getDefaultInstance())
        responseObserver?.onCompleted()
    }
}