// requires Java Mail API (mail.jar), which must be in classpath

import javax.mail.{Session, Message, Transport}
import javax.mail.internet.{InternetAddress, MimeMessage}

object EMail extends App {
  
  val properties = new java.util.Properties
  properties.put("mail.debug", "true");          
  //  properties.put("mail.smtp.host", "localhost")

  // Set up the mail object (Verizon)
  properties.put("mail.smtp.host", "smtp.verizon.net")	
  properties.put("mail.smtp.port", "465");
  properties.put("mail.smtp.ssl.enable", "true");      
  
  // Set up the mail object (iCloud)
//  properties.put("mail.smtp.host", "smtp.mail.me.com")
//  properties.put("mail.smtp.starttls.enable", "true");			

  val session = Session.getDefaultInstance(properties)
	
  // Define a message
  val message = new MimeMessage(session)	
  message.setRecipients(Message.RecipientType.TO, "mark.frymire@dac.us")	
  message.setSubject("A compliment to you...")	
  message.setText("You suck!")
		
  // Send it (Verizon)
  message.setFrom( new InternetAddress("frymiretest@verizon.net") )
  Transport.send(message, "frymiretest", "dactest5" )
  
  // Send it (iCloud)	
//  message.setFrom( new InternetAddress("mark.frymire@icloud.com") )	
//  Transport.send(message, "mark.frymire@icloud.com", { print("Password: "); readLine } )
  
} // EMail
