/*
* Password Strength Test
*/

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
    val digit = password.findDigit()
    val uppercase = password.findUpperCase()
    val lowercase = password.findLowerCase()
    val specialChars = password.findSpecialChars()
    val p1 = findCommonPatterns(password).size
    val p2 = findRepeatChars(password).size
    val p3 = findABCpatterns(password).size
    val p4 = findABCpatterns(password, true).size
    val patterns = p1 + p2 + p3 + p4
    return password.length / 8 + digit + uppercase + lowercase + specialChars - patterns
}

fun printResults(password: String, score: Int) {
    println("password length = " + password.length)
    println()
    printCheckList("numbers", password.findDigit())
    printCheckList("capital letters", password.findUpperCase())
    printCheckList("lowercase letters", password.findLowerCase())
    printCheckList("special characters", password.findSpecialChars(), true)
    printPatterns(findCommonPatterns(password))
    printPatterns(findRepeatChars(password))
    printPatterns(findABCpatterns(password))
    printPatterns(findABCpatterns(password, true))
    println("\ntotal score = $score (${evaluateScore(score)})")
}

fun printCheckList(name: String, value: Int, specialChar: Boolean = false) {
    if (value == 0) println("[ ] $name")
    else {
        if (specialChar) println("[x] $value special character(s)")
        else println("[x] $name")
    }
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
fun String.findDigit(): Int {
    for (c in this) if (c.isDigit()) return 1
    return 0
}

/*
* Returns 1 if at least 1 uppercase letter was found, or 0 if no such character was found.
*/
fun String.findUpperCase(): Int {
    for (c in this) if (c.isUpperCase()) return 1
    return 0
}

/*
* Returns 1 if at least 1 lowercase letter was found, or 0 if no such character was found.
*/
fun String.findLowerCase(): Int {
    for (c in this) if (c.isLowerCase()) return 1
    return 0
}

/*
* Returns the number of special characters, or 0 if no special characters were found.
*/
fun String.findSpecialChars(): Int {
    var count = 0
    for (c in this) if (!(c.isLetterOrDigit())) count++
    return count
}

/*
* Returns a set of substrings of any common patterns found in the given String (password).
* The patterns are added manually to the Array `patterns`.
* Each substring is at least 4 Chars long.
*/
fun findCommonPatterns(password: String): MutableSet<String> {
    val results = mutableSetOf<String>()
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
    for (pattern in patterns) {
        var i = 0
        while (i < password.length) {
            var temp = ""
            if ((password[i] == pattern[0]) && (password.length >= (i + pattern.length))) {
                for (j in pattern.indices) {
                    if (password[i + j] == pattern[j]) temp += password[i + j]
                    else break
                }
                if (temp == pattern) {
                    results.add(temp)
                    i += pattern.length
                }
            }
            i++
        }
    }
    return results
}

/*
* Returns a set of substrings of any repeating Chars found in the given String (password).
* Each substring is at least 4 Chars long.
*/
fun findRepeatChars(password: String): MutableSet<String> {
    val results = mutableSetOf<String>()
    var i = 0
    while (i < password.length - 3) { //looking for patterns longer than 3 Chars
        var temp = ""
        if (password[i] == password[i + 1]) { //2 repeating Chars found
            temp += password[i]
            while ((i < (password.length - 1)) && (password[i] == password[i + 1])) {
                temp += password[i + 1]
                i++
            }
        }
        if (temp.length > 3) results.add(temp)
        i++
    }
    return results
}

/*
* Returns the set of substrings found in the given String (password).
* These substrings are arithmetic sequences with a common difference of 1,
* as well as sequences of letters in alphabetical order.
* If backwards == true, the substrings are arithmetic sequences with a common difference of -1,
* as well as sequences of letters in reverse alphabetical order.
* Each substring is at least 4 Chars long.
*/
fun findABCpatterns(password: String, backwards: Boolean = false): MutableSet<String> {
    val results = mutableSetOf<String>()
    val d = if (backwards) -1 else 1 // common difference
    var i = 0
    while (i < password.length - 3) { //looking for sequences longer than 3 Chars
        var temp = ""
        if ((password[i].code + d == password[i + 1].code) && password[i].isLetterOrDigit()) {
            temp += password[i]
            while ((i < (password.length - 1)) && (password[i].code + d == password[i + 1].code) && password[i].isLetterOrDigit()) {
                temp += password[i + 1]
                i++
            }
        }
        if (temp.length > 3) results.add(temp)
        i++
    }
    return results
}
