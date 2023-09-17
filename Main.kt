/*
* PASSWORD STRENGTH TEST
*/

fun main() {
    println("Enter a password to test its strength:")
    val password = Password(readln())
    password.printResults()
}

class Password(private val password: String, private val minPatternLength: Int = 4) {
    private val digits = containsDigit()
    private val upperCase = containsUpperCase()
    private val lowerCase = containsLowerCase()
    private val specialChars = countSpecialChars()
    private val patternsFound = mutableSetOf<String>()

    init { // add elements to 'patternsFound' Set
        findCommonPatterns()
        findRepeatingChars()
        findRepeatingPairs()
        findAbcPatterns()
        findAbcPatterns(true)
    }

    /*
    * Total score is calculated based on the following rules:
    *   +1 point for every 8 characters
    *   +1 point for numbers
    *   +1 point for upper case letters
    *   +1 point for lower case letters
    *   +1 point for every special character
    *   -1 point for every common pattern
    */
    private var score = 0

    init {
        score = password.length / 8 + digits + upperCase + lowerCase + specialChars - patternsFound.size
    }

    private fun containsDigit(): Int {
        for (c in password) if (c.isDigit()) return 1
        return 0
    }

    private fun containsUpperCase(): Int {
        for (c in password) if (c.isUpperCase()) return 1
        return 0
    }

    private fun containsLowerCase(): Int {
        for (c in password) if (c.isLowerCase()) return 1
        return 0
    }

    private fun countSpecialChars(): Int {
        var count = 0
        for (c in password) if (!(c.isLetterOrDigit())) count++
        return count
    }

    /*
    * Checks whether 'password' contains
    * any password patterns from 'patterns' Array ('admin', 'qWeRty', 'ILoveYou', etc.).
    * These substrings are added to 'patternsFound'.
    */
    private fun findCommonPatterns() {
        val patterns = arrayOf( //add common passwords (in lower case) to this Array
            "123123",
            "123321",
            "123qwe",
            "1q2w3e",
            "abc123",
            "admin",
            "dragon",
            "iloveyou",
            "lovely",
            "password",
            "qwerty",
            "welcome"
        )
        var shortestPatternLength = patterns[0].length
        for (pattern in patterns) {
            if (pattern.length < shortestPatternLength)
                shortestPatternLength = pattern.length
        }
        if (password.length < shortestPatternLength) return
        for (pattern in patterns) {
            if (pattern.length > password.length) continue
            var i = 0
            while (i <= password.length - pattern.length) {
                var tempString = ""
                if (password[i].lowercase() == pattern[0].toString()) {
                    for (j in pattern.indices) {
                        if (password[i + j].lowercase() == pattern[j].toString())
                            tempString += password[i + j]
                        else break
                    }
                    if (tempString.length == pattern.length) {
                        patternsFound.add(tempString)
                        i += pattern.length - 1
                    }
                }
                i++
            }
        }
    }

    /*
    * Checks whether 'password' contains
    * any repeating characters ('1111', 'hhhh', 'aAaaa', '++++', etc.).
    * These substrings are added to 'patternsFound'.
    * Each substring is at least minPatternLength characters long.
    */
    private fun findRepeatingChars() {
        var i = 0
        while (i <= password.length - minPatternLength) {
            //looking for patterns not shorter than minPatternLength
            var tempString = ""
            if (password[i].lowercase() == password[i + 1].lowercase()) { //2 repeating Chars found
                tempString += password[i]
                while (
                    i < password.length - 1
                    && password[i].lowercase() == password[i + 1].lowercase()
                ) {
                    tempString += password[i + 1]
                    i++
                }
            }
            if (tempString.length >= minPatternLength)
                patternsFound.add(tempString)
            i++
        }
    }

