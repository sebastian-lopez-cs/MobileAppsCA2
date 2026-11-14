
package com.example.mobileappsca2

// Adapted from Eugene O'Regan's MyException.kt.
// "open" allows this exception class to be extended.
open class WordLengthException :
    Exception("Word cannot exceed 10 characters")
