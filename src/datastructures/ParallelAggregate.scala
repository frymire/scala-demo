package datastructures

object ParallelAggregate {

  def main(args: Array[String]): Unit = {
    
    // FIRST EXAMPLE: Order Value
    val numItems = 10
    val prices = Array.fill(numItems)(util.Random.nextInt(9))
    val quantities = Array.fill(numItems)(util.Random.nextInt(9))    
    
    // The map job is to find the total amount spent on a given item
    def map(initialValue: Int, mapJob: (Int,Int) ) = {      
      val (price, quantity) = mapJob      
      println("Map... " + initialValue + " " + mapJob + " " + price*quantity)      
      price*quantity       
    }   
    
    // The reduce job sums the amount spent on each pair of items
    def reduce(y1: Int, y2: Int) = { 
      println("Reduce... " + y1 + " " + y2 + " " + (y1 + y2) )
      y1 + y2       
    } 
        
    val totalOrderValue = (prices zip quantities).par.aggregate(0)(map, reduce)
    println(totalOrderValue)
    
    
   // SECOND EXAMPLE: Auction Bid
    
    case class BidInfo(index: Int, maxValue: Double, var secondBestMaxValue: Double)
    

    val values = Array.fill(numItems)(util.Random.nextInt(9))
    
    println(values mkString " ")
    println(prices mkString " ")

    // The map job is to find the total amount spent on a given item
    def scoreBid(dummyBid: BidInfo, objectID: Int) = {

      val temp = BidInfo(objectID, values(objectID) - prices(objectID), Double.NegativeInfinity)
      println("Map... -> " + temp)
      temp
      
    }
    
    // The reduce job sums the amount spent on each pair of items
    def chooseBid(bid1: BidInfo, bid2: BidInfo) = { 
      
      val temp = 
        if (bid1.maxValue >= bid2.maxValue) { bid1.secondBestMaxValue = math.max(bid1.secondBestMaxValue, bid2.maxValue); bid1 }
        else { bid2.secondBestMaxValue = math.max(bid2.secondBestMaxValue, bid1.maxValue); bid2 }
      
      println("Reduce... " + bid1 + " " + bid2 + " -> " + temp)
      temp
    } 
        
    // In theory, this should work, but I found a lot of mysterious bugs.  Do not use!!!
    val bid = (0 until numItems).par.aggregate( BidInfo(-1, 0, 0) )(scoreBid, chooseBid)
    println(bid)
    
  } // main

}


