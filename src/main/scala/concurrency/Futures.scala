
package concurrency

import util.{Success, Failure}
import concurrent.{Await, future}
import concurrent.duration._
import concurrent.ExecutionContext.Implicits.global


object Futures extends App {
  
  val SLOW_METHOD_TIME = 2000
  def slowMethod = { Thread.sleep(SLOW_METHOD_TIME); "I'm done." }
  def slowFailingMethod = { throw new Error; slowMethod }
  
  println("Trying impatiently...")
  val impatient = future { slowMethod }
  Thread.sleep(1000)
  println("Impatient result: " + impatient.value)

  println("\nTrying patiently...")
  val patient = future { slowMethod }
  Thread.sleep(3000)
  println("Patient result: " + patient.value)
  
  // Blocking is bad, but it might be necessary to keep the programming from exiting 
  // before the result can be received.  Also, it will end once the result is available,
  // so you don't have to guess how long something will take and sleep for that long.
  println("\nBlocking...")
  val blocking = future { slowMethod }
  Await.result(blocking, Duration.Inf) // or maybe 500 millis
  println("Blocking result: " + patient.value)

  
  // The future might also return an error, which is boxed within   
  println("\nFailing patiently...")
  val failing = future { slowFailingMethod }
  Thread.sleep(3000)
  println("Patient Failure result: " + failing.value)

  
  // Instead of sleeping or blocking, though, the whole point is to be able to do other 
  // work in the meantime.  We'll specify a callback to use when efficient is done.
  println("\nTrying efficiently...")
  val efficient = future { slowMethod } // { slowFailingMethod }
  efficient onComplete {
    case Success(str) => println(s"Efficient result: $str")
    case Failure(error) => println(s"Efficient failure result: $error")    
  }
  
  // NOTE: while "onComplete" handles both successes and failures, you could split them out like this.
//  efficient onSuccess { case str => println(s"Efficient: $str") }
//  efficient onFailure { case error => println(s"Efficient error: $error") }
  
  println("Doing some other stuff while waiting...")
  Await.result(efficient, Duration.Inf)
  

  // You can define multiple onSuccess callbacks for a given future, but we aware that
  // the order of execution is arbitrary.  Rerunning this multiple times will give 
  // different sequences to the value of totalA.  (NOTE: @volatile means the variable can 
  // be updated in multiple threads.)  Since they could also execute in their own threads
  // you might not ever get 18, since the += operation is not atomic.
  @volatile var totalA = 0
  val text = future { "na" * 16 + "BATMAN!!!" }  
  text onSuccess { case txt => { totalA += txt.count(_ == 'a'); println(totalA) } }
  text onSuccess { case txt => { totalA += txt.count(_ == 'A'); println(totalA) } }
  Await.result(text, Duration.Inf)
  
  // To make a future based on another future, you first need to extract the values from 
  // the original.  The map method is probably the easiest way to do this.
  val slow = future { slowMethod }
  val updatedSlow = slow map { _ + " Finally!" }
  Await.result(updatedSlow, Duration.Inf)
  println("\n" + updatedSlow.value)
      
  // If you want to make a future from multiple input futures, you could use a flatMap, but a for-comprehension is cleaner.
  // Be careful, though, because this blocks on the first future before it gets the second.
  def slowQuote = future { Thread.sleep(5000); 5 }
  def fastQuote = future { Thread.sleep(5000); 10 }
  val highestValue = for { slow <- slowQuote; fast <- fastQuote } yield math.max(slow, fast)  
  Await.result(highestValue, Duration.Inf)
  println("\n" + highestValue.value)    
  
}
