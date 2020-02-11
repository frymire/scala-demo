
package concurrency

import concurrent.{Await, Future, Promise}
import concurrent.duration._
import concurrent.ExecutionContext.Implicits.global


class WritingAssignment {  
  val roughDraft = Promise[String]
  val reviewedDraft = Promise[String]
  val finalDraft = Promise[String]  
}


class Writer {
  
  def writeProposal(assignment: WritingAssignment) = {
    
    println("\nWriter is writing the rough draft...")
    Thread.sleep(1000)
    assignment.roughDraft success "Rough Draft."
    println("Writer is done with rough draft, awaiting reviewed draft.")

    assignment.reviewedDraft.future onSuccess { case reviewedDraft =>
      println(s"\nWriter is writing the final draft based on: $reviewedDraft")    
      Thread.sleep(3000)    
      assignment.finalDraft success (s"Final Draft based on $reviewedDraft")
      println("Writer is done with final draft.")
    }        
  }  
}


class Reviewer {
  def reviewRoughDraft(assignment: WritingAssignment) = assignment.roughDraft.future onSuccess { case roughDraft =>
    println("\nReviewer is reviewing: " + roughDraft)
    Thread.sleep(1000)
    assignment.reviewedDraft success ("Review of " + roughDraft)
    println(s"Reviewer is done with review of rough draft.")    
  }  
}


// A demo of promises in Scala, based on a proposal-writing exercise
object Promises extends App {
  
  println("\nMake a new writing assignment, then tell a writer to write it and a reviewer to review it (in either order)...") 
  val writer = new Writer
  val reviewer = new Reviewer
  val assignment = new WritingAssignment
  reviewer.reviewRoughDraft(assignment)  
  writer.writeProposal(assignment)
  println(s"\nProduct: ${Await.result(assignment.finalDraft.future, Duration.Inf)}")
  
  println("\nLet two futures race to fulfill a promise...")
  val p = Promise[String]
  val f1 = Future { Thread.sleep(3000); "3 seconds" }
  val f2 = Future { Thread.sleep(2000); "2 seconds" }
  f1 onSuccess { case str => p.trySuccess(str) }
  f2 onSuccess { case str => p.trySuccess(str) }
  println(Await.result(p.future, Duration.Inf))  
}
