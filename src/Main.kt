fun main() {
    val bank = Bank() // Create a new Bank instance

    // Create two accounts
    val account1 = bank.createAccount("Alice")
    val account2 = bank.createAccount("Bob")

    // Perform some operations
    bank.deposit(account1, 1000.0) // Deposit 1000 into Alice's account
    bank.withdraw(account1, 200.0) // Withdraw 200 from Alice's account
    bank.transfer(account1, account2, 300.0) // Transfer 300 from Alice to Bob
    bank.applyInterest(account1, 5.0) // Apply 5% interest to Alice's account

    // Generate and print account statements
    println(bank.generateStatement(account1)) // Print Alice's statement
    println(bank.generateStatement(account2)) // Print Bob's statement
}