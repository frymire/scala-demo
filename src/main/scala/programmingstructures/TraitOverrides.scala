

package programmingstructures

abstract class AClass { val n: Int; val samples: Array[Int]; def report = println("n = " + n + "\t" + samples.size + " samples") }

trait InstantiateMe  { val n: Int;      val samples = (for (i <- 1 to n) yield i) toArray }
trait OverrideMe     { val n = 10;      val samples = (for (i <- 1 to n) yield i) toArray }
trait OverrideMeLazy { val n = 10; lazy val samples = (for (i <- 1 to n) yield i) toArray }


object TraitOverrides {

  def main(args: Array[String]) {
    
    // This doesn't work.  n is zero when samples is built, and then gets set to 5 afterwards.
    (new AClass with InstantiateMe {val n = 5} ).report
    
    // This works fine...
    (new AClass with OverrideMe).report

    // But this doesn't work either, for the same reason as above.
    (new AClass with OverrideMe {override val n = 5} ).report
    
    // Early definition is better, although maybe a little ugly
    (new {override val n = 5} with AClass with OverrideMe).report

    // Could also declare samples as lazy, giving the chance to set n before samples gets built.
    (new AClass with OverrideMeLazy {override val n = 5} ).report    
    
  }

}