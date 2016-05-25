
package datastructures

import scala.collection.mutable.Map

object UniqueCounts {

  def main(args: Array[String]): Unit = {

    val stringList = Array("Mark Frymire","Mark Frymire","Mark Frymire","Kurt Frymire","The Fryguy","The Fryguy")
    println(stringList.mkString("\n") )
    
    // Super slick way.  Note, however, that this can be inefficient if the list is large.
    val slickCounts = stringList.groupBy(s => s).mapValues(_.size)
    println(slickCounts)

    // Here's a more memory efficient alternative.
    val counts = Map[String, Int]()    
    for (i <- stringList) counts(i) = (counts.getOrElse(i, 0) + 1)
    println
    println(counts)
    
    // Default max references keys
    println(counts.max)
    
    // Do this to get the max over values
    val max = counts.maxBy(_._2)
    println(max._1 + " has the most counts: " + max._2)    
    
    
    // Slick way...  The Map in the first set of parentheses is the starting "value", which in this case is an empty map.  
    // The operation to be performed at each step is in braces.  It says to return a new map given the previous one and a
    // new string from the list.  In particular, the operation defined here increments the count in the map, as we did above. 
    val counts2 = stringList.foldLeft( Map[String,Int]() ) { (map, string) => map + (string -> (map.getOrElse(string, 0) + 1) ) }

    // Same thing, a little harder to comprehend.  Note the reverse order
    val counts3 = (Map[String, Int]() /: stringList) { (map, string) => map + (string -> (map.getOrElse(string, 0) + 1) ) }
    
    println(counts2)
    println(counts3)

  }

}