
package programmingstructures

trait ParameterizedTrait[T] { def computeDistance: (T, T) => Double }

class ImplementingClass[T] (val computeDistance: (T, T) => Double) extends ParameterizedTrait[T] 


object Test extends App {
  
  def function = { (i1: Int, i2: Int) => (i1*i2).toDouble }
    
  // You don't even need to say ImplementingClass[Int] here, 
  // since Scala can infer the type parameter automatically
  val myIC = new ImplementingClass(function)
    
  println( function(2,3) )
  println( myIC.computeDistance(2,3) )
    
  
  // As an aside, you can also compose functions like this
  val f = (i: Int) => i.toString
  val g = (s: String) => s+s+s
  val h = g compose f  // : Int => String
  println( g( f(123) ) )
  println( h(123) )
  
}