package files

object ReadFromFile extends App {
  val lines = io.Source.fromFile("HelloWorld.txt").getLines()
  lines foreach println
}