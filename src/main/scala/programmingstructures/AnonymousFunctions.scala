

package programmingstructures

class ParentDouble(val x: Double)

class ChildDouble(y: Double) extends ParentDouble(y)


class Factor(val function: (ParentDouble) => Double) {
  
  def apply(x: ParentDouble) = function(x) 
}


object AnonymousFunctions {
  
  def main(args: Array[String]): Unit = {
    
    // Here's the normal operation
//    val parentFactor = new Factor( {(x) => 2*x.x} )    
    val parentFactor = new Factor( {2*_.x} )
    println(parentFactor(new ParentDouble(4.0)))
    
    // It's okay to provide an input that is a child of the input type defined for the function
    println(parentFactor(new ChildDouble(3.0)))
    
    // But you can't provide a function where the input type extends that which is expected in the parent
//    val childFactor = new Factor( { (x: ChildDouble) => 3*x.x } )
    
  }

}