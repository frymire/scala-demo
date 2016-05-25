

package files

import java.io.File
import collection.mutable.ArrayBuffer

object DirectoryListing extends App {
    
  def getAllSubDirs(dir: File): ArrayBuffer[File] = {
        
    // Add each local directory and the results of a recursive call to a list, and return it
    val list = new ArrayBuffer[File]    
    for (d <- dir.listFiles if d.isDirectory) { (list += d) ++= getAllSubDirs(d) }
    list
    
  }

  getAllSubDirs( new File("D:/OneDrive/Mars") ) foreach println

}