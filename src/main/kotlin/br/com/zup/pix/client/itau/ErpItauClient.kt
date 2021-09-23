package br.com.zup.pix.client.itau

import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.PathVariable
import io.micronaut.http.annotation.QueryValue
import io.micronaut.http.client.annotation.Client

@Client("\${itau.contas.url}")
interface ErpItauClient {

    @Get("/api/v1/clientes/{clientId}")
    fun consulta(@PathVariable clientId: String) : HttpResponse<ClienteResponse>

    @Get("/api/v1/clientes/{clientId}/contas{?tipo}")
    fun consultaConta(@PathVariable clientId: String, @QueryValue tipo:String) : HttpResponse<ContaResponse>
}