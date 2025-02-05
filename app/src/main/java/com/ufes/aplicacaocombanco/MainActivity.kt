package com.ufes.aplicacaocombanco

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.ufes.aplicacaocombanco.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: ArrayAdapter<Usuario>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Operações com o Banco
        val db = DBHelper(this)

        //Listar usuarios
        listarUsuario(db)

        //Inserir Usuario
        inserirUsuario(db)
    }

    fun inserirUsuario(db: DBHelper){
        binding.buttonInserir.setOnClickListener {
            val nomeUsuario = binding.editNomeUsuario.text.toString()
            val senhaUsuario = binding.editSenhaUsuario.text.toString()

            if (nomeUsuario.isNotEmpty() && senhaUsuario.isNotEmpty()) {
                // Inserir usuário no banco de dados
                val resultado = db.usuarioInsert(nomeUsuario, senhaUsuario)
                validar(resultado)

            }else {
                Toast.makeText(this, "Preencha todos os campos.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun listarUsuario(db: DBHelper){
        val listaUsuarios = db.listSelectAllUsuarios()
        adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, listaUsuarios )
        binding.ListViewUsuarios.adapter = adapter
    }

    fun validar(resultado: Long){
        val db = DBHelper(this)
        // Exibir mensagem de sucesso ou erro
        if (resultado > 0) {
            Toast.makeText(this, "Usuário inserido com sucesso!", Toast.LENGTH_SHORT).show()
            // Atualizar a lista de usuários
            val listaAtualizada = db.listSelectAllUsuarios()
            adapter.clear()
            adapter.addAll(listaAtualizada)
            adapter.notifyDataSetChanged()
        } else {
            Toast.makeText(this, "Erro ao inserir usuário.", Toast.LENGTH_SHORT).show()
        }
    }
}