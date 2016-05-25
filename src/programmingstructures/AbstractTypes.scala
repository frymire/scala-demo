package programmingstructures


class Parent
class Parent1 extends Parent
class Parent2 extends Parent

trait Child extends Parent { val x: Any }
class Child1 extends Child { val x = 1 }
class Child2 extends Child { val x = "Mark" }

trait DataModel { 
  type P <: Parent
  type C <: Child  
  def createC: C
}

class DataWithBehavior(val model: DataModel) extends DataModel {
  
  // Normally with abstract types, we'd hard code the types for a particular model.  Instead, though, we can
  // just read the types off of a parameter instance passed in to the constructor.
  type P = model.P
  type C = model.C  
  def createC = model.createC
  
  def sayHi(aC: C) = println(createC.x)
  
}


object model11 extends DataModel { type P = Parent1; type C = Child1; def createC = new Child1 }
object model12 extends DataModel { type P = Parent1; type C = Child2; def createC = new Child2 }
object model21 extends DataModel { type P = Parent2; type C = Child1; def createC = new Child1 }
object model22 extends DataModel { type P = Parent2; type C = Child2; def createC = new Child2 }

// Scala catches you if you violate the type constraints.
//object badModel1 extends MyClassModel { type P = Parent2; type C = Child2; def createC = new Child1 }  // error
//object badModel2 extends MyClassModel { type P = Parent; type C = Parent2; def createC = new Parent2 }  // error


trait SaysSomething {
  val model: DataModel    
  def sayHi(aChild: model.C) = println(aChild.x)  
}

// Surprisingly, you can't do this.  If you try to say "M.C" Scala doesn't recognize it.
//trait SaysSomething[M <: DataModel] { def sayHi(aChild: M.C) = println(aChild.x) }

class DataFromModel(val model: DataModel) extends DataModel {
  
  // Normally with abstract types, we'd hard code the types for a particular model.  Instead, though, we can
  // just read the types off of a parameter instance passed in to the constructor.
  type P = model.P
  type C = model.C  
  def createC = model.createC
  
}


// Trying to use the apply method of MyClass2 to make sure the data model and behavior model match doesn't work.  
object DataFromModel {  
  def apply(theModel: DataModel) = new DataFromModel(theModel) with SaysSomething { override val model = theModel }
}

object AbstractTypes extends App {
  
  val myInstance1 = new DataWithBehavior(model11)
  val myInstance2 = new DataWithBehavior(model22)
  
  // You wish you could pass Child instances created at the client level, given that the type C for myInstance1 is, in fact Child1.  
  // However, you have to use the constructor from the model, which requires that the myInstance variables have already been created.
//  myInstance1.sayHi( new Child1 )
//  myInstance1.sayHi( model1.createC )  
  myInstance1.sayHi( myInstance1.createC )  
  myInstance2.sayHi( myInstance2.createC )
  
  // If you declare the model data with a trait, then you can get type checking by overriding the model parameter 
  println
  val myInstance3 = new DataFromModel(model11) with SaysSomething { override val model = model11 }
  myInstance3.sayHi( new Child1 )
  myInstance3.sayHi(model11.createC)
//  myInstance3.sayHi( new Child2 )
//  myInstance3.sayHi(model2.createC)  

  // The previous version allows you to put a different model in as the parameter to DataFromModel and in the 
  // override for SaysSomething.  I was hoping you could use the apply method to make sure the data model and 
  // behavior model match, but it doesn't work.
  val myInstance4 = DataFromModel(model11)
//  myInstance4.sayHi( new Child1 )
//  myInstance4.sayHi(model11.createC)
//  myInstance4.sayHi( new Child2 )
//  myInstance4.sayHi(mode22.createC)
  
  // This seems equivalent to using a SaysSomething class, but by overriding the model parameter, you can
  // guarantee that the type of data created outside the SaysSomething trait can be used inside.
  val myInstance5 = new SaysSomething { val model = model11 }
  myInstance5.sayHi( new Child1 )
  myInstance5.sayHi(model11.createC)
//  myInstance5.sayHi( new Child2 )
//  myInstance5.sayHi(mode22.createC)
    
}
