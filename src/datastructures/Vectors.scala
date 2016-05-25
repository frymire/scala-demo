package datastructures

object Vectors {
  
  def makeTempVectors = { 
    
    val myVector = (0 to 63).toVector
    val myUpdatedVector = myVector.updated(0, 100) 
    println(myUpdatedVector)
    myUpdatedVector
  }

  def main(args: Array[String]): Unit = {
    

    val myAugmentedVector = makeTempVectors :+ 64 
    println(myAugmentedVector)
    
    // Vectors automatically print recursively, unlike arrays
    val a = Array( Vector(1,2,3), Vector(4,5,6) )
    val v = Vector( Vector(1,2,3), Vector(4,5,6) )
    println(a)
    println(v)
    
    // You can flatten out a vector of vectors
    println(v.flatten)
  }

}