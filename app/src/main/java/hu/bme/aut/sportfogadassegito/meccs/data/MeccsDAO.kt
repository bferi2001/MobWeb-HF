package hu.bme.aut.sportfogadassegito.meccs.data

import androidx.room.*
import hu.bme.aut.sportfogadassegito.szelveny.data.Szelveny


@Dao
interface MeccsDAO {
    @Query("SELECT * FROM meccsek")
    fun getAll(): List<Meccs>
    @Query("SELECT * FROM meccsek WHERE szelvenyId= :szelvenyNumber")
    fun getSelected(szelvenyNumber: Long): List<Meccs>

    @Query("SELECT * FROM meccsek WHERE homeName= :TeamName OR awayName = :TeamName")
    fun getMeccsekForTeam(TeamName: String): List<Meccs>

    @Insert
    fun insert(meccs: Meccs): Long

    @Update
    fun update(meccs: Meccs)

    @Delete
    fun deleteItem(meccs: Meccs)

    @Delete
    fun deleteSelectedSzelveny(szelveny: Szelveny){
        for(items in getSelected(szelveny.id!!)){
            deleteItem(items)
        }
    }
    @Delete
    fun deleteAll(){
        for(items in getAll()){
            deleteItem(items)
        }
    }
}




