/* PASSWORD STRENGTH TEST

    SCORE:
Length: +1 point for every 8 characters
+1 point for numbers
+1 point for capital letters
+1 point for lowercase letters
+1 point for every special character
-1 point for every common pattern */

fun main() {
    val pass = readln()
    println("password length = ${pass.length}\n")

    var score = 0
    val patterns = arrayOf("0000", "1111", "123123", "123321", "12345", "5555", "654321", "8888", "123qwe", "1q2w3e", "abc123", "abcdef", "admin", "dragon", "iloveyou", "lovely", "password", "qwerty", "welcome")
    val numbers = checkChar(pass,48,57, "numbers") //ASCII codes
    val capitals = checkChar(pass,65,90, "capital letters")
    val lowercase = checkChar(pass,97,122, "lowercase letters")
    val characters = checkSpecialChar(pass)

    score+= pass.length/8 + numbers + capitals + lowercase + characters
    for(pattern in patterns) score-= findCommonPat(pass, pattern)

    println("\ntotal score = $score (${evalStrength(score)})")
}


fun evalStrength(score:Int):String{
    return if(score<=2) "very weak"
    else if(score<=4) "weak"
    else if(score<=6) "medium"
    else if(score<=8) "strong"
    else "very strong"
}

//This function checks if a password contains numbers/lowercase/capital letters (using their ASCII codes)
fun checkChar(pass: String, num1: Int, num2: Int, name: String): Int{
    var found = 0
    for(i in pass){
        if(i.code in num1..num2) found = 1
    }
    if(found==0) println("[ ] $name")
    else println("[x] $name")
    return found
}

//This function looks for special characters and counts them
fun checkSpecialChar(pass: String):Int{
    var count = 0
    val specialChar ="~@#$%^&*_-+=`|\\/(){}[]<>:;\"',.?!"
    for(i in pass){
        for(j in specialChar){
            if(i==j) count++
        }
    }
    if(count==0) println("[ ] special characters")
    else println("[x] $count special character(s)")
    return count
}

//This function looks for common password patterns such as "qwerty", "12345", etc.
fun findCommonPat(pass: String, pattern: String): Int{
    var temp = ""
    var found = 0
    for(i in 0 until pass.length){
        if(pass[i]==pattern[0] && (i+pattern.length-1)<=pass.length){ //find the 1st letter of a pattern
            temp+= pattern[0]
            for(j in 1 until pattern.length){
                if(pass[i+j]==pattern[j]) temp+= pattern[j]
                else break
            }
            if(temp==pattern){ //a common pattern is found
                println("[!] common pattern \"$pattern\" is found")
                found = 1
            }
        }
    }
    return found
}