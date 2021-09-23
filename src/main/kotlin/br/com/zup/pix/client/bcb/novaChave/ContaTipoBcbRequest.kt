package br.com.zup.pix.client.bcb.novaChave

import br.com.zup.TipoConta

enum class ContaTipoBcbRequest {
    CACC, SVGS, UNKNOWN;

    companion object {
        fun by(domainType: TipoConta): ContaTipoBcbRequest {
            return when (domainType) {
                TipoConta.CONTA_CORRENTE -> CACC
                TipoConta.CONTA_POUPANCA -> SVGS
                else -> UNKNOWN
            }
        }
    }
}
