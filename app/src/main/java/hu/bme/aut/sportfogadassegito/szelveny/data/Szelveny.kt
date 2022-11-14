package hu.bme.aut.sportfogadassegito.szelveny.data

import androidx.room.*
import java.util.*

@Entity(tableName = "szelveny")
data class Szelveny (
    @ColumnInfo(name = "id") @PrimaryKey(autoGenerate = true) var id: Long? = null,
    @ColumnInfo(name = "discription") var discription: String,
    @ColumnInfo(name = "date") var date: String,
    @ColumnInfo(name = "price") var price: Int,
    @ColumnInfo(name = "expected_prize") var expectedPrize: Int,
    @ColumnInfo(name = "is_Win") var isWin: Boolean,
    @ColumnInfo(name = "is_Ended") var isEnded: Boolean
)

