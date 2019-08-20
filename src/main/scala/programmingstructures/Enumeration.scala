

package programmingstructures

class ColorPrinter(val color: ColorPrinter.Colors.Value) { def printColor = println("The color is " + color + ".") }

object ColorPrinter { 
  
  object Colors extends Enumeration {val Red, Yellow, Green = Value} 

  def test(color: Colors.Value) = println(color)

}


object Enumeration extends App {

  import ColorPrinter.Colors
  
  ColorPrinter.test(Colors.Red)

  (new ColorPrinter(Colors.Yellow)).printColor      
  
  println
  for (c <- Colors.values) yield println(c.id + " " + c)
  
  println 
  for (i <- 0 until Colors.maxId) println(Colors(i))
  
  println("\n" + (Colors withName "Green") )
        
}