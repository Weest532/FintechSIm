// Represents a bank account with details like ID, holder name, balance, and transaction history.
data class Account(
    val accountId: String, // Unique identifier for the account
    val accountHolder: String, // Name of the account holder
    var balance: Double, // Current balance in the account
    val transactions: MutableList<Transaction> = mutableListOf() // List of transactions for the account
)

// Represents a single transaction (e.g., deposit, withdraw, or transfer).
data class Transaction(
    val transactionId: String, // Unique ID for the transaction
    val type: TransactionType, // Type of the transaction (e.g., DEPOSIT, WITHDRAW)
    val amount: Double, // Amount involved in the transaction
    val timestamp: Long = System.currentTimeMillis() // Timestamp of when the transaction occurred
)

// Enum to specify types of transactions for clarity and safety.
enum class TransactionType {
    DEPOSIT, WITHDRAW, TRANSFER
}