

package datastructures

object EitherDemo extends App {  

  val in = Console.readLine("Type Either a string or an Int: ")

  val result: Either[String,Int] = try Right(in.toInt) catch { case e: Exception => Left(in) }

  result match {
    case Right(x) => println(s"You passed me the Int: $x, which I will increment: ${x+1}")
    case Left(x) => println(s"You passed me the String: $x")
  }
  
}