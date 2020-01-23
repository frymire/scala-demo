

object BitwiseOperations extends App {

  val x = 16 // 0001 0000
  val y = 24 // 0001 1000
  
  println("Bitwise And. x & y = 16: " + (x & y))
  println("Bitwise Or.  x | y = 24: " + (x | y))
  println("Bitwise Xor. x ^ y = 8: " + (x ^ y))
  println("Bitwise Ones Complement. ~x = -17: " + (~x))
  println("Bitwise Left Shift. x << 2 = 64: " + (x << 2))
  println("Bitwise Right Shift. x >> 2 = 4: " + (x >> 2))
  
  // Shift right zero fill operator >>>. The left operands value is moved right by the number  
  // of bits specified by the right operand and shifted values are filled with zeros.
  println("Bitwise Shift Right. x >>> 2 = 4: " + (x >>> 2));  
}
