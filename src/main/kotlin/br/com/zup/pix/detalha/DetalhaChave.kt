package br.com.zup.pix.remove

import br.com.zup.*
import br.com.zup.models.ChavePix
import br.com.zup.pix.client.bcb.detalhaChave.DetalhaChavePixBcbResponse
import br.com.zup.pix.detalha.DetalhaChaveService
import br.com.zup.pix.registra.ClienteNaoEncontradoException
import br.com.zup.pix.toDto
import com.google.protobuf.Empty
import com.google.protobuf.Timestamp
import io.grpc.Status
import io.grpc.stub.StreamObserver
import jakarta.inject.Singleton
import org.slf4j.LoggerFactory
import java.lang.IllegalStateException
import java.time.LocalDateTime
import java.time.ZoneId
import javax.validation.ConstraintViolationException

@Singleton
class DetalhaChave(val detalhaChaveService: DetalhaChaveService) : KeyManagerDetalhaServiceGrpc.KeyManagerDetalhaServiceImplBase() {

    private val logger = LoggerFactory.getLogger(RemoveChave::class.java)

    override fun encontraChave(
        request: EncontraChaveRequest,
        responseObserver: StreamObserver<EncontraChaveResponse>
    ) {
        var chaveInfo: ChavePix? = null
        val detalhaChaveDto = request.toDto()
        try{
           chaveInfo = detalhaChaveService.validaEDetalha(detalhaChaveDto)
        }catch (e: ChaveNaoEncontradaException){
            responseObserver?.onError(
                Status.NOT_FOUND
                    .withDescription(e.message)
                    .asRuntimeException()
            )
        }catch (e: IllegalStateException){
            responseObserver?.onError(Status.INTERNAL
                .withDescription(e.message)
                .asRuntimeException())
        } catch (e: Exception){
            responseObserver?.onError(Status.INTERNAL
                .withDescription(e.message)
                .asRuntimeException())
        }

        responseObserver?.onNext(EncontraChaveResponse.newBuilder()
            .setClienteId(chaveInfo?.clienteId?.toString() ?: "")
            .setPixId(chaveInfo?.id?.toString() ?: "")
            .setChave(EncontraChaveResponse.ChavePix
                .newBuilder()
                .setTipo(chaveInfo?.tipoChave?.name?.let { TipoChave.valueOf(it) })
                .setChave(chaveInfo?.chave)
                .setConta(EncontraChaveResponse.ChavePix.ContaInfo.newBuilder()
                    .setTipo(chaveInfo?.tipoConta?.name?.let { TipoConta.valueOf(it) })
                    .setInstituicao(chaveInfo?.conta?.instituicao)
                    .setNomeDoTitular(chaveInfo?.conta?.nomeDoTitular)
                    .setCpfDoTitular(chaveInfo?.conta?.cpfDoTitular)
                    .setAgencia(chaveInfo?.conta?.agencia)
                    .setNumeroDaConta(chaveInfo?.conta?.numeroDaConta)
                    .build()
                )
                .setCriadaEm(chaveInfo?.criadaEm?.let {
                    val createdAt = it.atZone(ZoneId.of("UTC")).toInstant()
                    Timestamp.newBuilder()
                        .setSeconds(createdAt.epochSecond)
                        .setNanos(createdAt.nano)
                        .build()
                })
            )
            .build()
        )
        responseObserver?.onCompleted()
    }
}