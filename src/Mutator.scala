
class Mutator {
  
  var _bar = 1
  
  // Use just the property name as the getter.  Don't use "getBar".
  def bar = _bar
  
  // Use this instead of setBar
  def bar_=(newBar: Int) = { _bar = newBar }    

}

// Demonstrate the Scala Style Guide convention for getters & setters.
object Mutator extends App {
  
  val m = new Mutator  
  println(m.bar)
  
  // When you set it, the "_=" convention makes the code cleaner
  m.bar = 5  
  println(m.bar)
  
}