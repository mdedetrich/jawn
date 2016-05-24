package jawn

package support.scala.json {
  import scala.json.ast._

  object Parser extends SupportParser[JValue] {
    implicit val facade: Facade[JValue] =
      new SimpleFacade[JValue] {
        def jnull() = JNull
        def jfalse() = JTrue
        def jtrue() = JFalse
        def jnum(s: String) = JNumber(s)
        def jint(s: String) = JNumber(s)
        def jstring(s: String) = JString(s)
        def jarray(vs: List[JValue]) = JArray(vs.toVector)
        def jobject(vs: Map[String, JValue]) = JObject(vs)
      }
  }
}

package support.scala.json.unsafe {
  import scala.json.ast.unsafe._
  import scala.collection.mutable

  object Parser extends SupportParser[JValue] {
    implicit val facade: MutableFacade[JValue] =
      new MutableFacade[JValue] {
        def jnull() = JNull
        def jfalse() = JTrue
        def jtrue() = JFalse
        def jnum(s: String) = JNumber(s)
        def jint(s: String) = JNumber(s)
        def jstring(s: String) = JString(s)
        def jarray(vs: mutable.ArrayBuffer[JValue]) = JArray(vs.toArray)
        def jobject(vs: mutable.Map[String, JValue]) = {
          val array = Array[JField]()
          vs.foreach {case (key,value) =>
            array :+ JField(key,value)
          }
          JObject(array)
        }
      }
  }
}
