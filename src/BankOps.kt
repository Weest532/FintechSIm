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
    // Transfers a specified amount from one account to another
    fun transfer(fromAccountId: String, toAccountId: String, amount: Double) {
        withdraw(fromAccountId, amount) // Withdraw from the source account
        deposit(toAccountId, amount) // Deposit to the destination account
        println("Transferred $amount from $fromAccountId to $toAccountId.") // Confirmation message
    }

    // Displays the details of an account, including balance and transaction history
    fun viewAccount(accountId: String) {
        val account = accounts[accountId] ?: throw IllegalArgumentException("Account not found") // Get the account or throw an error
        println("Account ID: ${account.accountId}") // Print account ID
        println("Holder: ${account.accountHolder}") // Print account holder's name
        println("Balance: ${account.balance}") // Print account balance
        println("Transactions: ${account.transactions}") // Print transaction history
    }

    // Applies interest to a savings account based on a given interest rate
    fun applyInterest(accountId: String, interestRate: Double) {
        val account = accounts[accountId] ?: throw IllegalArgumentException("Account not found") // Get the account or throw an error
        val interest = account.balance * (interestRate / 100) // Calculate interest as a percentage of the balance
        account.balance += interest // Add the interest to the account balance
        // Add a transaction record for the interest applied
        account.transactions.add(Transaction(UUID.randomUUID().toString(), TransactionType.DEPOSIT, interest))
        println("Applied $interest as interest to account $accountId. New balance: ${account.balance}.") // Confirmation message
    }

    // Generates an account statement with details about transactions and the current balance
    fun generateStatement(accountId: String): String {
        val account = accounts[accountId] ?: throw IllegalArgumentException("Account not found") // Get the account or throw an error
        val builder = StringBuilder() // StringBuilder for efficient string concatenation
        builder.append("Account Statement for ${account.accountHolder}\n") // Add header
        builder.append("Account ID: ${account.accountId}\n") // Add account ID
        builder.append("Balance: ${account.balance}\n") // Add current balance
        builder.append("Transactions:\n") // Add transactions header
        for (transaction in account.transactions) { // Iterate over all transactions
            builder.append("${transaction.timestamp} - ${transaction.type} - Amount: ${transaction.amount}\n") // Append transaction details
        }
        return builder.toString() // Return the statement as a string
    }

}
