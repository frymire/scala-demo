


package programmingstructures


import scala.Array.canBuildFrom

object BooleanArrays {
  
  def main(args: Array[String]) = {
    
    val matches = Array(true, false, false, true) zip 
                  Array(true,  true, false, true)
    
    println ("Matches: "         + (matches count { (pair) => pair._1 == pair._2} ) )
    println ("Mis-matches: "     + (matches count { (pair) => pair._1 != pair._2} ) )
    println ("Conditional AND: " + (matches count { (pair) => pair._1 && pair._2} ) )
    println ("Conditional OR: "  + (matches count { (pair) => pair._1 || pair._2} ) )
    println ("Exclusive OR: "    + (matches count { (pair) => pair._1 ^  pair._2} ) )

    // These always evaluate both conditions.  They're generally bad style...
    println
    println ("Logical AND: "     + (matches count { (pair) => pair._1 &  pair._2} ) )
    println ("Logical OR: "      + (matches count { (pair) => pair._1 |  pair._2} ) )
    
  }

}