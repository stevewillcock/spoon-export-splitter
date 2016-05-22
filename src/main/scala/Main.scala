import java.io.File

import scala.language.postfixOps
import scala.xml.{NodeSeq, XML}

object Main extends App {

  def snakify(name: String) = name.replaceAll("([A-Z]+)([A-Z][a-z])", "$1_$2").replaceAll("([a-z\\d])([A-Z])", "$1_$2").replace(" ", "_").toLowerCase

  val xmlExportFile = XML.loadFile(this.args(0))

  def exportNodes(nodes: NodeSeq, fileExtension: String, nameRootFinder: (NodeSeq) => NodeSeq = n => n): Unit = {

    println(s"Exporting ${nodes.length} files as $fileExtension")

    nodes.toList.foreach { kettleXMlNode =>

      val (name, directory) = nameRootFinder(kettleXMlNode) match {
        case n => (n \ "name" text, n \ "directory" text)
      }

      val targetDirectory = new File(exportDirectory, snakify(directory))
      val targetFile = new File(targetDirectory, s"${snakify(name)}.$fileExtension")

      targetDirectory.mkdirs()
      val path = targetFile.getPath
      println(s"Saving $name as $path")
      XML.save(path, kettleXMlNode)

    }

  }

  val exportDirectory = new File(this.args(1))

  exportNodes(xmlExportFile \ "transformations" \ "transformation", "ktr", n => n \ "info")
  exportNodes(xmlExportFile \ "jobs" \ "job", "kjb")

}
