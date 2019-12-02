object Main {
  def main(arguments: Array[String]):Unit = {
    val dataset = new Dataset("ca.csv")
    println("There are:", dataset.get_length)

    val city = dataset("Oshawa")
    println(city)

    for(c <- dataset.southOf(dataset("Oshawa"))) {
      println(c)
    }
  }
}
