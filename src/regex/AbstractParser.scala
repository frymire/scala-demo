

package regex

import java.io.{File, PrintWriter, FileWriter}
import collection.mutable.ArrayBuffer
import org.apache.tika.Tika


object AbstractParser { 

  
  def getAllSubDirs(dir: File): ArrayBuffer[File] = {
        
    // Add each local directory and the results of a recursive call to a list, and return it
    val list = new ArrayBuffer[File]
    for (d <- dir.listFiles if d.isDirectory) yield list += d ++= getAllSubDirs(d)
    list
    
  }

    
  def extractAuthorLastNames(line: String): Array[String] = {

    // Set up a regex to search for any text that indicates a given string isn't describing an author,
    // including words that seem to describe an institution, state abbreviations, street names, and a few
    // miscellaneous other terms.
    val nonAuthorRegex = ( "Universit|College|Research|Institute|Dept|Department|School|Center|Group|Science|" +
    					   "Proceedings|UC|China|Germany|Michigan|United|Chicago|Place|Street|[0-9]{2}|, ?[A-Z][A-Z]" ).r
        					   
    // If this doesn't seem to be an author line, return an empty array.  Otherwise print the line to the console.
    if ( (line.length < 2) || (nonAuthorRegex.findFirstIn(line) != None) ) return Array()
//    else println(line)

    // TODO: Extract full names on one line (i.e. "Mark Edward Frymire")
    
    // Set up a regular expression to extract names.
    // TODO: It's a hack to put "´" in here explicitly, but it wouldn't work otherwise.
    val nameExtractor = """([\p{Lu}][\p{L}´\-]+) (\p{Lu}\.)? ?([\p{Lu}][\p{L}´\-]+)( III)?""".r
    
    // Find all of the matches in the input line, and return an array of the last token and suffix of each
    val names = nameExtractor findAllIn line
//    names foreach println
// TODO: I think there was a phantom copy-paste on this line.  Double check.
//    ((for (nameExtractor(first, middle, last, suffix) <- names_ matchnameExtractor(first, middle, last, suffix) <- names yield last + {if (suffix != null) suffix else ""}) toArray        

    (for (nameExtractor(first, middle, last, suffix) <- names) yield last + {if (suffix != null) suffix else ""}) toArray        
          
  }


  def main(args: Array[String]) {

	  // Specify the year that the documents are assumed to be published in 
	  val year = 2012
	  
	  // If the papers are from SIGGRAPH, set this to true
	  val siggraph = false

	  // Set up a Tika instance to parse PDFs (or anything else it can handle, for that matter)
	  val tika = new Tika

	  // Look recursively through the directory provided on the command line to find subfolders called "Uncategorized"
	  // NOTE-TO-SELF: args(1) is set as the "..\Papers\Abstract Extractor (Small)" folder for debug purposes.
	  for (folder <- getAllSubDirs( new File(args(0)) ) if folder.getName == "Uncategorized") yield {

		  // Print the folder name to the console and set up an output file in the parent directory
		  println("\n" + folder)
		  val outFile = new PrintWriter(new FileWriter(folder.getParent + "\\Abstracts.txt") )

		  // Loop over all of the files in the input folder to parse them
		  for (file <- folder.listFiles) yield {		    

			  // Print the filename to the console, parse it to a string, and get an iterator to its lines
			  println(file.getName)
			  val lines = tika.parseToString( new File(folder + "\\" + file.getName) ).lines
	
			  // Set up a variable to store the current line being read in, and read up the the first non-empty line
			  var line = lines.next
			  if (siggraph) { while ( !(line contains "DOI:") && lines.hasNext) line = lines.next; line=lines.next }
			  while ( (line == "") && lines.hasNext) line = lines.next
	
			  // Write out rows of text -- presumably the article title -- to a buffer until we get to an empty line
			  val title = new ArrayBuffer[String]
			  while (line != "") { if (line.trim != "") title += line.trim; line = lines.next }
	
			  // Read lines until we reach the beginning of the abstract, extracting authors along the way
			  val authors = new ArrayBuffer[String]
			  while ( !(line.toLowerCase contains "abstract") && lines.hasNext) {
			    
			    // Extract authors from the line, and add them to the authors buffer
				authors ++= extractAuthorLastNames(line) filter {_ != ""}			
				
				// Go to the next line
				line = lines.next
				
			  }
			  
			  // This line starts with something like "Abstract" or "Abstract - ", so we trim it.  Then,
			  // if it's empty, keep moving up until we have a non-empty line.
			  line = (line drop 9).trim
			  while (line == "") line = lines.next
			  			  
			  // Print out the authors, date, and title to the file
			  authors.size match {
			    
			    case 0 => outFile print "AUTHOR"
			    case 1 => outFile print authors(0)
			    case 2 => outFile print authors.mkString(" & ")
			    case 3 => outFile print (authors(0) + ", " + authors(1) + ", & " + authors(2))
			    case size if (size > 3) => outFile print (authors.head + ", et. al.")

			  }
			  outFile print (" (" + year + ").  \"" + title.mkString(" ") + ".\"  ")
			  
			  // For as long as we're still in the abstract section...
			  while (lines.hasNext && !(line.toLowerCase contains "introduction")  && 
					                  !(line contains "Categories and Subject Descriptors") ) {
			    
			    // Print the line to the file, checking/correcting for hyphens
			    if (line endsWith "-")
			      outFile print (line dropRight 1)
			    else
			      outFile print (line.trim + " ")
			    
			    // Go to the next line
			    line = lines.next
			    
			  } // while
			  
			  // Put an extra line between each abstract in the output file
			  outFile println "\n"

		  } // for file

		  // Close the output file
		  outFile.close

	  } // for directory

  } // main

} // AbstractParser