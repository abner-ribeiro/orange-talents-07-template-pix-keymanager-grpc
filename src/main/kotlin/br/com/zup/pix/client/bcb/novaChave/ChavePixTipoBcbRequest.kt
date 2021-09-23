package br.com.zup.pix.client.bcb.novaChave

import br.com.zup.TipoChave
import br.com.zup.TipoConta

enum class ChavePixTipoBcbRequest{
    CPF,
    CNPJ,
    PHONE,
    EMAIL,
    UNKNOWN,
    RANDOM;

    companion object {
        fun by(domainType: TipoChave): ChavePixTipoBcbRequest {
            return when (domainType) {
                TipoChave.TELEFONE -> PHONE
                TipoChave.EMAIL -> EMAIL
                TipoChave.CPF -> CPF
                TipoChave.CNPJ -> CNPJ
                TipoChave.ALEATORIA -> RANDOM
                else -> UNKNOWN
            }
        }
    }
}
