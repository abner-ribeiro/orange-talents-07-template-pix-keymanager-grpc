package br.com.zup.pix.client.bcb.novaChave

class ContaBcbRequest (
    val participant: String,
    val branch: String,
    val accountNumber: String,
    val accountType: ContaTipoBcbRequest
    ){
}
