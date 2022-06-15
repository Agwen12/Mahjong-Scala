import scala.collection.mutable

class TilesHandler(val numberOfTiles: Int) {
  val typesOfTiles: Array[Int] = getRandomTypes
  var tilesUsage: mutable.HashMap[Int, Int] = createInitialHashMap
  var currentlyUsedTiles = 0


  def checkIfTileIsFree(x: Double, y: Double): Boolean =  {
      false
  }

  def getRandomTypes: Array[Int] = {
    var tempSet = Set[Int]()
    val r = scala.util.Random

    while(tempSet.size < numberOfTiles) {
      tempSet += r.nextInt(42)
    }

    val res = tempSet.toArray

    res
  }

  def createInitialHashMap: mutable.HashMap[Int, Int] = {
    val res: mutable.HashMap[Int, Int] = mutable.HashMap.empty[Int, Int]

    for(index <- 0 until numberOfTiles) {
      res += (typesOfTiles(index) -> 0)
    }

    res
  }

  def getRandomTile: Int = {
    if (currentlyUsedTiles < 2 * numberOfTiles) {
      val r = scala.util.Random
      var flag = true
      var res = -1
      while(flag) {
        val key = typesOfTiles(r.nextInt(numberOfTiles))
        if (tilesUsage(key) < 2) {
          val temp = tilesUsage(key) + 1
          tilesUsage -= key
          tilesUsage += (key -> temp)
          currentlyUsedTiles += 1
          res = key
          flag = false
        }
      }
      res
    } else {
      -1
    }
  }
}
