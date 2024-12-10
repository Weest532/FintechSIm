import java.util.UUID // For generating unique IDs for accounts and transactions

class Bank {
    // A map to store accounts with account IDs as keys
    private val accounts = mutableMapOf<String, Account>()

    // Creates a new account and returns its unique ID
    fun createAccount(holderName: String): String {
        val accountId = UUID.randomUUID().toString() // Generate a unique account ID
        val account = Account(accountId, holderName, 0.0) // Create a new account with initial balance of 0
        accounts[accountId] = account // Add the account to the accounts map
        println("Account created for $holderName with ID $accountId.") // Confirmation message
        return accountId // Return the newly created account ID
    }

    // Adds a specified amount to the account balance
    fun deposit(accountId: String, amount: Double) {
        val account = accounts[accountId] ?: throw IllegalArgumentException("Account not found") // Get the account or throw an error
        account.balance += amount // Increase the account balance
        // Add a transaction record for the deposit
        account.transactions.add(Transaction(UUID.randomUUID().toString(), TransactionType.DEPOSIT, amount))
        println("Deposited $amount to account $accountId. New balance: ${account.balance}.") // Confirmation message
    }

    // Deducts a specified amount from the account balance
    fun withdraw(accountId: String, amount: Double) {
        val account = accounts[accountId] ?: throw IllegalArgumentException("Account not found") // Get the account or throw an error
        if (account.balance < amount) { // Check for sufficient funds
            println("Insufficient funds.") // Error message
            return
        }
        account.balance -= amount // Decrease the account balance
        // Add a transaction record for the withdrawal
        account.transactions.add(Transaction(UUID.randomUUID().toString(), TransactionType.WITHDRAW, amount))
        println("Withdrew $amount from account $accountId. New balance: ${account.balance}.") // Confirmation message
    }

}
