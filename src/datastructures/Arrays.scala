
package datastructures

import scala.collection.immutable.Vector
import scala.Array.canBuildFrom

object Arrays {

  def main(args: Array[String]): Unit = {

    // Arrays are mutable    
    val t1 = Array(1, 2, 3)
    println(t1 mkString " ")
    t1(1) = 5
    println(t1 mkString " ")

    // Arrays are fixed length, but you can concatenate them like this
    println
    println(t1 ++ Array(4, 5) mkString " ") // concatenate another array
    println(t1 :+ 6 mkString " ") // concatenate a single element

    // Vectors are fixed length and immutable.  You can't update a vector in place, 
    // but you can create a new one with a value replaced.
    val v1 = Vector(1, 2, 3)
    println; println(v1 mkString " ")
    //v1(2) = 4 Error  
    val v2 = v1.updated(2, 4)
    println(v2 mkString " ")

    // Print all of the permutations and combinations
    println
    for (permutation <- v2.permutations) println(permutation.mkString(" "))
    for (i <- 0 to v2.length; combination <- v2.combinations(i)) println(combination.mkString(" "))
    
    // The deep method in ArrayLike creates nested sequences, so we can apply a method to embedded Sequences
	val recursiveArray = Array( Array(1,2,3), Vector(4,5,6) )
	println; println(recursiveArray)
	println(recursiveArray.deep)
	
	
	// Make a sliding window of size 2 and move it along an array.
	val t2 = Array(1,2,3,4,5)	
	t2.sliding(2) foreach { x => println(x(0) + "\t" + x(1)) }
	
	
	// Deep copy
	val t4 = Array(1,2,3)
	val t5 = t4
	val t6 = t4.clone
	t4(1) = 4
	
	println(t4 mkString "\t")
	println(t5 mkString "\t")
	println(t6 mkString "\t")

  }

}