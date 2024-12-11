fun main() {
    DatabaseHelper.initializeDatabase() // Ensure tables are created
    val bank = Bank()

    // Create a new account with a password
    val aliceId = bank.createAccount("Alice", "securePassword123")
    val bobId = bank.createAccount("Bob", "password456")

    // Attempt to log in with the correct password
    if (bank.login(aliceId, "securePassword123")) {
        println("Login successful for Alice!")
    } else {
        println("Login failed for Alice.")

    }
    if (bank.login(bobId, "securePassword123")) {
        println("Login successful for Alice!")
    } else {
        println("Login failed for Alice.")

    }

    // Test deposit functionality
    println("\n--- Testing Deposit ---")
    testDeposit(bank, aliceId, 500.0) // Expect: Alice's balance becomes 500.0
    testDeposit(bank, bobId, 1000.0) // Expect: Bob's balance becomes 1000.0

    // Test withdraw functionality
    println("\n--- Testing Withdraw ---")
    testWithdraw(bank, aliceId, 200.0) // Expect: Alice's balance becomes 300.0
    testWithdraw(bank, bobId, 1500.0) // Expect: Bob's withdraw fails due to insufficient funds
    testWithdraw(bank, bobId, 500.0) // Expect: Bob's balance becomes 500.0

    // Check final account balances
    println("\n--- Final Account Balances ---")
    val aliceAccount = bank.getAccount(aliceId)
    val bobAccount = bank.getAccount(bobId)
    println("Alice's final balance: ${aliceAccount?.balance}")
    println("Bob's final balance: ${bobAccount?.balance}")

    // Attempt to log in with an incorrect password
    if (bank.login(aliceId, "wrongPassword")) {
        println("Login successful for Alice!")
    } else {
        println("Login failed for Alice.")
    }
}

// Test deposit functionality
fun testDeposit(bank: Bank, accountId: String, amount: Double) {
    println("Depositing $amount to account $accountId...")
    bank.deposit(accountId, amount)
    val account = bank.getAccount(accountId)
    println("Expected balance >= $amount, Actual balance: ${account?.balance}")
}

// Test withdraw functionality
fun testWithdraw(bank: Bank, accountId: String, amount: Double) {
    println("Withdrawing $amount from account $accountId...")
    bank.withdraw(accountId, amount)
    val account = bank.getAccount(accountId)
    println("Post-withdraw balance: ${account?.balance}")
}
