

package programmingstructures

class ColorPrinter(val color: ColorPrinter.Colors.Value) { def printColor = println("The color is " + color + ".") }

object ColorPrinter { 
  
  object Colors extends Enumeration {val Red, Yellow, Green = Value} 

  def test(color: Colors.Value) = println(color)

}


object Enumeration {

  def main(args: Array[String]) {
    
    //import ColorPrinter.Colors._ // Could do this and just say "Yellow" and "values" below.
    
    ColorPrinter.test(ColorPrinter.Colors.Red)

    (new ColorPrinter(ColorPrinter.Colors.Yellow)).printColor      
    
    println; 
    for (c <- ColorPrinter.Colors.values) yield println(c.id + " " + c)
    
    println; 
    for (i <- 0 until ColorPrinter.Colors.maxId) println( ColorPrinter.Colors(i) )
    
    println("\n" + (ColorPrinter.Colors withName "Green") )
        
  }

}