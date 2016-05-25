package datastructures

object SeqMethods {
  
  def add1(aSeq: IndexedSeq[Int]) = aSeq map {_ + 1}
  
  def main(args: Array[String]): Unit = {
    
	// Arrays and Vectors are both indexed sequences, so we can use them in the add1 function above.
	// The return type is whatever you sent in, as opposed to an IndexedSeq.
	println( add1( Array(1,2,3) ) )
	println( add1( Vector(4,5,6) ) )
	
	// Find the first element that satisfies a predict and apply a partial function to it.    
	val temp = Seq("a", 3, 5L).collectFirst({ case x: Int => x*10 })
    println(temp)
 
	    
  }

}