
package concurrency

import concurrent.{Await, Future, future, promise}
import concurrent.duration._
import concurrent.ExecutionContext.Implicits.global


class WritingAssignment {  
  val roughDraft = promise[String]
  val reviewedDraft = promise[String]
  val finalDraft = promise[String]  
}


class Writer {
  
  def writeProposal(assignment: WritingAssignment) = {
    
    // Write the rough draft of the assignment  
    println("\nWriter is writing the rough draft...")
    Thread.sleep(1000)
    assignment.roughDraft success "Rough Draft."
    println("Writer is done with rough draft.")
    
    // Wait until the reviewed draft is available
    assignment.reviewedDraft.future onSuccess { case reviewedDraft =>
      
      // Write the final draft of the assignment, based on the reviewed draft.
      println(s"\nWriter is writing the final draft based on: $reviewedDraft")    
      Thread.sleep(3000)    
      assignment.finalDraft success (s"Final Draft based on $reviewedDraft")
      println("Writer is done with final draft.")
    }
        
  } // writeProposal
  
} // class Writer


class Reviewer {
  
  // Wait until the rough draft of the assignment becomes available
  def reviewRoughDraft(assignment: WritingAssignment) = assignment.roughDraft.future onSuccess { case roughDraft =>

    // Write a review based on the rough draft
    println("\nReviewer is reviewing: " + roughDraft)
    Thread.sleep(1000)
    assignment.reviewedDraft success ("Review of " + roughDraft)
    println(s"Reviewer is done with review of rough draft.")
    
  } // reviewRoughDraft
  
} // class Reviewer


// A demo of promises in Scala, based on a proposal-writing exercise
object Promises extends App {
  
  // As an aside, here's how to let two futures compete to fulfill a single promise.
  def first[T](f: Future[T], g: Future[T]): Future[T] = {
    
    val p = promise[T]

    f onSuccess { case x => p.trySuccess(x) }
    g onSuccess { case x => p.trySuccess(x) }
    
    p.future
  }

  // Make a new writing assignment, then tell a writer to write it and a reviewer 
  // to review it.  Note that we can do the last two steps in either order.
  val a = new WritingAssignment
//  new Writer writeProposal(a)
  new Reviewer reviewRoughDraft(a)  
  new Writer writeProposal(a)
  
  // Blocking is generally bad, but we need it here to allow the assignment to be finished.
  Await.result(a.finalDraft.future, Duration.Inf)
  println("\n" + a.finalDraft.future.value)
  
  
  // Now, let's try the competing promise method
  val winner = first( future { Thread.sleep(2000); "Left" }, future {Thread.sleep(3000); "Right" } )
  Await.result(winner, Duration.Inf)
  println("\n" + winner.value)
  
} // object Promises

