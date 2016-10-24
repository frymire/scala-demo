package datastructures

import java.io.{FileInputStream, ObjectInputStream}

import reflect._
import reflect.runtime.universe._

object Reflection extends App {
  
  // NOTE: I first set this up using Manifests, but then found that those are deprecated in favor of TypeTags / ClassTags.
  
  // If you wanted to print the name of a generic type used in a method, you can't use the type parameter
  // directly.  Instead, you have to have an instance of type T and get its class.
//  def printClassName[T] = println(T)  // error
  def printClassName[T](x: T) = println(x.getClass)
  printClassName(3.14)
  
  // Using an implicit ClassTag parameter, you can do this without passing an instance.  The two definitions are equivalent.
//  def printClassName[T](implicit tag: ClassTag[T]) = println(tag)
  def printClassName[T: ClassTag] = println(classTag[T])
  printClassName[Int => Int]
  
  // If you use a TypeTag instead of a ClassTag, any type parameters to the top-level type are included
  def printFullClassName[T: TypeTag] = println(typeTag[T])
  printFullClassName[Int => Int]
  
  def printTypeArguments[T: TypeTag](x: T) = {
    val TypeRef(_, _, args) = typeOf[T]
    println(s"Type arguments for $x: ${args mkString " "}")
  }
  printTypeArguments(42)
  printTypeArguments( List(1, 2) )

  // Here's another example that returns an array instance of a type learned at run-time. 
//  def makePairArray[T] = new Array[T](2) // error
//  def makePairArray[T: TypeTag] = new Array[T](2)  // error, not sure why
  def makePairArray[T: ClassTag] = new Array[T](2)
  println( makePairArray[Int] mkString " " )
  println( makePairArray[String] mkString " " )
  
  // We can use the "<:<" method  to determine subtype relationships (approximately).
  // This enables use cases where you do different things based on the type.
  def add[T: TypeTag](anArray: Array[T]) = typeOf[T] match {
      
      case t if (t =:= typeOf[String] ) => println(anArray mkString " ")
      
      case t if (t <:< typeOf[Int] ) => println(anArray.asInstanceOf[ Array[Int] ] sum)
      
      case t => println("Not sure what to do with an array of type " + t)

  } // add

  
  add( Array("Mark", "Frymire") )
  add( Array(1, 2, 3) )
  add( Array(0.1, 2.3, 4.5, 6.7) )
  
  
  // Here's a practical application, if you won't know what objects you'll be reading until run-time.  
  def readObjectFromFile[A: ClassTag](filename: String): A = {
    
    val input = new ObjectInputStream(new FileInputStream(filename))
    val obj = input.readObject
    // TODO: Don't we need to close the input and the file?  Does this happen automatically?
//    input.close  
//    file.close
    
    if (!classTag[A].runtimeClass.isInstance(obj) ) throw new Error("Read object with unexpected type from file.")      
    obj.asInstanceOf[A]
    
  } // readObjectFromFile
    
  
} // object Reflection
