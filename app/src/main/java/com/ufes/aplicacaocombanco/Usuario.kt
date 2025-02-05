package com.ufes.aplicacaocombanco

data class Usuario(
    val id: Int = 0,
    val nomeUsuario: String = "",
    val senhaUsuario: String = ""
) {
    // Sobrescrevendo o método toString para uma representação mais legível do objeto
    override fun toString(): String {
        return "Usuario(ID=$id, nomeUsuario='$nomeUsuario', senhaUsuario='$senhaUsuario')"
    }
}
