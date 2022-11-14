package hu.bme.aut.sportfogadassegito.meccs.data

import android.content.Context
import androidx.room.*

@Database(entities = [Meccs::class], version = 1)
abstract class MeccsDatabase: RoomDatabase() {
    abstract fun meccsDao(): MeccsDAO


    companion object {
        fun getDatabase(applicationContext: Context): MeccsDatabase {
            return Room.databaseBuilder(
                applicationContext,
                MeccsDatabase::class.java,
                "meccsek"
            ).build();
        }
    }

}