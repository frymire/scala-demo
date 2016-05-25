

package programmingstructures

class Foo {

  // Scala doesn't allow static variables, but we can achieve 
  // the same thing by using the companion object below.
  val id = Foo.inc
  def printID = println(id)    
        
  object EmbeddedAutoNumber { private var current = -1; def inc = { current +=1; current } }
  def printEmbed = println(EmbeddedAutoNumber.inc)
    
} // class Foo


// Demo the auto-number capability
object Foo extends App {
  
  // Set up auto-number capability
  private var current = -1
  private def inc = {current += 1; current}
  
  val myFoo1 = new Foo
  val myFoo2 = new Foo

  myFoo1.printID
  myFoo1.printEmbed
  myFoo1.printEmbed     
      
  println
  myFoo2.printID
  myFoo2.printEmbed
            
} // object Foo
