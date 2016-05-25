//
//package actordemos
//
//import scala.actors.{Actor, OutputChannel}
//
//
//// Set up a mapper actor that does some math and forwards the result to a reducer, specified in the constructor
//class Mapper(val theReducer: OutputChannel[(Int, Int)]) extends Actor {
//  
//  // Get a unique ID for this mapper
//  val mapperID = Mapper.getNextID
//  
//  // Go ahead and start this actor
//  start
//  
//  // Loop to get messages from the mailbox
//  def act = while (true) receive {
//    
//    // If we get an int, pass it to the reducer.  Using an output channel here constrains us to send an (Int, Int)
//    // Furthermore, it prevents us from having direct access to the reducer actor, so we can't accidentally invoke a 
//    // method on it, which would risk race conditions.  Using channels limits us to the safer message passing model.
//    case i: Int => { Thread.sleep(300); theReducer ! (mapperID, i) }
//    
//    // If we get a Die object, close down.
//    case Die => { println("Mapper #" + mapperID + " shutting down..."); reply(); exit }
//    
//    // If we get a message type we don't know about, throw an error
//    case msg => throw new Exception("Unhandled message type in " + getClass + ": " + msg.getClass + ", value = " + msg)
//    
//  } // act
//  
//} // Mapper
//
//
//object Mapper { private var nextID = -1; def getNextID = { nextID += 1; nextID } }
//
//
//
//class Reducer extends Actor {
//  
//  // Set up a running sum  
//  var sum: Double = 0
//  
//  // Go ahead and start this actor
//  start
//
//  // Loop to get messages from the mailbox
//  def act = while (true) receive {
//    
//    // If we get an int from a mapper, increment the running sum, and print an update.
//    case (mapperID: Int, i: Int) => { sum += i; println(mapperID + "\t" + i + "\t" + sum) }
//    
//    // If we get a Die object, close down.
//    case Die => { println("Reducer shutting down...  Sum = " + sum); exit }
//    
//    // If we get a message type we don't know about, throw an error
//    case msg => throw new Exception("Unhandled message type in " + getClass + ": " + msg.getClass + ", value = " + msg)
//    
//  } // act
//  
//} // Reducer
//
//
//
//object ActorMapReduce {
//
//  def main(args: Array[String]): Unit = {
//    
//    // Set up a reducer, so we can use it when we set up the mappers
//    val theReducer = new Reducer
//
//    // Set up an array of mappers, passing the reducer to each to use as its output channel.
//    val numMappers = 10
//    val mappers = (0 until numMappers).map {_ => new Mapper(theReducer) }
//    
//    // Split the set of integers evenly over the set of mappers.  
//    for (i <- 1 to 100) { mappers(i % numMappers) ! i }
//    
//    // At this point, it's safe to kill the mapping actors, since no more messages are coming.  You can't kill
//    // the reducer right away, though, because it might not have received all of the messages from the mappers.  
//    // To make sure that all of the mappers get a chance to send all of their messages first, we'll use a synchronous 
//    // exchange with "!?" when sending the Die message to each of them.  The first line below will block until all of 
//    // the mappers have replied to acknowledge that they've received the shutdown message, which can only happen after 
//    // they've sent all of their work messages to the reducer queue.  At that point, it is safe to put the Die message 
//    // at the end of the reducer's queue.
//    mappers foreach { _ !? Die }
//    theReducer ! Die
//    
//  } // main
//
//} // ActorMapReduce
