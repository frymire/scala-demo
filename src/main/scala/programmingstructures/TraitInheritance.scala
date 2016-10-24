

package programmingstructures

object TraitInheritance {

  // Diamond Problem
  trait Parent {val text: String}
  trait Left {val text = "Left"}
  trait Right {val text = "Right"}
  
  
  //Application example

  trait Samplable                       { def generateSample }
  class Distribution1 extends Samplable { def generateSample = println("Here's a Distribution1 sample.") }
  class Distribution2 extends Samplable { def generateSample = println("Here's a Distribution2 sample.") }

  trait PValueEstimator { def getPValue }
  trait MonteCarloPValueEstimator extends PValueEstimator with Samplable { def getPValue = for (i <- 1 to 3) generateSample }

    
  def main(args: Array[String]): Unit = {
    
    // Scala prevents the diamond problem
//    val test = new Parent with Left // This works just fine.  
//    val test = new Parent with Left with Right // Can't do this.  The "text" variable conflicts
    val test = new Parent with Left with Right {override val text = "Overriden."}  // Overriding text in both traits works. 
    println(test.text)
    
    //Application example: we can use any Distribution with MonteCarloPValueEstimator
    val myD1P = new Distribution1 with MonteCarloPValueEstimator;  println;  myD1P.getPValue
    val myD2P = new Distribution2 with MonteCarloPValueEstimator;  println;  myD2P.getPValue    

  }

}