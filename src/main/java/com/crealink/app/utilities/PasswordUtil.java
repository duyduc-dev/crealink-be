package com.crealink.app.utilities;

import org.springframework.security.crypto.password.PasswordEncoder;

public final class PasswordUtil {

    private PasswordUtil() {
        // Utility class — prevent instantiation
    }

    /**
     * Generates a salted password hash.
     *
     * @param passwordEncoder the PasswordEncoder bean (e.g., BCrypt)
     * @param rawPassword the plain password
     * @return a String array where [0] = salt, [1] = hashed password
     */
    public static String[] generatePasswordHash(PasswordEncoder passwordEncoder, String rawPassword) {
        String salt = UniqueUtil.generateSalt();
        String saltedPassword = rawPassword.trim().concat(salt);
        String hashedPassword = passwordEncoder.encode(saltedPassword);
        return new String[] { salt, hashedPassword };
    }

    /**
     * Checks if a raw password matches the hashed password using the stored salt.
     *
     * @param passwordEncoder the PasswordEncoder bean
     * @param rawPassword the raw input password
     * @param salt the salt originally used during hashing
     * @param hashedPassword the previously stored hashed password
     * @return true if the password matches; false otherwise
     */
    public static boolean checkPassword(
        PasswordEncoder passwordEncoder,
        String rawPassword,
        String salt,
        String hashedPassword
    ) {
        String saltedPassword = rawPassword.trim().concat(salt);
        return passwordEncoder.matches(saltedPassword, hashedPassword);
    }
}
