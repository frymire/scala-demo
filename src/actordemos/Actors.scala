//
//package actordemos
//
//import scala.actors.Actor
//
//
//// A sample actor class that handles some messages
//class HiActor(shareThreads: Boolean = true) extends Actor {
//    
//  // Go ahead and start (i.e. call act()).  (I'm not sure why you wouldn't.)
//  start  
//  
//  // Define actions to take, depending on the message received.
//  def handleMessage: PartialFunction[Any, Unit] = { 
//    
//    // If we get a string, print it
//    case msg: String => println(msg)
//    
//    // If we get a name, extract its elements and print them in a new format
//    case Name(first, last) => println(last + ", " + first)
//    
//    // If we get a Die object, print the message, and then close down the actor.
//    // Using the exit method let's use specify a reason that can be recovered.
//    case Die => { println("Shutting down..."); exit }
//    
//    // If we get an unknown message type, say we don't know what to do.
//    case msg => println("Unhandled message type in " + getClass + ": " + msg.getClass + ", value = " + msg)
////    case msg => throw new Exception("Unhandled message type in " + getClass + ": " + msg.getClass + ", value = " + msg)
//   
//  } // handleMessage
//    
//  // The Actor trait requires you to override the act() method.  Typically, we call receive in a loop to get each new 
//  // message from the mailbox. If you expect an actor to be working most of the time, using "while(true) { receive ..." 
//  // is appropriate, because it will just sit on its thread doing its work.  On the other hand, if you expect that an actor 
//  // will only be used sporadically, using "loop { react { ..." will make probably make your overall system faster, because 
//  // the seldom-used actor won't hog an entire thread, and instead will only be attached to a thread when it has to respond to 
//  // a message.   This, in turn, means that you could have as many "reacting" actors in your system as you wanted, whereas having 
//  // a large number of threads allocated for them if they were implemented as "receiving" actors would eventually slow the system.
//  // As shown here, "eventloop" is equivalent to "loop { react { ..." for cases when we don't need to call react recursively
//  // within the top level react call (as we would when handling sequences of different message sets).
//  def act = if (shareThreads) Actor.eventloop(handleMessage) else { while(true) receive(handleMessage) }
//  
//  // If we know how to recover from exceptions, we can do that here.
//  //  override def exceptionHandler = { case e => println(s"Error in HCorefActor #$id: $e") }
//
//} // HiActor
//
//
//// Some case classes to use as messages to the HiActor
//case class Name(firstName: String, lastName: String)
//case class Die()
//
//
//// Let's demo how to talk to the actor
//object Actors {
//
//  def main(args: Array[String]): Unit = {
//       
//    // Create and start an actor
//    val anActor = new HiActor
//    
//    // Send it some messages.
//    anActor ! "Hello, cruel world."
//    anActor ! Name("Mark", "Frymire")
//    
//    // It doesn't know how to handle a Double.
//    anActor ! 2.5
//   
//    // Let's kill the actor.  One way we know it worked is that the second message never gets printed, and
//    // this main function can terminate.  If we leave the actor running, it prints the second message and hangs.  
//    anActor ! "Goodbye, cruel world!"
//    anActor ! Die
//    anActor ! "Is this thing still on?"     
//        
//  } // main
//
//} // Actors