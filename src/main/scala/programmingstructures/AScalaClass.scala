
package programmingstructures

//import scala.reflect.BeanProperty

// See "ScalaFromJava.java" in the project with the same name.

trait AScalaTrait { 
  def value: Any
  def printValue = println(value) 
}


class AScalaClass {  
  val valMember = 42;
  var varMember = 0; 
//  @BeanProperty var beanMember = "Before" // generates getters and setters
  def sayHello = println("Hello (class)")  
  def getATuple = (1, 2)     
} // class AScalaClass


object AScalaClass {
  val PI = 3.14
  def sayHello = println("Hello (object)") 
  def youSuck(name: String) = println(s"$name sucks.")  
  def apply10toFunction(f: Int => String) = f(10)
}

