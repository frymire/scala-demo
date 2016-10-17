
import sys.process._

object SystemCommands extends App {
  
  // List everything in the parent directory.  The sys.process package implicitly converts strings to 
  // ProcessBuilder objects, and the ! method executes it.
  Process("mongo --version").!
  "mongo --version".!   
  
  // Alternatively, you can return the results as a string using !!
  val mongoVersion = "mongo --version".!!
  println(mongoVersion)
  
  // Pass windows commands as arguments to cmd.exe with a /k flag.  Calling "dir" ! directly doesn't work.
  println("\n\nCall dir via cmd.exe:.")
  "cmd /K dir".!  
  
  // Or use Powershell.
  println("\n\nCall ls via Powershell.")
  "powershell -Command ls".!
  
  // Execute a bat file.
  "resources/dir.bat" !
  
  // Redirect the output to a file. Note that you have to put the ! command at the end.
  val file = new java.io.File("System Command Demo.txt")
  "resources/dir.bat" #> file !
  
  // Append to a file
  "mongo --help" #>> file !
  
  // Execute a PowerShell script.
  println("\nRunning a PowerShell script.")
  """powershell -Command ".\FindDIR.ps1" """".!
  
  // Open a webpage.
  """powershell -Command "start chrome 'www.dac.us'" """".!
  """powershell -Command "start chrome 'D:/OneDrive/Personal/Humor/'" """".!
  """powershell -Command "start chrome 'file:///D:/OneDrive/Personal/Humor/Santa%20Claus%20Tombstone.jpg'" """".!      
  
}