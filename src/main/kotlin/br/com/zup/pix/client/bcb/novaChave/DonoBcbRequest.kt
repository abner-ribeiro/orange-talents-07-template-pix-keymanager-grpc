package br.com.zup.pix.client.bcb.novaChave

class DonoBcbRequest(
    val type: DonoTipo,
    val name: String,
    val taxIdNumber: String
) {
}
