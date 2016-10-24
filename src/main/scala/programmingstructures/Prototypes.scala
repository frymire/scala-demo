
package programmingstructures

trait Speaks { val msg: String; def speak = println(msg) }

class Dog extends Speaks { val msg = "Woof." }
class Cow extends Speaks { val msg = "Mooo." }

// You wish you could do the first version, but type erasure prevents Java from knowing T
// Instead, you have to provide a constructor for the type.
// class Speaker[T <: Speaks] extends Speaks { val msg = (new T).msg } //error } 
class Speaker[T <: Speaks](val constructT: () => T) extends Speaks { val msg = constructT().msg }

object Prototypes extends App {
  
  val myDogSpeaker = new Speaker( { () => new Dog } )   
  myDogSpeaker.speak
  
  val myCowSpeaker = new Speaker( { () => new Cow } )   
  myCowSpeaker.speak
  
}