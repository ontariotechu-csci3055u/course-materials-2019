/* fileio.CsvIO **extra**
 * Author: Michael Valdron
 * Date: Dec 2019
 */

package fileio

import scala.io.{
  Source,
  BufferedSource
}


object CsvIO {
  private val HEADERS: Array[String] = Array("title", "code", "dow", "sh",
                                             "sm", "eh", "em", "room")

  def getValuesInOrder(row: Map[String, String]): Array[String] = HEADERS.map(col => row(col))

  def reader(fpath: String): BufferedSource = Source.fromFile(fpath)

  def parseCsv(csvFile: String, sep: String): List[Map[String, String]] = reader(csvFile)
    .getLines
    .toList
    .map(HEADERS zip _.trim.split(sep))
    .map(_.toMap)
}
