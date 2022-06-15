import javafx.scene.image
import scalafx.application.{JFXApp3, Platform}
import scalafx.event.ActionEvent
import scalafx.scene.Scene
import scalafx.scene.control.{Button, ComboBox, Label, ListView, TextArea}
import scalafx.scene.input.MouseButton.Primary
import scalafx.scene.paint.Color
import scalafx.scene.paint.Color._
import scalafx.scene.shape.{Box, Rectangle}
import scalafx.Includes._
import scalafx.beans.property.ObjectProperty
import scalafx.event.ActionEvent
import scalafx.scene.image.{Image, ImageView}
import scalafx.scene.input.MouseEvent
import scalafx.scene.layout.{BorderPane, HBox}

import scala.io.Source
import scala.util.Random

object SnakeFX extends JFXApp3 {
  var curr: Int = -1;
  private var currTile: ImageView = null;

  val xOffset: Double = 175
  val yOffset: Double = 140
  val tileW: Double = 46
  val tileH: Double = 62
  val rand: Random.type = Random
  val tilesHandler = new TilesHandler(36) //34

  override def start(): Unit = {

    stage = new JFXApp3.PrimaryStage {
      title = "Mahjong"
      width = 700
      height = 900
      val backGround = new ImageView("file:src/main/scala/background.png")

      backGround.setFitWidth(700)
      backGround.setFitHeight(900)
      scene = new Scene(700, 900) {
        content = List(backGround) ++
          pyramid(0,   0) ++
          pyramid(0 ,  7) ++
          pyramid(4,   7) ++
          pyramid(4,   0) ++
          pyramid(4.5, 0.5, 2, 2, 1) ++
          pyramid(0.5, 0.5, 2, 2, 1) ++
          pyramid(0.5, 0.5, 2, 2, 1) ++
          pyramid(0.5, 7.5, 2, 2, 1) ++
          pyramid(4.5, 7.5, 2, 2, 1) ++
          List(tileImage(1, 1, tilesHandler.getRandomTile),
            tileImage(5, 1, tilesHandler.getRandomTile),
            tileImage(1, 8, tilesHandler.getRandomTile),
            tileImage(5, 8, tilesHandler.getRandomTile),
            tileImage(0, 4.5, tilesHandler.getRandomTile),
            tileImage(6, 4.5, tilesHandler.getRandomTile)) ++
          stripe()

      }
    }
  }

  def pyramid(x: Double, y: Double, nx: Int = 3, ny: Int = 3, level:Int = 0): Seq[ImageView] =
    for(i <- 0 until nx;
        j <- 0 until ny)
    yield tileImage(x + i, y + j, tilesHandler.getRandomTile, level)


  def stripe(): Seq[ImageView] =
    for (i <- 0 until 5;
         j <- 0 until 2)
    yield tileImage(i+1, 4 + j, tilesHandler.getRandomTile)


  def tileImage(xr: Double, yr: Double, img_id: Int, level: Int = 0) = new ImageView {
    x = xr * tileW + xOffset
    y = yr * tileH + yOffset
    val x_id: Int = (2 * xr).toInt
    val y_id: Int = (2 * yr).toInt
    val lvl = level
    val img = img_id
    private var shadow = 0
    image = new Image(s"file:src/main/scala/mahjong_tiles/tiles-${getNumber(img)}-00.png")
    onMouseClicked = () => {
      if (curr == img && currTile != this && tilesHandler.checkIfTileIsFree(x_id, y_id, level)) {
        currTile.image = null
        image = null
        curr = -1
        currTile = null
        tilesHandler.deleteTile(x_id, y_id, level, img)
      } else {
        shadow = 1 - shadow
        image = new Image(s"file:src/main/scala/mahjong_tiles/tiles-${getNumber(img)}-${switchShadow(shadow)}.png")
        currTile = this
        curr = img
      }
    }
  }

  def getNumber(number: Int): String = {
    if (number > 9) s"$number" else s"0$number"
  }

  def switchShadow(shadow: Int) = {
    if (shadow == 0) "00" else "01"
  }


//  def square(xr: Double, yr: Double, color: Color) = new Rectangle {
//    x = xr
//    y = yr
//    width = 48
//    height = 48
//    fill = color
//    onMouseClicked = () => println(s"Clicked $xr $yr")
//  }
}