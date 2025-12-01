package com.example.level_up.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.level_up.model.Producto
import com.example.level_up.model.Usuario
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(
    entities = [
        Usuario::class,
        Producto::class
    ],
    version = 1
)

abstract class LevelupDatabase : RoomDatabase() {
    abstract fun usuarioDao(): UsuarioDao
    abstract fun productoDao(): ProductoDao

    companion object {
        private var database: LevelupDatabase? = null

        fun getDatabase(context: Context): LevelupDatabase {
            if (database == null) {
                database = Room.databaseBuilder(
                    context,
                    LevelupDatabase::class.java,
                    "levelup.db"
                )
                    .fallbackToDestructiveMigration()
                    .addCallback(object: Callback() {
                        override fun onCreate(db: SupportSQLiteDatabase) {
                            super.onCreate(db)
                            CoroutineScope(Dispatchers.IO).launch {
                                insertarDatosPorDefecto(database!!)
                            }
                        }
                    })
                .setJournalMode(JournalMode.TRUNCATE)
                .build()
            }
            return database!!
        }
        private suspend fun insertarDatosPorDefecto(db: LevelupDatabase) {
            val usuarioDao = db.usuarioDao()

            val usuarios = listOf(
                Usuario(nombre = "Daniel", email = "daniel@duocuc.cl", password = "password"),
                Usuario(nombre = "Andres", email = "andres@duocuc.cl", password = "password"),
                Usuario(nombre = "Admin", email = "admin", password = "admin")
            )

            usuarios.forEach { usuarioDao.insertar(it) }

            val productoDao = db.productoDao()
            val productos = listOf(
                Producto(nombre = "Xbox Series X", precio = 599990, imagen = "xbox.png"),
                Producto(nombre = "PlayStation 5", precio = 459000, imagen = "playstation.png"),
                Producto(nombre = "PC Gamer", precio = 659990, imagen = "pcgamer.png")
            )

            productos.forEach { productoDao.insertar(it) }
        }
    }
}
