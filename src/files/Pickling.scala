//
//package files
//
//// Java stuff
//import java.io.{FileInputStream, FileOutputStream, ObjectInputStream, ObjectOutputStream}
//
//// Scala stuff
//import reflect._
//import pickling._
//import binary._
////import json._
//
//
//abstract class Person { def name: String }
//case class Firefighter(name: String, since: Int) extends Person
//case class Teacher(name: String, since: Int) extends Person
//
//object Pickling extends App {
//  
//  def readPickleFromFile(filename: String) = {
//    
//    val input = new ObjectInputStream(new FileInputStream(filename))
//    val obj = input.readObject
//    input.close
//    
//    if ( !obj.isInstanceOf[BinaryPickle] ) throw new Error("Read object with unexpected type from file.")      
//    obj.asInstanceOf[BinaryPickle]
//    
//  } // readPickleFromFile
//  
//  
//  def writePickleToFile(pickle: BinaryPickle, filename: String) = {
//    
//    val objOut = new ObjectOutputStream(new FileOutputStream(filename))    
//    objOut.writeObject(pickle)  
//    objOut.close
//    
//  } // writePickleToFile
//  
//  
//  // Pickle a double, and print it out in whatever format we used (bytes, in this case).
//  val x = 2.0  
//  val xPickle = x.pickle
//  println("Pickle as bytes: " + (xPickle.value mkString " ") )
//  
//  // Write the pickle to a file
//  writePickleToFile(xPickle, "x.pkl")  
//  println("doublePickle is saved in x.pkl")
//
//  // Read the pickle back in from the file, and use "unpickle" with the type to get the value out.
//  val yPickle = readPickleFromFile("x.pkl")
//  println(s"y is $yPickle  ${yPickle.getClass}")
//  println(yPickle.unpickle[Double])    
//
//  // Can't unpickle without a specific type or with the wrong type.
////  val out3 = yPickle.unpickle
////  val out4 = yPickle.unpickle[Int]
//  
//  // Now, let's pickle classes.  Here, even though the original reference is declared as a Person, the
//  // unpickled type is based on the instance, so both unpickled instances are Firefighters, not Persons.
//  val aPerson: Person = Firefighter("Jim", 2005)
//  val p1 = aPerson.pickle.unpickle[Person]
//  val p2 = aPerson.pickle.unpickle[Firefighter]
//  println(p1 + "\t" + p1.getClass)
//  println(p2 + "\t" + p2.getClass)
//  
//  
//
//  val people: List[Person] = List(Firefighter("Tim", 2013), Teacher("Bob", 1999))
//  println( people.pickle.unpickle[List[Person]] )
//   
//} // Pickling
