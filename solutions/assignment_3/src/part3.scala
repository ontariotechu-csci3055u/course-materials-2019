/* part3
 * Author: Michael Valdron
 * Date: Dec 2019
 */

import fileio.CsvIO

object part3 {
  private var csvfile: String = ""
  private var date: Map[String, String] = Map()

  // Eval for start if true, else eval for end
  def timeOutRange(hour: Int, min: Int, startEnd: Boolean): Boolean = {
    val startHour: Int = date("st").split(":")(0).toInt
    val startMin: Int = date("st").split(":")(1).toInt
    val endHour: Int = date("et").split(":")(0).toInt
    val endMin: Int = date("et").split(":")(1).toInt
    val startMinOfDay: Int = (startHour * 60) + startMin
    val endMinOfDay: Int = (endHour * 60) + endMin
    val minOfDay: Int = (hour * 60) + min

    if (startEnd)
      minOfDay < startMinOfDay || minOfDay >= endMinOfDay
    else
      minOfDay <= startMinOfDay || minOfDay > endMinOfDay
  }

  def filterTimes(record: Map[String, String]): Boolean = {
    val differentDay: Boolean = date("dow") != record("dow")
    val startOutRange: Boolean = timeOutRange(record("sh").toInt, record("sm").toInt, true)
    val endOutRange: Boolean = timeOutRange(record("eh").toInt, record("em").toInt, false)
    differentDay || (startOutRange && endOutRange)
  }

  def loadRooms(): List[String] = CsvIO
    .parseCsv(csvfile, ",")
    .filter(_("room") contains "University Building")
    .groupBy(_("room"))
    .filter(_._2.forall(filterTimes))
    .keys
    .toList
    .sorted

  def main(args: Array[String]): Unit = {
    csvfile = args(0)
    date = Array("dow", "st", "et").zip(args.slice(1, args.length)).toMap
    loadRooms.foreach(println)
  }
}
