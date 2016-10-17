//package scalacheck
//
//import org.scalacheck.Properties
//import org.scalacheck.Prop.forAll
//
//
//// Each Properties object is a complete application in ScalaCheck - it has a main
//// method defined, which simply tests all the properties.
//object StringSpecification extends Properties("String") {
//
//  property("startsWith") = forAll { (a: String, b: String) =>
//    (a+b).startsWith(a)
//  }
//
//  property("concatenate") = forAll { (a: String, b: String) =>
//    (a+b).length > a.length && (a+b).length > b.length
//  }
//
//  property("substring") = forAll { (a: String, b: String, c: String) =>
//    (a+b+c).substring(a.length, a.length+b.length) == b
//  }
//
//}