    /*
    * Checks whether 'password' contains
    * any repeating pairs of characters ('1212', 'HAHAhaha', etc.).
    * These substrings are added to 'patternsFound'.
    * Each substring is at least minPatternLength characters long.
    */
    private fun findRepeatingPairs() {
        if (password.length < 4) return
        var i = 0
        while (i <= password.length - minPatternLength) {
            //looking for patterns not shorter than minPatternLength
            var tempString = ""
            if (
                password[i].lowercase() == password[i + 2].lowercase()
                && password[i + 1].lowercase() == password[i + 3].lowercase()
                && password[i].lowercase() != password[i + 1].lowercase()
            ) {
                tempString += password[i]
                tempString += password[i + 1]
                while (
                    i < password.length - 3
                    && password[i].lowercase() == password[i + 2].lowercase()
                    && password[i + 1].lowercase() == password[i + 3].lowercase()
                ) {
                    tempString += password[i + 2]
                    tempString += password[i + 3]
                    i += 2
                }
            }
            if (tempString.length >= minPatternLength) {
                patternsFound.add(tempString)
                i += 2
            } else i++
        }
    }

    /*
    * By default, checks whether 'password' contains
    * any arithmetic sequences with a common difference of 1 ('1234', '34567', etc.)
    * or sequences of letters in alphabetical order ('abcd', 'ABcDe', 'hIjKLMn', etc.).
    * If 'backwards' is set to 'true', checks whether 'password' contains
    * arithmetic sequences with a common difference od -1 ('4321', '76543', etc.)
    * or  sequences of letters in reverse alphabetical order ('dcba', 'eDcBA', 'nMLKjIh', etc.).
    * These substrings are added to 'patternsFound'.
    * Each substring is at least minPatternLength characters long.
    */
    private fun findAbcPatterns(backwards: Boolean = false) {
        if (password.length < minPatternLength) return
        val d = if (backwards) -1 else 1 // common difference
        var i = 0
        while (i <= password.length - minPatternLength) {
            //looking for sequences not shorter than minPatternLength
            var tempString = ""
            if (
                (password[i].code + d == password[i + 1].code
                        && password[i].isLetterOrDigit())
                ||
                (password[i].isUpperCase()
                        && password[i + 1].isLowerCase()
                        && password[i].code + d + 32 == password[i + 1].code)
                //convert password[i] + d to lower case
                ||
                (password[i].isLowerCase()
                        && password[i + 1].isUpperCase()
                        && password[i].code + d - 32 == password[i + 1].code)
            //convert password[i] + d to upper case
            ) {
                tempString += password[i]
                while (
                    i < password.length - 1
                    &&
                    (
                            (password[i].code + d == password[i + 1].code
                                    && password[i].isLetterOrDigit())
                                    ||
                                    (password[i].isUpperCase()
                                            && password[i + 1].isLowerCase()
                                            && password[i].code + d + 32 == password[i + 1].code)
                                    //convert password[i] + d to lower case
                                    ||
                                    (password[i].isLowerCase()
                                            && password[i + 1].isUpperCase()
                                            && password[i].code + d - 32 == password[i + 1].code)
                            //convert password[i] + d to upper case
                            )
                ) {
                    tempString += password[i + 1]
                    i++
                }
            }
            if (tempString.length >= minPatternLength)
                patternsFound.add(tempString)
            i++
        }
    }

    private fun String.printLine(value: Int, specialChar: Boolean = false) {
        val check = if (value != 0) 'x' else ' '
        if (specialChar && (value != 0))
            println("[$check] $value special character(s)")
        else println("[$check] $this")
    }

    private fun Int.evaluateScore(): String {
        return if (this <= 2) "very weak"
        else if (this <= 4) "weak"
        else if (this <= 6) "medium"
        else if (this <= 8) "strong"
        else "very strong"
    }

    fun printResults() {
        println("Password length: ${password.length}\n")
        "Digits".printLine(digits)
        "Upper case letters".printLine(upperCase)
        "Lower case letters".printLine(lowerCase)
        "Special characters".printLine(specialChars, true)
        if (patternsFound.size != 0) println()
        for (pattern in patternsFound)
            println("[!] Common pattern '$pattern' found")
        println("\nTOTAL SCORE: $score (${score.evaluateScore()})")
    }
}
