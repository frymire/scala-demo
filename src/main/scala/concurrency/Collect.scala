package concurrency

import concurrent.{Await, future, Future}
import concurrent.duration._
import concurrent.ExecutionContext.Implicits.global
import util.Random

object Collect extends App {

  // Define a job we want to do   
  def doJob(delay: Int = Random.nextInt(3000) ) = { Thread.sleep(delay); println(s"Done: $delay ms"); s"Task with $delay ms" }

  // We can use the "Future.sequence" call to make a combined future to use to wait for a set of futures to finish
  val jobs1 = List.fill(10)( future { doJob() } )
  Await.result(Future.sequence(jobs1), Duration.Inf) foreach println
  
  // That didn't combine the separate futures into a combined result, though.  Here's how to do that.
  println
  val jobs2 = List.fill(10)( future { doJob() } )
  println( Await.result( Future.reduce(jobs2) { _ + "\n" + _ }, Duration.Inf) )

} // object Collect