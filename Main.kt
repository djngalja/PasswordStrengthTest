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

    val numbers = findNumbers(password)
    val uppercase = findUpperCase(password)
    val lowercase = findLowerCase(password)
    val specialChars = findSpecialChars(password)
    val patterns =  findCommonPat(password) + findRepChars(password) + findABCpatterns(password)

    val score = password.length/8 + numbers + uppercase + lowercase + specialChars - patterns
    printResults(password, score)

}

fun printResults(password: String, score: Int) {
    println()
    println("password length = " + password.length)
    println()
    printCheckList("numbers", findNumbers(password))
    printCheckList("capital letters", findUpperCase(password))
    printCheckList("lowercase letters", findLowerCase(password))
    printCheckList("special characters", findSpecialChars(password), true)
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
fun findUpperCase(password: String): Int{
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
fun findLowerCase(password: String): Int{
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
fun findCommonPat(pass: String): Int{
    //add common password patterns to this array:
    val patterns = arrayOf("123123", "123321", "654321", "123qwe", "1q2w3e", "abc123", "admin", "dragon", "iloveyou", "lovely", "password", "qwerty", "welcome")
    var count = 0
    for(pattern in patterns){
        var i = 0
        while(i<pass.length){
            var temp = ""
            if(pass[i]==pattern[0] && pass.length>=(i+pattern.length)){ //find the 1st letter of a pattern
                temp+= pass[i]
                for(j in 1 until pattern.length){
                    if(pass[i+j]==pattern[j]) temp+= pass[i+j]
                    else break
                }
                if(temp==pattern){
                    println("[!] common pattern \"$pattern\" is found")
                    count++
                    i+= pattern.length
                }
            }
            i++
        }
    }
    return count
}

//find patterns of repeating (>3) characters and count them
fun findRepChars(pass: String):Int{
    val len = pass.length
    var i = 0
    var count = 0
    while(i<len-3){ //ignore the last 3 characters
        var temp = ""
        if(pass[i]==pass[i+1]){ //2 repeating characters found
            temp+= pass[i]
            var j = 1
            while(j<(len-i) && pass[i]==pass[i+j]){
                temp+= pass[i]
                j++
            }
        }
        if(temp.length>3){
            count++
            println("[!] common pattern \"$temp\" is found")
            i+= temp.length-1
        }
        i++
    }
    return count
}

//find arithmetic sequences with a common difference of 1 and sequences of letters in alphabetical order
fun findABCpatterns(pass: String):Int{
    var i = 0
    var count = 0
    while(i<pass.length-3){ //ignore the last 3 characters
        var temp = ""
        if(pass[i].code==pass[i+1].code-1){
            temp+= pass[i]
            temp+= pass[i+1]
            var j = 1
            while(j<(pass.length-i-1) && pass[i+j].code==pass[i+j+1].code-1){
                temp+= pass[i+j+1]
                j++
            }
        }
        if(temp.length>3){
            count++
            println("[!] common pattern \"$temp\" is found")
            i+= temp.length-1
        }
        i++
    }
    return count
}