package hu.bme.aut.sportfogadassegito.szelveny.data

import androidx.room.*

@Dao
interface SzelvenyDAO {
    @Query("SELECT * FROM szelveny")
    fun getAll(): List<Szelveny>

    @Insert
    fun insert(shoppingItems: Szelveny): Long

    @Update
    fun update(shoppingItem: Szelveny)

    @Delete
    fun deleteItem(shoppingItem: Szelveny)
}