package programmingstructures


//class MyPair[T](val first: T, val second: T)  // Invariant
class MyPair[+T](val first: T, val second: T)  // Covariant

class Person(val name: String) { override def toString = name }

case class Student(name1: String) extends Person(name1)

object Variance extends App {
  
  def makeFriends(p: MyPair[Person]) = println(s"${p.first} and ${p.second} are now friends.")
  
  val bros = new MyPair( new Person("Mark"), new Person("Kurt") )
  val classmates = new MyPair( new Student("Mark"), new Student("Kurt") )
  
  // Even though Student is a subclass of Person, you can't make the second call, because with MyPair[T],
  // MyPair[Person] and MyPair[Student] have no relation whatsover.  On the other hand, with MyPair[+T],
  // the inheritance relationship Student -> Person extends covariantly to MyPair[Student] -> MyPair[Person].
  makeFriends(bros)
  makeFriends(classmates)  

}