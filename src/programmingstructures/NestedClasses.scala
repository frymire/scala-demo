

package programmingstructures

class Exterior {
  
  class Interior { def apply() = println("Interior.") }
  
  def apply() = println("Exterior.")
  
}

object NestedClasses {

  def main(args: Array[String]): Unit = {
    
    val e = new Exterior
    e()
    
    val i = new e.Interior
    i()
  
    // This doesn't work, though, because the Interior class is specific to each Exterior instance, not the Exterior class.
//    val i = new Exterior.Interior
  
    
  }

}