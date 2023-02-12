package hu.bme.aut.sportfogadassegito.meccs.data

import androidx.room.*

@Entity(tableName = "meccsek")
data class Meccs(
    @ColumnInfo(name = "id") @PrimaryKey(autoGenerate = true) var id: Long? = null,
    @ColumnInfo(name = "szelvenyId") var szelvenyId: Long,
    @ColumnInfo(name = "expectedWinner") var expectedWinner: Boolean,
    @ColumnInfo(name = "homeName") var homeName: String,
    @ColumnInfo(name = "awayName") var awayName: String,
    @ColumnInfo(name = "awayScore") var awayScore: Int,
    @ColumnInfo(name = "homeScore") var homeScore: Int,
    @ColumnInfo(name = "date") var date: String = "2023.02.04.",
    @ColumnInfo(name = "odds") var odds: Double,
    @ColumnInfo(name = "isWin") var isWin: Boolean,
    @ColumnInfo(name = "isEnded") var isEnded: Boolean
)
//ToDo Mikor van a meccs? + DatePicker