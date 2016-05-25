

package datastructures

object Maps extends App {

	val m1 = Map(1 -> "A", 2 -> "AA", 3 -> "AAA")
	
	println(m1(1))
	println(m1.get(4)) // returns an option
//	println(m1(4)) // error.  returns the value, or throws an error if it isn't there

	println(m1)
	println(m1 + (1 -> "B") )    
	println(m1 ++ { if(true) Map(1 -> "B") else Map() })

	// Convert an array to a map
	val a = Array(6,7,8,9,10)
	//    println(a.toMap) Doesn't work
	println( a.zipWithIndex.map {_.swap} toMap )
	
	// If you combine maps, k-v pairs in the second map will overwrite key matches in the first
	val m2 = Map(1 -> "B", 4 -> "BB", 5 -> "BBB")
	println(m1 ++ m2)
	println(m2 ++ m1)
	
	// Here's what zip does.
	println(m1 zip m2)

}