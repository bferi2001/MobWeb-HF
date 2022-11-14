package hu.bme.aut.sportfogadassegito.szelveny.data

import android.content.Context
import androidx.room.*


@Database(entities = [Szelveny::class], version = 1)
abstract class SzelvenyDatabase: RoomDatabase(){
    abstract fun szelvenyDao(): SzelvenyDAO

    companion object {
        fun getDatabase(applicationContext: Context): SzelvenyDatabase {
            return Room.databaseBuilder(
                applicationContext,
                SzelvenyDatabase::class.java,
                "szelveny"
            ).build();
        }
    }


}