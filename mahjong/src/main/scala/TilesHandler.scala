import scala.collection.mutable
import scala.collection.mutable.ArrayBuffer

class TilesHandler(val numberOfTiles: Int) {
  val typesOfTiles: ArrayBuffer[Int] = getRandomTiles
  var tilesUsage: mutable.HashMap[Int, Int] = createInitialHashMap
  var currentlyUsedTiles = 0


  def checkIfTileIsFree(x: Double, y: Double): Boolean =  {
      false
  }

  def getRandomTiles: ArrayBuffer[Int] = {
    val res = ArrayBuffer[Int]()
    val r = scala.util.Random

    for(_ <- 0 until numberOfTiles + 1) {
        res.append(r.nextInt(43))
    }

    res
  }

  def createInitialHashMap: mutable.HashMap[Int, Int] = {
    val res: mutable.HashMap[Int, Int] = mutable.HashMap.empty[Int, Int]

    for(index <- 0 until numberOfTiles + 1) {
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
