package hu.bme.aut.sportfogadassegito.team.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Team::class], version = 1)
abstract class TeamDatabase: RoomDatabase() {
    abstract fun teamDao(): TeamDAO


    companion object {
        fun getDatabase(applicationContext: Context): TeamDatabase {
            return Room.databaseBuilder(
                applicationContext,
                TeamDatabase::class.java,
                "team"
            ).build();
        }
    }

}