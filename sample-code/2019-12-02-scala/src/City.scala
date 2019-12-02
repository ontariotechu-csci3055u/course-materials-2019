class City(_name: String, _lat: String, _lng: String) {
  val name = _name
  val lat: Float = _lat.toFloat
  val lng: Float = _lng.toFloat

  override def toString:String = {
    name + " (" + this.lat + "," + this.lng + ")"
  }
}
