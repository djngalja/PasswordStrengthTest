## Password Strength Test
Run this Kotlin code to evaluate the strength of your password based on the following criteria:

- length
- the number of special characters
- whether it contains uppercase and lowercase letters, and numbers
- whether it is (or it contains) a common pattern, such as "qwerty", "123123", "abcdef", etc.

### Input Format

A string containing a password. 

Example: <br />
`:g8jzabcdef9p}qwerty7d7v `

### Sample Output

password length = 24

[x] numbers <br />
[ ] capital letters <br />
[x] lowercase letters <br />
[x] 2 special characters <br />
[!] common pattern "qwerty" is found <br />
[!] common pattern "abcdef" is found <br />

total score = 5 (medium)

### Scoring System

| Score  | Password Strength |
| --- | --- |
| < 3  | very weak  |
| 3 - 4  | weak  |
| 5 - 6 | medium |
| 7 - 8 | strong |
| > 8 | very strong |

Points are awarded if:

- the length is at least 8 characters (and +1 point for every 8 additional characters) 
- there are numbers
- there are capital letters 
- there are lowercase letters
- there are special characters (and +1 point for every additional special character) 

A point is subtracted for every common pattern found in the password.

