
package files

// Scala imports
import scala.xml.{XML, Elem, NodeBuffer, Attribute, Null}


object XMLTest {
 
  /**
   * Generate a song.
   */
  private def generateSong(artistName: Array[String]): Elem = {
    
    // To define a song in XML from scratch, first define a structure that allows us to add 
    // nodes sequentially. 
    val song = new NodeBuffer
    
    // Add the artist to the song.
    song += <artist>{for (arg <- artistName) yield <name>{arg}</name>}</artist>
    
    // Add the title to the song
    song += <title>I'm sexy and I know it.</title>
      
    // Add some lyrics to the song
    song += generateLyrics
    
    // Return the song as an XML element
    <song>{song}</song>

  }
  
  
  /**
   * Generate some song lyrics.
   */
  private def generateLyrics: Elem = {
    
    // Set up a buffer that we will use to add multiple lines at once at the end of this block
    val lyrics = new NodeBuffer
   
    // Add 4 lines, each with an attribute "number" set as the result of a Scala expression  
    for (i <- 1 to 4) lyrics += <line number={i toString}>Girl, look at that body.</line>
        
    // Let's change the last line.  This is possible since NodeBuffer is mutable, unlike NodeSeq.
    // NOTE: You can make a new Elem with a different child using copy(child = ...), but there 
    // doesn't appear to be a way to change just the body.
    lyrics(3) = <line>"I work out"</line> 
    
    // Let's change the label
    lyrics(3) = lyrics(3).asInstanceOf[Elem] copy(label = "lastLine")
    
    // Let's add a "number" attribute and set it to "4*"
    lyrics(3) = lyrics(3).asInstanceOf[Elem] % Attribute(null, "number", "4*", Null)
    
    // Let's change the "number" attribute to "4"
    lyrics(3) = lyrics(3).asInstanceOf[Elem] % Attribute(null, "number", "4", Null)
    
    // We actually just made four new nodes, but each refers to the contents of the previous ones, 
    // so it's generally still efficient.  
    
    // Return the lyrics as an XML element, along with an XML comment
    <lyrics><!--This is the bridge.-->{lyrics}</lyrics>

  } // end getLyrics
  
  
/**
 * Demonstrate creating and changing XML, and reading it from and writing it to a file.
 */

  def main(args: Array[String]): Unit = {
    
    // Generate a song and write it to a file
    XML save("Sexy.xml", <html>{generateSong(args)}</html>)

    // Read in the XML file
    val xmlFromFile = XML loadFile "Sexy.xml"
    
    // We can access the child nodes directly.  The "child" calls gets the song, then the title.
    println; println( ( (xmlFromFile child 0) child 1) text)

    // Get nodes with a "line" label, and loop over them printing their "number" attribute and text
    println; for (node <- xmlFromFile \\ "line") println( (node attribute "number").get + "  " + node.text )
    
    // Get nodes with a "number" attribute, and loop over them printing out out their values
    println; for (number <- xmlFromFile \\ "@number" ) println(number)
    
  }

}
