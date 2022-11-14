package hu.bme.aut.sportfogadassegito.team.data

import androidx.room.*
import hu.bme.aut.sportfogadassegito.meccs.data.Meccs

@Dao
interface TeamDAO {
    @Query("SELECT * FROM team")
    fun getAll(): List<Team>

    @Query("SELECT name FROM team")
    fun getAllNames(): List<String>

    @Insert
    fun insert(team: Team): Long

    @Update
    fun update(team: Team)

    @Delete
    fun deleteItem(team: Team)
}