

package datastructures

object ParallelCollections {

  def main(args: Array[String]): Unit = {
    
    // Let's show what happens if we allow multiple threads to print out a list of numbers.  Note that the ordering
    // of the output array can be in a mixed up order
    val badList = for (i <- (0 until 200).par) yield { if (i < 100) println(i) else println("    " + i); 2*i }
    badList foreach println
    
    // To make sure everything stays in order, assign the specific index
    val goodList = new Array[Int](200)
    for (i <- (0 until 200).par) { if (i < 100) println(i) else println("    " + i); goodList(i) = 3*i }
    println; goodList foreach println
    
  }

}