
package datastructures


import collection.mutable.ArrayBuffer

object MultiDArrays {

  def main(args: Array[String]): Unit = {
    
    val n = 3
    
    val mean = new Array[Double](n)
    val cov = Array.ofDim[Double](n, n)    
    
    for (d <- 0 until n) cov(d)(d) = 1.0
    
    val covAB = new ArrayBuffer[Double]
    for (row <- cov) covAB ++= row
    
    println (mean mkString "\n"); println
    for (row <- cov) println(row mkString "\t"); println
    println (covAB mkString "\t")
    

    val ones = Array.fill[Int](n, n)(1)
    println; for (row <- ones) println(row mkString "\t"); println
    
  }

}