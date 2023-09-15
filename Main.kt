/*
* PASSWORD STRENGTH TEST
*/

const val MIN_PATTERN_LENGTH = 4

val patternsFound = mutableSetOf<String>()
var digits = 0
var lowerCase = 0
var upperCase = 0
var specialChars = 0

fun main() {
    println("Enter a password to test its strength:")
    val password = readln()
    val score = calculateScore(password)
    printResults(password, score)
}

/*
* Calculates and returns the total score based on the following rules:
Length: +1 point for every 8 characters
+1 point for numbers
+1 point for uppercase letters
+1 point for lowercase letters
+1 point for every special character
-1 point for every common pattern
*/
fun calculateScore(password: String): Int {
    digits = password.containsDigit()
    upperCase = password.containsUpperCase()
    lowerCase = password.containsLowerCase()
    specialChars = password.containsSpecialChars()
    findCommonPatterns(password)
    findRepeatingChars(password)
    findAbcPatterns(password)
    findAbcPatterns(password, true)
    findRepeatingPairs(password)
    return password.length / 8 + digits + upperCase + lowerCase + specialChars - patternsFound.size
}

fun printResults(password: String, score: Int) {
    println("password length = " + password.length)
    println()
    printCheckList("digits", digits)
    printCheckList("upper case letters", upperCase)
    printCheckList("lower case letters", lowerCase)
    printCheckList("special characters", specialChars, true)
    printPatterns(patternsFound)
    println("\ntotal score = $score (${evaluateScore(score)})")
}

fun printCheckList(name: String, value: Int, specialChar: Boolean = false) {
    val check = if (value == 0) ' ' else 'x'
    if (specialChar && (value != 0)) println("[$check] $value special character(s)")
    else println("[$check] $name")
}

fun printPatterns(set: MutableSet<String>) {
    for (pattern in set) println("[!] common pattern \"$pattern\" is found")
}

fun evaluateScore(score: Int): String {
    return if (score <= 2) "very weak"
    else if (score <= 4) "weak"
    else if (score <= 6) "medium"
    else if (score <= 8) "strong"
    else "very strong"
}

/*
* Returns 1 if at least 1 digit was found, or 0 if no such character was found.
*/
fun String.containsDigit(): Int {
    for (c in this) if (c.isDigit()) return 1
    return 0
}

/*
* Returns 1 if at least 1 uppercase letter was found, or 0 if no such character was found.
*/
fun String.containsUpperCase(): Int {
    for (c in this) if (c.isUpperCase()) return 1
    return 0
}

/*
* Returns 1 if at least 1 lowercase letter was found, or 0 if no such character was found.
*/
fun String.containsLowerCase(): Int {
    for (c in this) if (c.isLowerCase()) return 1
    return 0
}

/*
* Returns the number of special characters, or 0 if no special characters were found.
*/
fun String.containsSpecialChars(): Int {
    var count = 0
    for (c in this) if (!(c.isLetterOrDigit())) count++
    return count
}

/*
* Adds substrings of common patterns found in the given String (password) to the set 'patternsFound'.
* The common patterns are added manually to the Array 'patterns'.
*/
fun findCommonPatterns(password: String){
    val patterns = arrayOf( //add common password patterns to this Array
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
                    i += pattern.length
                }
            }
            i++
        }
    }
}

/*
* Adds substrings of any repeating Chars found in the given String (password) to the set 'patternsFound'.
* Each substring is at least MIN_PATTERN_LENGTH Chars long.
*/
fun findRepeatingChars(password: String){
    var i = 0
    while (i <= password.length - MIN_PATTERN_LENGTH) {
        //looking for patterns not shorter than MIN_PATTERN_LENGTH
        var tempString = ""
        if (password[i] == password[i + 1]) { //2 repeating Chars found
            tempString += password[i]
            while ((i < (password.length - 1)) && (password[i] == password[i + 1])) {
                tempString += password[i + 1]
                i++
            }
        }
        if (tempString.length >= MIN_PATTERN_LENGTH)
            patternsFound.add(tempString)
        i++
    }
}

/*
* Adds substrings of any repeating pairs of Chars found in the given String (password) to the set 'patternsFound'.
* Each substring is at least 6 Chars long.
*/
fun findRepeatingPairs(password: String){
    if (password.length < 6) return
    var i = 0
    while (i <= password.length - 6) {
        //looking for patterns not shorter than 6 Chars
        var tempString = ""
        if (
            password[i].lowercase() == password[i+2].lowercase()
            && password[i+1].lowercase() == password[i+3].lowercase()
            && password[i].lowercase() != password[i+1].lowercase()
            ) {
            tempString += password[i]
            tempString += password[i+1]
            while (
                i < password.length - 3
                && password[i].lowercase() == password[i+2].lowercase()
                && password[i+1].lowercase() == password[i+3].lowercase()

            ) {
                tempString += password[i+2]
                tempString += password[i+3]
                i += 2
            }
        }
        if (tempString.length >= 6)
            patternsFound.add(tempString)
        i++
    }
}

/*
* Adds substrings found in the given String (password) to the set 'patternsFound'.
* These substrings are arithmetic sequences with a common difference of 1,
* as well as sequences of letters in alphabetical order.
* If backwards == 'true', the substrings are arithmetic sequences with a common difference of -1,
* as well as sequences of letters in reverse alphabetical order.
* Each substring is at least MIN_PATTERN_LENGTH Chars long.
*/
fun findAbcPatterns(password: String, backwards: Boolean = false) {
    if (password.length < MIN_PATTERN_LENGTH) return
    val d = if (backwards) -1 else 1 // common difference
    var i = 0
    while (i <= password.length - MIN_PATTERN_LENGTH) {
        //looking for sequences not shorter than MIN_PATTERN_LENGTH Chars
        var tempString = ""
        if ((password[i].code + d == password[i + 1].code) && password[i].isLetterOrDigit()) {
            tempString += password[i]
            while (
                (i < (password.length - 1))
                && (password[i].code + d == password[i + 1].code)
                && password[i].isLetterOrDigit()
            ) {
                tempString += password[i + 1]
                i++
            }
        }
        if (tempString.length >= MIN_PATTERN_LENGTH)
            patternsFound.add(tempString)
        i++
    }
}
