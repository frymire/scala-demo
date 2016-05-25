

package programmingstructures

import util.Random

// An algorithm that we want to be able to evaluate externally
class AnAlgorithm {
  
  // An internal state of the algorithm
  var state = Random.nextInt
  
  // Set up a hook to return a string to append to the log messages
  def getIterationLogAppendString = ""
  
  // Do one iteration of the algorithm
  def runIteration(iter: Int) = {
    
    // Update the state
    state = Random.nextInt    
    
    // Write out where we are, including a hook to call to append to the end of the string
    println( "In iteration #" + iter + ", state = " + state + getIterationLogAppendString )    
  }
  
  // Do a specified number of loops
  def loop(numIterations: Int) = (1 to numIterations) map { runIteration(_) }  
  
} // AnAlgorithm


// Set up a trait the gives access to the current state of the algorithm
trait CurrentStateAccess { var state: Int }


object AlgorithmEvaluators {

  def main(args: Array[String]) {
    
    // Here's how to run the algorithm normally
    (new AnAlgorithm).loop(3)
    
    // This time, set it up with an evaluator that computes something (e.g. performance) based on the current state.
    val algorithmWithEvaluator = new AnAlgorithm with CurrentStateAccess {
      
      // Override the function the returns the string to be appended to the log.
      override def getIterationLogAppendString = if (state > 0) ".  Positive." else ".  Negative."            
    } 
    
    // Run it just like before, but now we have client-side performance evaluation.
    println; algorithmWithEvaluator.loop(3) 
        
  } // main

} // AlgorithmEvaluators