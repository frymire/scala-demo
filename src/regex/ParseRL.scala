
package regex

import scala.io.Source
import scala.xml.XML
import scala.xml.Elem
import scala.xml.NodeBuffer
import scala.Array.canBuildFrom

/**
 * Represent a step as written to the logfile in Clausen's RL-based coreference resolution engine
 * 
 * @param input The string extracted from the log file corresponding to one step.
 */
class Step(input: String) {
  
  // Break the input string into a header string and a string for each proposed action
  val headerAndActions = input split "    \\["
  
  // Define a regular expression to parse the header.  (Need to do a better job on the doubles.)
  val headerPattern = "Episode ([0-9]+) Step ([0-9]+)\n" + 
  					  "Performed Action \\[([0-9]+), ([0-9]+), (-?[0-9]+)\\] " +
  					  "Q:([\\+-[0-9]\\.E]+) Number of features changed: ([\\+-[0-9]\\.E]+) " +
  					  "pairwise F1 ([\\+-[0-9]\\.E]+) cluster F1 ([\\+-[0-9]\\.E]+) global F1 ([\\+-[0-9]\\.E]+)\n" +
  					  "Considering the following actions: \n" r
  
  // Parse the header
  val headerPattern(episodeID_, stepID_, cluster1_, cluster2_, moveType_, q_, changes_, pairF1_, clusterF1_, globalF1_) = headerAndActions(0) 
  
  // Convert the extracted strings to member variables of the appropriate type
  val episodeID = episodeID_ toInt
  val stepID    = stepID_    toInt
  val cluster1  = cluster1_  toInt
  val cluster2  = cluster2_  toInt
  val moveType  = moveType_  toInt
  val q         = q_         toDouble
  val changes   = changes_   toDouble
  val pairF1    = pairF1_    toDouble
  val clusterF1 = clusterF1_ toDouble
  val globalF1  = globalF1_  toDouble

  
  // Create proposed action objects from the input strings, ignoring the first one, since it's the header.
  val proposedActions = for (proposedActionText <- (headerAndActions drop 1)) yield new ProposedAction("[" + proposedActionText)
  
  
  /**
   * Write the step to an XML element.
   * 
   * @param featuresToKeep the maximum number of affected features to output for any proposed action
   */
  def toXML(featuresToKeep: Int): Elem = {
    
    // Define a structure that allows us to add nodes sequentially.
    val stepAsXML = new NodeBuffer
    
    // Add the current scores
    //stepAsXML += <scores>{"Global F1: " + globalF1 + "   Pairwise F1: " + pairF1 + "   Cluster F1: " + clusterF1}</scores>
    
    // Add the proposed actions
    stepAsXML += <proposedActions>{for (proposedAction <- proposedActions) yield {proposedAction.toXML(featuresToKeep)}}</proposedActions>
    
    // Return the step as an XML element
    <step ID={stepID toString} globalF1={globalF1 toString} acceptedAction={cluster1 + " " + cluster2 + " " + moveType}>{stepAsXML}</step>    

  }

}


/**
 * Represent a proposed action as written to the logfile in Clausen's RL-based coreference resolution engine
 * 
 * @param input The string extracted from the log file corresponding to one proposed action. 
 */
class ProposedAction(input: String) {
  
  // Break the input string into a header string and a string for each proposed action
  val headerAndFeatureTexts = input split "\n"
  
  // Define a regular expression to parse the header.  (Need to do a better job on the doubles.)
  val headerPattern = "\\[(\\d+), (\\d+), (-?\\d+)\\] Q: ([\\+-[0-9]\\.E]+)" r
  
  // Parse the header, extracting values to member variables of the appropriate type
  val (cluster1, cluster2, moveType, q) = {
    
    val headerPattern(cluster1_      , cluster2_      , moveType_      , q_         ) = headerAndFeatureTexts(0)
    				 (cluster1_ toInt, cluster2_ toInt, moveType_ toInt, q_ toDouble)
    				 
  }

  // Create proposed action objects from the input strings, ignoring the first two, since they're the header and junk text
  val featuresAffected = for (featureText <- (headerAndFeatureTexts drop 2)) yield new Feature(featureText trim)

  
  /**
   * Write the proposed action to an XML element.
   * 
   * @param featuresToKeep the maximum number of affected features to output for any proposed action
   */
  def toXML(featuresToKeep: Int): Elem = {
    
    // Define a structure that allows us to add nodes sequentially.
    val proposedActionAsXML = new NodeBuffer
    
    // Add the affected features
    for (featureAffected <- featuresAffected.take(featuresToKeep)) proposedActionAsXML += featureAffected.toXML
    
    // Return the step as an XML element
    <proposedAction action={cluster1 + " " + cluster2 + " " + moveType}>{proposedActionAsXML}</proposedAction>    

  }
  
}


/**
 * Represent a feature as written to the logfile in Clausen's RL-based coreference resolution engine
 * 
 * @param input The string extracted from the log file corresponding to one feature.
 */
class Feature(input: String) {
  
  val pattern = "(RARF|RARFOA|BABF|RABF): Config (for all|[a-zA-Z]+) of cluster (for all|[a-zA-Z]+) of " +
  				"(pairwise feature [a-zA-Z]+|[a-zA-Z]+ on attribute [0-9]+): ([\\+-[0-9]\\.E]+) " +
  				"Absolute Change: ([\\+-[0-9]\\.E]+) Q contribution :([\\+-[0-9]\\.E]+)" r
  
  // Match the line to the appropriate pattern and extract its values to member variables
  val (featureType, configFx, clusterFx, feature, featureValue, featureChange, deltaQ) = {

	    val pattern(featureType_, configFx_, clusterFx_,  feature_, featureValue_         , featureChange_         , deltaQ_         ) = input
	               (featureType_, configFx_, clusterFx_,  feature_, featureValue_ toDouble, featureChange_ toDouble, deltaQ_ toDouble)

  }
  
  /**
   * Write the feature as an XML element
   */
  def toXML: Elem = {<affectedFeature>{featureType + " " + feature + " " + deltaQ}</affectedFeature>}
  
}


/**
 * Read in a log file from Clausen's RL-based coreference resolution engine, and 
 * write it to an XML file.
 */
object ParseRL {

	def main(args: Array[String]): Unit = {
	  
	  // Read the log file specified on the command line as a string, and split it into a 
	  // a separate string for each step.  Ignore the first empty one.  (It's probably
	  // bad style not to close the file.)
	  val stepTexts = ( ( ( Source fromFile args(0) ) mkString ) split "Episode ") drop 1

	  // Create step objects from the input strings, and group them by episode. Unfortunately, 
	  // the result is an immutable, unsorted map, so we'll have to be sure to traverse it in 
	  // order later.  However, the list of steps for each episode remains in order.
	  val episodes = ( for (stepText <- stepTexts) yield new Step("Episode " + stepText) ).groupBy(_.episodeID)
	  
	  // Write out the episodes as XML
	  XML save("RLLog.xml", <html>{
		  					  for (episode <- 0 until episodes.size) yield <episode ID={episode toString}>{
		  						for (step <- episodes(episode)) yield step.toXML(5)
		  					  }</episode>
		  					}</html> )
	  
	} // main
	
} // ParseRL

