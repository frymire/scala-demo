

package programmingstructures

object VariableArguments {
  
  def sum(args: Int*) = args.sum
  
  def main(args: Array[String]): Unit = {
    
    // This is standard...
    println ( sum(1, 2, 3) )
    
    // You can't do it for something that is already a sequence...
//    println ( sum(1 to 5) )
    
    // But you can indicate to the function that you want the 
    // argument to be treated as a variable argument sequence like this...
    println ( sum(1 to 5: _*) )
    
  }
  

}