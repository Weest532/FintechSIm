fun main() {
    DatabaseHelper.initializeDatabase() // Ensure tables are created
    val bank = Bank()

    // Create a new account with a password
    val aliceId = bank.createAccount("Alice", "securePassword123")

    // Attempt to log in with the correct password
    if (bank.login(aliceId, "securePassword123")) {
        println("Login successful for Alice!")
    } else {
        println("Login failed for Alice.")
    }

    // Attempt to log in with an incorrect password
    if (bank.login(aliceId, "wrongPassword")) {
        println("Login successful for Alice!")
    } else {
        println("Login failed for Alice.")
    }
}
