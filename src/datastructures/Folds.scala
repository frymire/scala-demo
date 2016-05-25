package datastructures

object Folds {

  def main(args: Array[String]): Unit = {
    
    val v = Vector(1, 1, 3, 7, 2)
    
    val sums = v.scan(0)(_ + _) dropRight 1
    
    println(sums)
    
    val localFNs = (v zip v.scanLeft(0)(_ + _) ) map { p => p._1 * p._2 } sum

    println(localFNs)
    
    
    
    val otherWay = v.foldLeft( (0,0) ) { (pair, newSubclusterCount) => val (previousTotal, runningFNs) = pair
    
      (previousTotal + newSubclusterCount, runningFNs + previousTotal * newSubclusterCount)
    }
    
    println(otherWay._2)
    
  }

}