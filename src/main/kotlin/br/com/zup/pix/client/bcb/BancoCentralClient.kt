package br.com.zup.pix.client.bcb

import br.com.zup.pix.client.bcb.deletaChave.DeletaChavePixBcbRequest
import br.com.zup.pix.client.bcb.deletaChave.DeletaChavePixBcbResponse
import br.com.zup.pix.client.bcb.novaChave.NovaChavePixBcbRequest
import io.micronaut.http.HttpResponse
import io.micronaut.http.MediaType
import io.micronaut.http.annotation.*
import io.micronaut.http.client.annotation.Client

@Client("\${bcb.pix.url}")
interface BancoCentralClient {
    @Post("/api/v1/pix/keys",
        produces = [MediaType.APPLICATION_XML],
        consumes = [MediaType.APPLICATION_XML]
    )
    fun create(@Body request: NovaChavePixBcbRequest): HttpResponse<NovaChavePixBcbResponse>

    @Delete("/api/v1/pix/keys/{key}",
        produces = [MediaType.APPLICATION_XML],
        consumes = [MediaType.APPLICATION_XML]
    )
    fun delete(@PathVariable key: String, @Body request: DeletaChavePixBcbRequest): HttpResponse<DeletaChavePixBcbResponse>

    @Get("/api/v1/pix/keys/{key}",
        produces = [MediaType.APPLICATION_XML],
        consumes = [MediaType.APPLICATION_XML]
    )
    fun findByKey(@PathVariable key: String): HttpResponse<DetalhaChavePixBcbRequest>
}