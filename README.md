# Password Strength Test
Run this Kotlin code to evaluate the strength of your password based on the following criteria:

- length
- the number of special characters
- whether it contains uppercase and lowercase letters, and numbers
- whether it is (or it contains) a common pattern, such as "qwerty", "123123", "abcdef", etc.

## Input Format

A string containing a password. For example:
- Agd123!goodPass
- 37485Iforgotit

## Sample Output

password length = 27

[+] numbers <br />
[+] capital letters <br />
[+] lowercase letters <br />
[+] special characters <br />
[-] common pattern "qwerty" is found <br />
[-] common pattern "abcdef" is found <br />

total score = 6 (medium)

