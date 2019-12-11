/* part2
 * Author: Michael Valdron
 * Date: Dec 2019
 */

import scala.collection.mutable.ListBuffer
import fileio.CsvIO

object part2 {

  def formatCourseCode(cc: String): String = {
    val beg = cc.toUpperCase.substring(0, 4)
    val end = cc.toUpperCase.substring(4, cc.length)
    beg + " " + end
  }

  def filterByCourseCode(data: Map[String, String], courseCodes: Array[String]): Boolean =
    courseCodes
      .map(formatCourseCode)
      .contains(data("code"))

  def sortByDOW(cs: List[Map[String, String]]): List[Map[String, String]] = {
    val key: String = "dow"
    val dowOrder: Array[String] = Array("M", "T", "W", "R", "F")
    var newList: ListBuffer[Map[String, String]] = ListBuffer()
    var idsToAdd: ListBuffer[Int] = List
      .range(0, cs.length)
      .to(ListBuffer)

    for (dow <- dowOrder) {
      for (idx <- idsToAdd) {
        if (cs(idx)(key) == dow) {
          newList += cs(idx)
          idsToAdd.remove(idsToAdd.indexOf(idx))
        }
      }
    }

    return newList.toList
  }

  def loadCourses(csvfile: String, selectedCourses: Array[String]): List[Map[String, String]] =
    sortByDOW(CsvIO
                .parseCsv(csvfile, ",")
                .filter(selectedCourses.isEmpty || filterByCourseCode(_, selectedCourses)))

  def main(args: Array[String]): Unit = {
    val csvfile = args(0)
    val selectedCourses = args.slice(1, args.length)
    loadCourses(csvfile, selectedCourses)
      .map(CsvIO
             .getValuesInOrder(_)
             .mkString(","))
      .foreach(println)
  }
}
