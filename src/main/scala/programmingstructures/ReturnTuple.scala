
/**
 * Demonstration that methods/functions can return multiple values.
 */
package programmingstructures

object ReturnTuple {

  def getStringAndInt: (String, Int) = {
    
    ("Here's a string.", 10)
  }
  
  
  def main(args: Array[String]): Unit = {
    
    val (aString, anInt) = getStringAndInt
    println("The string is \"" + aString + "\", and the integer is " + anInt + ".")
  }

}