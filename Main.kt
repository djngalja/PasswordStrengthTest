/*PASSWORD STRENGTH TEST

Evaluates the strength of your password based on the following criteria:

- length
- lower- and uppercase letters
- special characters
- numbers
- common patterns

Input Format:
A string containing a password.

*/

fun main() {
    val pass = readln()
    //val pass = "hdd_ht45Tvd"
    //val pass = ":g8jzabcdefY9p}qwerty7d7vPX"
    evaluate(pass)
}

fun evaluate(pass: String){
    var score = 0

//The longer, the better. A good password must be at least 8 characters long (score +1). Even better, if it's twice as long. +2 points, if the password is 16 characters long.
    println("password length = ${pass.length}\n")
    score+= pass.length/8

//+1 point, if the password contains numbers.
    val numbers = checkChar(pass,48,57)//ACSII codes
    printResult(numbers, "numbers")
    score+=numbers

//+1 point, if there are uppercase letters.
    val capitals = checkChar(pass,65,90)
    printResult(capitals, "capital letters")
    score+=capitals

//+1 point, if there are lowercase letters.
    val lowercase = checkChar(pass,97,122)
    printResult(lowercase, "lowercase letters")
    score+= lowercase

//An additional point for every special character.
    val characters = checkSpecialChar(pass)
    if(characters==0) println("[ ] special characters")
    else println("[x] $characters special character(s)")
    score+= characters

//Too lazy to come up with an original password? Too bad! -1 point for every common pattern found in your password.
    val common = arrayOf("0000", "1111", "123123", "123321", "12345", "5555", "654321", "8888", "123qwe", "1q2w3e", "abc123", "abcdef", "admin", "dragon", "iloveyou", "lovely", "password", "qwerty", "welcome") //add popular password patterns to this array
    for(i in common){
        score+=findCommonPass(pass, i)
    }

    val result = strength(score)
    println("\ntotal score = $score ($result)")
}

fun printResult(num: Int, name: String){
    if(num==0) println("[ ] $name")// Functions return 0, if nothing was found (no numbers / no special characters)
    else println("[x] $name")
}

fun strength(score:Int):String{
    return if(score<=2) "very weak"
    else if(score<=4) "weak"
    else if(score<=6) "medium"
    else if(score<=8) "strong"
    else "very strong"
}

//This function checks whether a group of certain characters can be found in your password using their ASCII codes
fun checkChar(pass: String, num1: Int, num2: Int): Int{

    for(i in pass){
        if(i.code in num1..num2) return 1
    }
    return 0
}

//This function looks for special characters and counts them
fun checkSpecialChar(pass: String):Int{
    var count = 0
    val special ="~@#$%^&*_-+=`|\\/(){}[]<>:;\"',.?!"
    for(i in pass){
        for(j in special){
            if(i==j) count++
        }
    }
    return count
}

//This function looks for common passwords such as "qwerty" and "12345"
fun findCommonPass(pass: String, common: String): Int{
    var size = 0
    var count = 0
    for(i in 0 until pass.length){
        if(pass[i]==common[0]){ //find the 1st letter of a pattern
            for(j in 1 until common.length){
                if(pass[i+j]==common[j]) size++
                else break
            }
            if(size==common.length-1){
                println("[!] common pattern \"$common\" is found")
                count++
            }
        }
    }
    return count*(-1)
}
