import java.security.MessageDigest

object PasswordUtil {
    // Hash a plaintext password using SHA-256
    fun hashPassword(password: String): String {
        val digest = MessageDigest.getInstance("SHA-256") // Use SHA-256 hashing algorithm
        val hashBytes = digest.digest(password.toByteArray()) // Hash the password
        return hashBytes.joinToString("") { "%02x".format(it) } // Convert hash to hex string
    }

    // Verify if a plaintext password matches a hashed password
    fun verifyPassword(plainTextPassword: String, hashedPassword: String): Boolean {
        val hashedInput = hashPassword(plainTextPassword) // Hash the input password
        return hashedInput == hashedPassword // Compare with the stored hash
    }
}
