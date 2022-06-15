import scala.Array.ofDim
import scala.collection.mutable
import scala.io.Source

class TilesHandler(val numberOfTiles: Int) {
  val typesOfTiles: Array[Int] = getRandomTypes
  var tilesUsage: mutable.HashMap[Int, Int] = createInitialHashMap
  var currentlyUsedTiles = 0
  var arrayOfTiles: Array[Array[Array[Int]]] = createArrayOfTiles

  //function takes left upper corner of tile and its layer
  def checkIfTileIsFree(x: Int, y: Int, z: Int): Boolean =  {
    var left = true
    var right = true

    if (z < 2) {
      for (xd <- x until x + 2) {
        for (yd <- y until y + 2) {
          if (arrayOfTiles(xd)(yd)(z + 1) > 0) {
            return false
          }
        }
      }
    }

    if (y != 0) {
      for (xd <- x until x + 2) {
        if (arrayOfTiles(xd)(y - 1)(z) > 0) {
          left = false
        }
      }
    }

    if (y != 12) {
      for (xd <- x until x + 2) {
        if (arrayOfTiles(xd)(y + 2)(z) > 0) {
          right = false
        }
      }
    }

    val res = left || right

    res
  }

  //function takes left upper corner of tile, its layer and type
  def deleteTile(x: Int, y: Int, z: Int, typeOfFile: Int): Unit = {
    currentlyUsedTiles -= 1

    val temp = tilesUsage(typeOfFile) - 1
    tilesUsage -= typeOfFile
    tilesUsage += (typeOfFile -> temp)

    for (xd <- x until x + 2) {
      for (yd <- y until y + 2) {
        arrayOfTiles(xd)(yd)(z) = 0
      }
    }
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

  def createArrayOfTiles: Array[Array[Array[Int]]] = {
    var array = ofDim[Int](20,14, 3)
    val fileLines = Source.fromFile("src/main/scala/Map1.txt").getLines.toList
    val arr = fileLines.filterNot(_.isEmpty).map {
      line => (line.toList).filter(e => e != ' ') }.toArray
    println(arr.mkString("Array(", ", ", ")"))

    for (x <- 0 until 20) {
      val line = arr(x)

      for (y <- 0 until 14) {
        val dimension = line(y).toInt - 48

        for(z <- 0 until 3) {
          if (dimension > z) {
            array(x)(y)(z) = 1
          } else {
            array(x)(y)(z) = 0
          }
        }
      }
    }

    array
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
