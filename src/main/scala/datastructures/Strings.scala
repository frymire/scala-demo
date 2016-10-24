package datastructures

object Strings extends App {

    println("CopyMe " * 16)

    println("Mark".head)
    
    // Let's do some string interpolation
    val name = "James"
    println(s"Hello, $name")  // Hello, James
    
    println(s"1 + 1 = ${1 + 1}")
    
    val height = 1.9d
    println(f"$name%s is $height%2.2f meters tall")  // James is 1.90 meters tall
    
    // The raw interpolator ignores escape characters
    println(s"a\nb")
    println(raw"a\nb")
    
    // Note that you can also make your own interpolators.  See StringContext.
    
    // Trim
    val s = "  hi "
    println(s)
    println(s.trim)
    
    // Find substrings
    val myName = "Mark Frymire"
    println( "Mark Frymire contains Fry?  " + myName.contains("Fry") )

}