/*import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [UserEntity::class], version = 1)
abstract class DemoDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao

    companion object {
        private const val DB_NAME = "demo-database"
        private var INSTANCE: DemoDatabase? = null
        fun getDatabase(context: Context): DemoDatabase {
            return INSTANCE ?: buildDatabase(context).also {
                INSTANCE = it
            }
        }

        private fun buildDatabase(context: Context): DemoDatabase {
            val ioDispatcherExecutor = Dispatchers.IO.asExecutor()
            return Room
                .databaseBuilder(
                    context,
                    DemoDatabase::class.java,
                    DB_NAME
                )
                .setQueryExecutor(ioDispatcherExecutor)
                .setTransactionExecutor(ioDispatcherExecutor)
                .build()
        }
    }
}*/