import java.io.File

import scala.language.postfixOps

object Main extends App {

  import scala.xml.XML

  val xmlExportFile = XML.loadFile(this.args(0))
  val exportDirectory = this.args(1)

  val transformNodes = xmlExportFile \ "transformations" \ "transformation"
  println(transformNodes.length)

  val transformNames = (transformNodes \ "info" \ "name").toList.map(x => x.text)
  val transformDirectories = (transformNodes \ "info" \ "directory").toList.map(x => x.text)

  def snakify(name : String) = name.replaceAll("([A-Z]+)([A-Z][a-z])", "$1_$2").replaceAll("([a-z\\d])([A-Z])", "$1_$2").toLowerCase

  transformNames.zip(transformNodes.toList).zip(transformDirectories).foreach { case ((name, kettleTransformXML), directory) =>
    val directoryName = directory.replace("/", "\\")
    val saveName = exportDirectory + snakify(directoryName + "\\" + name).replace(" ", "_") + ".ktr"
    val f = new File(saveName.substring(0, saveName.lastIndexOf("\\")))
    f.mkdirs()
    println(s"Saving $name as $saveName")
    XML.save(saveName, kettleTransformXML)
  }

}
