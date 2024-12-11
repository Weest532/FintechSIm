import java.sql.Connection
import java.sql.DriverManager

object DatabaseHelper {
    private const val DB_URL = "jdbc:sqlite:fintech_simulation.db" // SQLite database file

    // Initialize the database connection
    fun connect(): Connection {
        return DriverManager.getConnection(DB_URL)
    }

    // Initialize tables if they don’t exist
    fun initializeDatabase() {
        val connection = connect()
        val statement = connection.createStatement()

        // SQL for creating accounts table
        val createAccountsTable = """
            CREATE TABLE IF NOT EXISTS accounts (
                account_id TEXT PRIMARY KEY,
                account_holder TEXT NOT NULL,
                balance REAL NOT NULL,
                password TEXT NOT NULL
            )
        """.trimIndent()


        // SQL for creating transactions table
        val createTransactionsTable = """
            CREATE TABLE IF NOT EXISTS transactions (
                transaction_id TEXT PRIMARY KEY,
                account_id TEXT NOT NULL,
                type TEXT NOT NULL,
                amount REAL NOT NULL,
                timestamp INTEGER NOT NULL,
                FOREIGN KEY(account_id) REFERENCES accounts(account_id)
            )
        """.trimIndent()

        // Execute table creation
        statement.execute(createAccountsTable)
        statement.execute(createTransactionsTable)
        statement.close()
        connection.close()
    }
}
