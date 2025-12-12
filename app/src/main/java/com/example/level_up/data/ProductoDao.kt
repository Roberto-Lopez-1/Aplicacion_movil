package com.example.level_up.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.level_up.model.Producto

@Dao
interface ProductoDao {
    @Insert
    suspend fun insertar(producto: Producto)

    @Update
    suspend fun actualizar(producto: Producto)

    @Delete
    suspend fun eliminar(producto: Producto)

    @Query("SELECT * FROM producto")
    suspend fun obtenerTodos(): List<Producto>

    @Query("SELECT * FROM producto WHERE id = :id")
    suspend fun obtenerPorId(id: Int): Producto?
}