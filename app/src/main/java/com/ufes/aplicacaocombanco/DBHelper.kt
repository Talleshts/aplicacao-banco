package com.ufes.aplicacaocombanco

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DBHelper(context: Context) : SQLiteOpenHelper(context, "database.db", null, 1) {

    // Criação da tabela usuarios
    override fun onCreate(db: SQLiteDatabase?) {
        // Criando a tabela 'usuarios' com nomeUsuario e senhaUsuario
        val createTableQuery = """
            CREATE TABLE usuarios (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                nomeUsuario TEXT,
                senhaUsuario TEXT
            );
        """
        db?.execSQL(createTableQuery)

        // Dados mock para inserção na tabela
        val usuariosMock = listOf(
            Pair("usuario1", "senha123"),
            Pair("usuario2", "senha456"),
            Pair("usuario3", "senha789")
        )

        // Inserindo os dados mock no banco de dados
        for (usuario in usuariosMock) {
            val contentValues = ContentValues().apply {
                put("nomeUsuario", usuario.first)
                put("senhaUsuario", usuario.second)
            }
            db?.insert("usuarios", null, contentValues)
        }
    }

    // Método para atualizar a versão do banco de dados
    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        // Se houver uma atualização, a tabela anterior será deletada e uma nova será criada
        db?.execSQL("DROP TABLE IF EXISTS usuarios")
        onCreate(db)
    }

    // Função para selecionar todos os registros de usuários e retornar um Cursor
    fun selectAllUsuarios(): Cursor {
        val db = this.readableDatabase
        return db.rawQuery("SELECT * FROM usuarios", null)
    }

    // Função para selecionar todos os registros de usuários e retornar uma lista de objetos Usuario
    fun listSelectAllUsuarios(): ArrayList<Usuario> {
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM usuarios", null)
        val listaUsuarios: ArrayList<Usuario> = ArrayList()

        // Verificando se há registros na tabela
        if (cursor.count > 0) {
            cursor.moveToFirst() // Posicionando o cursor no primeiro registro
            do {
                // Obtendo os índices das colunas
                val idIndex = cursor.getColumnIndex("id")
                val nomeIndex = cursor.getColumnIndex("nomeUsuario")
                val senhaIndex = cursor.getColumnIndex("senhaUsuario")

                // Obtendo os dados do registro
                val id = cursor.getInt(idIndex)
                val nomeUsuario = cursor.getString(nomeIndex)
                val senhaUsuario = cursor.getString(senhaIndex)

                // Criando o objeto usuario
                val usuario = Usuario(id, nomeUsuario, senhaUsuario)

                // Adicionando o usuário à lista
                listaUsuarios.add(usuario)

            } while (cursor.moveToNext()) // Movendo para o próximo registro
        }
        //cursor.close() // Fechando o cursor
        db.close() // Fechando o banco
        return listaUsuarios
    }

    //Função para inserir o usuario
    fun usuarioInsert(nome: String, senha: String): Long {
        val db = this.writableDatabase
        val contentValues = ContentValues().apply {
            put("nomeUsuario", nome)
            put("senhaUsuario", senha)
        }
        val res = db.insert("usuarios", null, contentValues)
        db.close()
        return res
    }

}
