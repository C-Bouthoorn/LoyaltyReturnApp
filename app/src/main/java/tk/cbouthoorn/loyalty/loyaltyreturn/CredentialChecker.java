package tk.cbouthoorn.loyalty.loyaltyreturn;


class CredentialChecker {
    static boolean isValidUsername(String username) {
        return true;
    }

    static boolean isValidPassword(String password) {
        return password.length() >= 8;
    }
}
