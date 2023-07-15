/* PASSWORD STRENGTH TEST

    SCORE:
Length: +1 point for every 8 characters
+1 point for numbers
+1 point for uppercase letters
+1 point for lowercase letters
+1 point for every special character
-1 point for every common pattern */

fun main() {
    val pass = readln()
    println("password length = ${pass.length}\n")

    var score = 0
    val numbers = checkChar(pass,48,57, "numbers") //ASCII codes
    val uppercase = checkChar(pass,65,90, "uppercase letters")
    val lowercase = checkChar(pass,97,122, "lowercase letters")
    val characters = checkSpecialChar(pass)
    val patterns =  findCommonPat(pass) + findRepChars(pass) + findABCpatterns(pass)

    score+= pass.length/8 + numbers + uppercase + lowercase + characters - patterns

    println("\ntotal score = $score (${evalStrength(score)})")
}


fun evalStrength(score:Int):String{
    return if(score<=2) "very weak"
    else if(score<=4) "weak"
    else if(score<=6) "medium"
    else if(score<=8) "strong"
    else "very strong"
}

//check if the password contains numbers/lowercase/capital letters (using their ASCII codes)
fun checkChar(pass: String, num1: Int, num2: Int, name: String): Int{
    var found = 0
    for(i in pass){
        if(i.code in num1..num2){
            found = 1
            break
        }
    }
    if(found==0) println("[ ] $name")
    else println("[x] $name")
    return found
}

//find special characters and count them
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