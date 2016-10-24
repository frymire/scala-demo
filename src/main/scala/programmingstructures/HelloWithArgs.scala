

package programmingstructures


class YouSuck { def main(args: Array[String]) = println(s"${args(1)} sucks.") }

object HelloWithArgs {

  def main(args: Array[String]) = {
    
    val ys = new YouSuck
    //ys main Array("Tony", "Romo")
    ys main ("Tony Romo" split " ")
    
    // Demonstrate that we can loop over the input arguments and do stuff
    val lowerCaseArgs = for (a <- args) yield a toLowerCase
    
    // Print something to the console.
    print("Arguments:")
    for (lca <- lowerCaseArgs) print(" " + lca toString)
    
  } // main

} // HelloWithArgs

// Here's an identical version that extends App.  This way, you don't need the main function, and 
// instead, Scala just starts executing whatever is in the object definition.  Also, if you add
// "-Dscala.time" as a VM command in the run configuration, it prints the running time of the program.
// If you don't extend App, this command doesn't do anything.
object HelloWithArgs1 extends App {
  
  val ys = new YouSuck
  //ys main Array("Tony", "Romo")
  ys main ("Tom Brady" split " ")
	
  // Demonstrate that we can loop over the input arguments and do stuff
  val lowerCaseArgs = for (a <- args) yield a toLowerCase
	
  // Print something to the console.
  print("Arguments:")
  for (lca <- lowerCaseArgs) print(" " + lca toString)
    
} // HelloWithArgs1