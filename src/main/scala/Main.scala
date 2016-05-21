import java.io.File

import scala.language.postfixOps

object Main extends App {

  import scala.xml.XML

  val xmlExportFile = XML.loadFile(this.args(0))
  val exportDirectory = this.args(1)

  val transformNodes = xmlExportFile \ "transformations" \ "transformation"
  println(transformNodes.length)

  def snakify(name: String) = name.replaceAll("([A-Z]+)([A-Z][a-z])", "$1_$2").replaceAll("([a-z\\d])([A-Z])", "$1_$2").replace(" ", "_").toLowerCase

  transformNodes.toList.foreach { kettleTransformXML =>

    val (name, directory) = kettleTransformXML \ "info" match {
      case infoNode => (infoNode \ "name" text, infoNode \ "directory" text)
    }

    val directoryName = directory.replace("/", "\\")
    val saveName = s"$exportDirectory${snakify(directoryName + "\\" + name)}.ktr"
    val f = new File(saveName.substring(0, saveName.lastIndexOf("\\")))
    f.mkdirs()
    println(s"Saving $name as $saveName")
    XML.save(saveName, kettleTransformXML)
  }

}
