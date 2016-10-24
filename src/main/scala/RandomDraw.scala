

object RandomDraw {
  
  // Normalize the likelihoods to get a probability distribution
  def getPDF(likelihoods: Vector[Double]) = { val denominator = likelihoods.sum; likelihoods map {_ / denominator} }
  
  // Get the CDF of the probability distribution, ignoring the first term (0.0).
  def getCDF(pdf: Vector[Double]) = pdf.scanLeft(0.0)(_ + _) drop 1
  
  // Draw a random number from U(0,1), and get the corresponding term from the CDF
  def drawFromCDF(cdf: Vector[Double]) = cdf.indexWhere(_ > util.Random.nextDouble)
  
  // Given the likelihoods corresponding to a set of outcomes, create a PDF and return a single sample from it.
  def sampleGivenLikelihoods(likelihoods: Vector[Double]) = drawFromCDF( getCDF( getPDF(likelihoods) ) )

  
  // Get a random draw from a discrete distribution, starting from a vector of likelihoods.
  def main(args: Array[String]): Unit = {
    
    // Make a PDF and CDF from some likelihoods
    val likelihoods = Vector(2.0, 3.0, 5.0) 
    val pdf = getPDF(likelihoods)
    val cdf = getCDF(pdf)
    println(pdf mkString " ")
    println(cdf mkString " ")
    
    // Draw and print 10 random variates from the CDF
    (0 until 10).map {i => println( drawFromCDF(cdf) )}
    
  } // main

} // RandomDraw