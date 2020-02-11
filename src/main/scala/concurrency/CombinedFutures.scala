package concurrency

import concurrent.{Await, future, Future}
import concurrent.duration._
import concurrent.ExecutionContext.Implicits.global
import util.Random

object CombinedFutures extends App {

  def doJob() = {
    val delay: Int = Random.nextInt(3000)
    Thread.sleep(delay)
    println(s"Done: $delay ms")
    s"Task with $delay ms" 
  }

  println("\nBoth of the following techniques execute their jobs in parallel, but combine the results differently.")
  
  println("\nFuture.sequence() combines a list of futures into a single future comprised of a single List of job results...")
  val jobsToSequence: List[Future[String]] = List.fill(10)( Future { doJob() } )
  val sequencedJob: Future[List[String]] = Future.sequence(jobsToSequence)
  val jobResults: List[String] = Await.result(sequencedJob, Duration.Inf) 
  jobResults foreach println
  
  println("\nFuture.reduce() returns a future that combines the results of separate jobs into a single reduced result...")
  val jobsToReduce: List[Future[String]] = List.fill(10)( Future { doJob() } )
  val reducedResultFuture: Future[String] = Future.reduce(jobsToReduce) { _ + "\n" + _ } // use reduceLeft() after 2.12.0
  val reducedResult: String = Await.result(reducedResultFuture, Duration.Inf) 
  println(reducedResult) 
}
