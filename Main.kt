/* PASSWORD STRENGTH TEST

    SCORE:
Length: +1 point for every 8 characters
+1 point for numbers
+1 point for uppercase letters
+1 point for lowercase letters
+1 point for every special character
-1 point for every common pattern */

fun main() {
    val password = readln()
    val score = calcScore(password)
    printResults(password, score)
}

fun calcScore(password: String): Int {
    val numbers = findNumbers(password)
    val uppercase = findUpperCase(password)
    val lowercase = findLowerCase(password)
    val specialChars = findSpecialChars(password)

    val comPat = findCommonPat(password).size
    val repCharPat = findRepChars(password).size
    val abcPat = findABCpatterns(password).size
    val cbaPat = findABCpatterns(password, true).size
    val patterns = comPat + repCharPat + abcPat + cbaPat

    return password.length / 8 + numbers + uppercase + lowercase + specialChars - patterns
}

fun printResults(password: String, score: Int) {
    println()
    println("password length = " + password.length)
    println()
    printCheckList("numbers", findNumbers(password))
    printCheckList("capital letters", findUpperCase(password))
    printCheckList("lowercase letters", findLowerCase(password))
    printCheckList("special characters", findSpecialChars(password), true)
    printPatterns(findCommonPat(password))
    printPatterns(findRepChars(password))
    printPatterns(findABCpatterns(password))
    printPatterns(findABCpatterns(password, true))
    println()
    println("total score = $score (${evalScore(score)})")
}

fun printCheckList(name: String, value: Int, specialChar: Boolean = false) {
    if (value == 0) println("[ ] $name")
    else {
        if (specialChar) println("[x] $value special character(s)")
        else println("[x] $name")
    }
}

fun printPatterns(set: MutableSet<String>){
    for (pattern in set) println("[!] common pattern \"$pattern\" is found")
}

fun evalScore(score: Int): String {
    return if (score <= 2) "very weak"
    else if (score <= 4) "weak"
    else if (score <= 6) "medium"
    else if (score <= 8) "strong"
    else "very strong"
}

/*
* Returns 1 if at least 1 number was found.
* Returns 0 if no such character was found.
*/
fun findNumbers(password: String): Int {
    var found = 0
    for (c in password) {
        if (c.isDigit()) {
            found = 1
            break
        }
    }
    return found
}

/*
* Returns 1 if at least 1 uppercase letter was found.
* Returns 0 if no such character was found.
*/
fun findUpperCase(password: String): Int {
    var found = 0
    for (c in password) {
        if (c.isUpperCase()) {
            found = 1
            break
        }
    }
    return found
}

/*
* Returns 1 if at least 1 lowercase letter was found.
* Returns 0 if no such character was found.
*/
fun findLowerCase(password: String): Int {
    var found = 0
    for (c in password) {
        if (c.isLowerCase()) {
            found = 1
            break
        }
    }
    return found
}

/*
* Returns the number of special characters, or 0 if no special characters were found.
*/
fun findSpecialChars(password: String): Int {
    var count = 0
    for (c in password) if (!(c.isLetterOrDigit())) count++
    return count
}

//find common patterns such as "qwerty", "admin", etc.
fun findCommonPat(pass: String): MutableSet<String> {
    //add common password patterns to this array:
    val patterns = arrayOf(
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
    val results = mutableSetOf<String>()
    for (pattern in patterns) {
        var i = 0
        while (i < pass.length) {
            var temp = ""
            if (pass[i] == pattern[0] && pass.length >= (i + pattern.length)) { //find the 1st letter of a pattern
                temp += pass[i]
                for (j in 1 until pattern.length) {
                    if (pass[i + j] == pattern[j]) temp += pass[i + j]
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
* Returns the set of substrings of any repeating Chars found in the given String (password).
* Each substring is at least 4 Chars long.
*/
fun findRepChars(password: String): MutableSet<String> {
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