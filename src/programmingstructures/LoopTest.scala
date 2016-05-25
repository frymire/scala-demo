

package programmingstructures

import scala.collection.mutable.ArrayBuffer


object LoopTest {

  def main(args: Array[String]): Unit = {
     
    val data = for (i <- 1 to 10) yield i*i
    
    def evaluateMe(x: Int) = { println("Evaluating..."); x*x }
    
    println("Evaluate within the loop.")
    println("The count is..." + data.count { _ > evaluateMe(5) } )
    
    println("\nEvaluate before the loop.")
    val value = evaluateMe(5)
    println("The count is..." + data.count { _ > value } )
    
    
    // The upper bound in a range used in a for-loop only gets evaluated once.
    var moreData = ArrayBuffer[Int]() 
    (0 to 5) foreach { i => moreData += i*i*i }
    println(moreData)
    for (i <- 0 until moreData.size) { println(moreData(i)); moreData += 1 }
    println(moreData)
  }

}