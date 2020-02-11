

object BitwiseOperations extends App {

  implicit def binaryString2Int(in: String) = Integer.parseInt(in.replaceAll("\\s", ""), 2)
  def hexString2Int(in: String) = Integer.parseInt(in.replaceAll("\\s", ""), 16)
  
  
  val binX: Int = "0001 0000"  // 16
  val binY: Int = "0001 1000"  // 24
  
  val decX: Int = 16
  val decY: Int = 24
  
  val hexX: Int = 0x10 // 16
  val hexY: Int = 0x18 // 24
  
  println(hexString2Int("10"))
  println(hexString2Int("18"))
  println(hexString2Int("00 00 04 18"))
  println(hexString2Int("7f ff ff ff"))
  
  println("Compare hex to decimal: 0x18 == 24 --> " + (hexY == 24))
  
  println(s"\nx = 16 as binary: 0b${binX.toBinaryString}")
  println(s"y = 24 as binary: 0b${binY.toBinaryString}")
  
  println(s"\nx = 16 as hex: 0x${binX.toHexString}")
  println(s"y = 24 as hex: 0x${binY.toHexString}")
  
  println("\nBitwise And. x & y = 16: " + (binX & binY) + " " + (decX & decY) + " " + (hexX & hexY))
  println("Bitwise Or.  x | y = 24: " + (binX | binY))
  println("Bitwise Xor. x ^ y = 8: " + (binX ^ binY))
  println("Bitwise Ones Complement. ~x = -17: " + (~binX))
  println("Bitwise Left Shift. x << 2 = 64: " + (binX << 2))
  println("Bitwise Right Shift. x >> 2 = 4: " + (binX >> 2))
  
  // Shift right zero fill operator >>>. The left operand's value is moved right by the number  
  // of bits specified by the right operand and shifted values are filled with zeros.
  println("Bitwise Shift Right. x >>> 2 = 4: " + (binX >>> 2));  
  
  /* Returns the byte from the least significant end of the provided Int. */
  def getByteFromInt(x: Int, byteIndex: Int): Byte = (x >> 8*byteIndex).toByte
  
  
//  def pad(str: String, nBits: Int = 16) = {
//    val unpaddedString = str.takeRight(nBits);
//    ("0". * (nBits - unpaddedString.length)) + unpaddedString     
//  }

  
  val hexZ = 1048 // 134678021
//  val hexZ = 0x082716A5 // 134678021
  println("\n" + hexZ)
  println(hexZ.toHexString.toUpperCase())
  val byteStrings = (0 to 3) map { position =>
    val theByte: Byte = getByteFromInt(hexZ, 3 - position)
    val theString = theByte.toInt.toHexString.takeRight(2).toUpperCase()
    if (theString.size == 1) { "0" + theString } else { theString }
  }
  println(byteStrings mkString " ")
    
  /** Returns a byte from a list of positions with bits to be set to 1. */
  def bits2Byte(ones: List[Int]) = ones map { (1 << _) } reduce { _ | _ } toByte;
  println("\nCreate the Byte b01001000 by specifying the bits to be flipped...")
  println(bits2Byte(List(6, 3)).toBinaryString)  
}
