
package datastructures


case class Student(name: String, score: Int)

object Reduction extends App {
  
  val alex = Student("Alex", 83)
  val david = Student("David", 80)
  val frank = Student("Frank", 85)
  val julia = Student("Julia", 90)
  val kim = Student("Kim", 95)
  
  val students = Seq(alex, david, frank, julia, kim)
  
  def max(s1: Student, s2: Student): Student = if (s1.score > s2.score) s1 else s2

  val topStudent = students.reduceLeft(max)
  println(s"${topStudent.name} had the highest score: ${topStudent.score}")

}