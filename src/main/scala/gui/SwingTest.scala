package gui

import scala.swing._

object SwingTest extends SimpleSwingApplication {
  
  def top = new MainFrame {
    
    title = "Hello, World!"
    contents = new Button { text = "Click Me!"}
    
  } // top
  
}