
package datastructures

import scala.collection.immutable.Set
import scala.collection.mutable.ArrayBuffer

object SetTest {

  def main(args: Array[String]): Unit = {
    
    // Make an array
    val mutableArray = ArrayBuffer(10, 11, 12, 13, 13)
    println("Before: " + mutableArray)
    
    // Remove the 3rd element and add a new element at the end
    mutableArray.remove(2)
    mutableArray += 9
    println("After: " + mutableArray)
    
    // Make a immutable set from a mutable array, and verify that you can't change its elements from the 
    // source array.
    val immutableSetFromMutableArray = mutableArray.toSet
    println("Array to Set: " + immutableSetFromMutableArray)
    mutableArray(0) = 50
    println(mutableArray)
    println("Array to Set (mutation had no effect): " + immutableSetFromMutableArray)
    
    
    // Make a set.  Note that the last "13" is ignored
    val setBefore = Set(10, 11, 12, 13, 13)
    println(setBefore)
    
    // Remove the "12" element, and add a new element
    val setAfter = setBefore - 12 + 9
    println(setBefore)
    println(setAfter)
    
    // Order of operations is left to right
    println("Order of operations test #1: " + (Set(1,2,3) ++  Set(3,4) -- Set(3)  ) )
    println("Order of operations test #2: " + (Set(1,2,3) ++ (Set(3,4) -- Set(3)) ) )
    
  }

}