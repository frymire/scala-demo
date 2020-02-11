
package concurrency

import util.{Success, Failure}
import concurrent.{Await, future}
import concurrent.duration._
import concurrent.ExecutionContext.Implicits.global


object Futures extends App {
  
  val SLOW_METHOD_TIME = 2000
  def slowMethod() = { Thread.sleep(SLOW_METHOD_TIME); "I'm done." }
  def slowFailingMethod() = { throw new Error; slowMethod }
  
  println("Trying impatiently, waiting for 1 sec for a 2 sec method...")
  val impatient = future { slowMethod() }
  Thread.sleep(1000)
  println("Impatient result: " + impatient.value)

  println("\nTrying patiently, waiting for 3 sec for a 2 sec method...")
  val patient = future { slowMethod() }
  Thread.sleep(3000)
  println("Patient result: " + patient.value)
  
  // The future might also return an error, which is boxed within.   
  println("\nTrying patiently, waiting for a failing result...")
  val failing = future { slowFailingMethod() }
  Thread.sleep(3000)
  println("Patient Failure result: " + failing.value)

  // Blocking is bad, but it might be necessary to keep the programming from exiting 
  // before the result can be received.  Also, it will end once the result is available,
  // so you don't have to guess how long something will take and sleep for that long.
  println("\nBlocking until the future returns...")
  val blocking = future { slowMethod() }
  val blockingResult = Await.result(blocking, Duration.Inf) // or maybe 500 millis
  println(s"Blocking result: $blockingResult")
  
  // Instead of sleeping or blocking, though, the whole point is to be able to do other work in the meantime.  
  // To do that, specify a callback for when the future completes, and only block at the end of this program.
  println("\nTrying with callbacks, doing other stuff while waiting...")
  val withCallbacks = future { slowMethod() } /* { slowFailingMethod } */ 
  withCallbacks onComplete {
    case Success(str) => println(s"Trying-with-callbacks Success result: $str")
    case Failure(error) => println(s"Trying-with-callbacks Failure result: $error")    
  }  
  
  // NOTE: "onComplete" lets you define success and failure callbacks simultaneously, but you could define them separately.
  // Or, if you only need to register success callbacks, you can use foreach (but this use seems misleading).
//  withCallbacks onSuccess { case str => println(s"Trying-with-callbacks Success result: $str") }
//  withCallbacks onFailure { case error => println(s"Trying-with-callbacks Failure result: $error") }  
//  withCallbacks foreach { str => println(s"Trying-with-callbacks Success result: $str") } 

  // You can define multiple onSuccess callbacks for a given future, but be aware that the order of execution is arbitrary.  
  // Rerunning this multiple times will give different sequences to the value of numAs.  (NOTE: @volatile means the variable 
  // can be updated in multiple threads.)  Since they could also execute in their own threads you might not ever get 18, since 
  // the += operation is not atomic.
  println("\nCompute the number of \'a\'s or \'A\'s to demonstrate non-atomicity of multiple onSuccess callbacks...")
  val multiCallback = future { "na" * 16 + "BATMAN!!!" }  
  @volatile var numAs = 0
  multiCallback onSuccess { case txt => { numAs += txt.count(_ == 'a'); println(s"#As = $numAs") } }
  multiCallback onSuccess { case txt => { numAs += txt.count(_ == 'A'); println(s"#As = $numAs") } }
  
  println("\nUse map to create a future from another future, extracting the result from the original...")
  val mapped = future { slowMethod() } map { _ + " Finally!" }
  mapped onSuccess { case str => println(s"After mapping two futures: $str") } 
      
  println("\nUse a for-comprehension to create a future from multiple input futures in sequence...")
  def quote1 = future { Thread.sleep(2000); 5 }
  def quote2 = future { Thread.sleep(2000); 10 }
  val highestValue = for { q1 <- quote1; q2 <- quote2 if (q2 > 8) } yield math.max(q1, q2)
  highestValue onSuccess { case str => println("Max value returned from multiple futures computed in sequence: " + str) }
  
  println("\nLet previous futures complete before terminating....")
  List(withCallbacks, multiCallback, mapped, highestValue) foreach { Await.result(_, Duration.Inf) }
}
