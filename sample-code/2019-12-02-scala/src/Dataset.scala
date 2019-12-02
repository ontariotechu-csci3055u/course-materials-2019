import scala.io.Source

class Dataset(filename: String) {
  val cities = (for(line <- Source.fromFile(filename).getLines) yield {
    val parts = line.split(",")
    val name = parts(0)
    val lat = parts(1)
    val lng = parts(2)
    new City(name, lat, lng)
  }).toList

  def get_length: Int = cities.length

  def apply(name: String): City = {
    var i = 0
    var c:City = cities(i)
    while(c.name != name) {
      c = cities(i)
      i += 1
    }
    c
  }

  def southOf(city: City): List[City] = {
    var result = List[City]()
    for(c <- cities) {
      if(c.lat < city.lat) {
        result = c :: result
      }
    }
    result
  }
}
