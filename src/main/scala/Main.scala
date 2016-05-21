import java.io.File

import scala.language.postfixOps

object Main extends App {

  import scala.xml.XML

  val xmlExportFile = XML.loadFile(this.args(0))
  val exportDirectory = new File(this.args(1))

  val transformNodes = xmlExportFile \ "transformations" \ "transformation"
  println(transformNodes.length)

  def snakify(name: String) = name.replaceAll("([A-Z]+)([A-Z][a-z])", "$1_$2").replaceAll("([a-z\\d])([A-Z])", "$1_$2").replace(" ", "_").toLowerCase

  transformNodes.toList.foreach { kettleTransformXML =>

    val (name, directory) = kettleTransformXML \ "info" match {
      case infoNode => (infoNode \ "name" text, infoNode \ "directory" text)
    }

    val targetDirectory = new File(exportDirectory, snakify(directory))
    val targetFile = new File(targetDirectory, snakify(name) + ".ktr")

    targetDirectory.mkdirs()
    val path = targetFile.getPath
    println(s"Saving $name as $path")
    XML.save(path, kettleTransformXML)

  }

}
