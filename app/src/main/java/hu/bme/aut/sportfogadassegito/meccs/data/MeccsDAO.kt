package hu.bme.aut.sportfogadassegito.meccs.data

import androidx.room.*
import hu.bme.aut.sportfogadassegito.szelveny.data.Szelveny


@Dao
interface MeccsDAO {
    @Query("SELECT * FROM meccsek WHERE szelvenyId= :szelvenyNumber")
    fun getSelected(szelvenyNumber: Long): List<Meccs>

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
}




