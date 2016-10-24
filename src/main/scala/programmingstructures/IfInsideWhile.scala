

package programmingstructures

import java.util.Random


object IfInsideWhile {

  def main(args: Array[String]): Unit = {
    
    var output = 0.0

    val rng = new Random

    var gotIt = false        
    while (!gotIt) { val u = rng.nextDouble; if (u < 0.5) {gotIt = true; output = u} }
    
    println(output)
    
  }

}