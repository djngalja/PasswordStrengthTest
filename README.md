## Password Strength Test
Run this Kotlin code to evaluate the strength of your password based on the following criteria:

- length
- the number of special characters
- whether it contains upper case letters, lower case letters and numbers
- whether it is (or it contains) a common pattern, such as "qwerty", "123123", "abcdef", etc.

### Input Format

A string containing a password. 

Example: <br />
`ADmINadmin3456$SSss`

### Sample Output

Password length: 19

[x] Digits <br />
[x] Upper case letters <br />
[x] Lower case letters <br />
[x] 1 special character(s) <br /><br />
[!] Common pattern 'ADmIN' found <br />
[!] Common pattern 'admin' found <br />
[!] Common pattern 'SSss' found <br />
[!] Common pattern '3456' found <br /><br />
TOTAL SCORE: 2 (very weak)

### Common Patterns
The following password patterns are taken into account: <br />

| Pattern description                                     | Examples                       |
|---------------------------------------------------------|--------------------------------|
| Arithmetic sequences with common differences of 1 or -1 | `1234` `65432`                 |
| Sequences of letters in (reverse) alphabetical order    | `abcd` `dcba` `hIjkLmN`        |
| Sequences of repeating characters                       | `GGGG` `_____` `6666` `AAAAaa` |
| Sequence of repeating pairs of characters               | `121212` `HAHa` `*+*+*+`       |
| Common password patterns                                | `qwerty` `admin` `PassWorD`    |

Only sequences longer than 3 characters are detected. No search algorithm is not case-sensitive.
### Scoring System

| Score | Password Strength |
|-------|-------------------|
| < 3   | very weak         |
| 3 - 4 | weak              |
| 5 - 6 | medium            |
| 7 - 8 | strong            |
| > 8   | very strong       |

**Total score is calculated based on the following rules:**

**+1 point** for every 8 characters *(password length)*
<br>   **+1 point** for numbers 
<br>   **+1 point** for upper case letters 
<br>   **+1 point** for lower case letters 
<br>   **+1 point** for every special character *(characters that are not letters or digits)*
<br>   **-1 point** for every common pattern 
