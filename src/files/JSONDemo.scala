
// Note that Scala's built-in JSON parser is reported to be relatively slow.
package files

import util.parsing.json.{JSONObject, JSONArray, JSON}

object JSONDemo extends App {
  
  val textLines = io.Source fromFile "resources/JSONTest.txt" getLines
  val jObject3 = JSON.parseRaw(textLines.toVector(0)).get.asInstanceOf[JSONObject]
  println(jObject3.obj("Romo"))
  
  // Define some JSON strings.  It's easier to use triple quotes to define raw strings, so that you don't
  // have to escape internal quotes. Scala's JSON parser can't handle single quotes within a JSON string.
  //val jsonObjectString = "{'hello' : 'world', 'Romo' : 'sucks'}" // error
  val jsonObjectString = """{"hello" : "world", "Romo" : "sucks"}"""
  val jsonArrayString = """[1, {"hello" : "world"}, 3]"""
  
  // Use parseRaw to create JSONObjects and JSONArrays from JSON strings.  The method returns a JSONType, 
  // so you have to know what type of JSON string you have and cast it appropriately.
  val jObject1 = JSON.parseRaw(jsonObjectString).get.asInstanceOf[JSONObject]
  val jArray1 = JSON.parseRaw(jsonArrayString).get.asInstanceOf[JSONArray] 
  println(jObject1)
  println(jArray1)
    
  // Note that you can't access the elements directly as jObject("hello") or jArray(1).  Instead, you have to 
  // reference the obj member (a Map[String, Any]) in JSONObject or the list member (a List[Any]) in JSONArray  
  //println( jObject1("hello") ) // compile-time error
  println( jObject1.obj("hello") )
  // println( jObject1.obj("goodbye") ) // key not found error
  println( jObject1.obj.get("goodbye") ) // None
  println( jObject1.obj.get("Romo") ) // Some(sucks)
  //println( jArray1(1) ) // compile-time error
  println( jArray1.list(1) )
    
  // You can also create a JSONObject from a Scala Map and a JSONArray from a Scala List, which is nicer,
  // because there's no need to cast.  Note that you can nest a JSONObject or JSONArray naturally. 
  val jObject2 = JSONObject( Map("hello" -> "world", "aList" -> (1, 2, 3), "anArray" -> jArray1 ) )
  val jArray2 = JSONArray( List(1, jObject1, 3 ) )
  println(jObject2)
  println(jArray2)
    
  // Use parseFull to parse JSON strings directly to appropriate Scala objects, instead of JSONType objects.
  def jsonToMap(json: String) = JSON.parseFull(json).asInstanceOf[ Option[Map[String, Any]] ].get
  def jsonToList(json: String) = JSON.parseFull(json).asInstanceOf[ Option[List[Any]] ].get

  // Get Scala objects directly from JSON strings.
  println( jsonToMap(jsonObjectString) )
  println( jsonToList(jsonArrayString) )
    
} 
