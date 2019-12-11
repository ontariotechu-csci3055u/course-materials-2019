/* part1
 * Author: Michael Valdron
 * Date: Dec 2019
 */

import fileio.CsvIO

object part1 {
  private var datasetFile: String = ""

  def getItems(key: String): List[String] = CsvIO.parseCsv(datasetFile, ",")
    .map(_(key))

  def numberOfCourseCodes(): Int = getItems("code").distinct.length

  def totalRooms(): Int = getItems("room").distinct.length

  def totalCSCourses(): Int = getItems("code")
    .filter(_ contains "CSCI")
    .length

  def main(args: Array[String]): Unit = {
    datasetFile = args(0)
    println(numberOfCourseCodes)
    println(totalRooms)
    println(totalCSCourses)
  }
}
