import java.sql.Connection

class Bank {
    private val connection: Connection = DatabaseHelper.connect()

    // Create a new account in the database
    fun createAccount(holderName: String): String {
        val accountId = java.util.UUID.randomUUID().toString() // Generate unique account ID
        val sql = "INSERT INTO accounts (account_id, account_holder, balance) VALUES (?, ?, ?)"

        val preparedStatement = connection.prepareStatement(sql)
        preparedStatement.setString(1, accountId)
        preparedStatement.setString(2, holderName)
        preparedStatement.setDouble(3, 0.0) // Initial balance is 0
        preparedStatement.executeUpdate()
        preparedStatement.close()

        println("Account created for $holderName with ID $accountId.")
        return accountId
    }

    // Fetch account details from the database
    fun getAccount(accountId: String): Account? {
        val sql = "SELECT * FROM accounts WHERE account_id = ?"
        val preparedStatement = connection.prepareStatement(sql)
        preparedStatement.setString(1, accountId)

        val resultSet = preparedStatement.executeQuery()
        if (resultSet.next()) {
            val account = Account(
                accountId = resultSet.getString("account_id"),
                accountHolder = resultSet.getString("account_holder"),
                balance = resultSet.getDouble("balance")
            )
            preparedStatement.close()
            return account
        }

        preparedStatement.close()
        return null // Return null if account not found
    }

    // Update account balance in the database
    private fun updateBalance(accountId: String, newBalance: Double) {
        val sql = "UPDATE accounts SET balance = ? WHERE account_id = ?"

        val preparedStatement = connection.prepareStatement(sql)
        preparedStatement.setDouble(1, newBalance)
        preparedStatement.setString(2, accountId)
        preparedStatement.executeUpdate()
        preparedStatement.close()
    }

    // Record a transaction in the database
    private fun recordTransaction(accountId: String, type: TransactionType, amount: Double) {
        val transactionId = java.util.UUID.randomUUID().toString()
        val sql = "INSERT INTO transactions (transaction_id, account_id, type, amount, timestamp) VALUES (?, ?, ?, ?, ?)"

        val preparedStatement = connection.prepareStatement(sql)
        preparedStatement.setString(1, transactionId)
        preparedStatement.setString(2, accountId)
        preparedStatement.setString(3, type.name)
        preparedStatement.setDouble(4, amount)
        preparedStatement.setLong(5, System.currentTimeMillis())
        preparedStatement.executeUpdate()
        preparedStatement.close()
    }

    // Modified deposit method to include database operations
    fun deposit(accountId: String, amount: Double) {
        val account = getAccount(accountId) ?: throw IllegalArgumentException("Account not found")
        val newBalance = account.balance + amount
        updateBalance(accountId, newBalance) // Update balance in the database
        recordTransaction(accountId, TransactionType.DEPOSIT, amount) // Record the transaction
        println("Deposited $amount to account $accountId. New balance: $newBalance.")
    }

    // Modified withdraw method to include database operations
    fun withdraw(accountId: String, amount: Double) {
        val account = getAccount(accountId) ?: throw IllegalArgumentException("Account not found")
        if (account.balance < amount) {
            println("Insufficient funds.")
            return
        }
        val newBalance = account.balance - amount
        updateBalance(accountId, newBalance) // Update balance in the database
        recordTransaction(accountId, TransactionType.WITHDRAW, amount) // Record the transaction
        println("Withdrew $amount from account $accountId. New balance: $newBalance.")
    }
}
