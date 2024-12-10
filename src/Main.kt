fun main() {
    DatabaseHelper.initializeDatabase() // Ensure tables are created
    val bank = Bank()

    // Create accounts
    val aliceId = bank.createAccount("Alice")
    val bobId = bank.createAccount("Bob")

    // Perform transactions
    bank.deposit(aliceId, 500.0)
    bank.withdraw(aliceId, 100.0)
    bank.deposit(bobId, 1000.0)

    // View updated account details
    val aliceAccount = bank.getAccount(aliceId)
    println("Alice's account: $aliceAccount")

    val bobAccount = bank.getAccount(bobId)
    println("Bob's account: $bobAccount")
}
