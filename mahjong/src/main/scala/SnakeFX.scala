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
  val rand: Random.type = Random
  val tilesHandler = new TilesHandler(36) //34

  override def start(): Unit = {
//
//    val fileLines = Source.fromFile("mahjong/src/main/scala/Map1.txt").getLines.toList
//    val arr = fileLines.filterNot(_.isEmpty).map {
//      line => (line.toList).filter(e => e != ' ') }.toArray

    stage = new JFXApp3.PrimaryStage {
      title = "Mahjong"
      width = 700
      height = 900
      val backGround = new ImageView("file:mahjong/src/main/scala/background.png")

      backGround.setFitWidth(700)
      backGround.setFitHeight(900)
      scene = new Scene(700, 900) {
        content = List(backGround) ++
          pyramid(xOffset, yOffset) ++ pyramid(xOffset + 184, yOffset) ++ pyramid(xOffset + 210, yOffset + 32, 2, 2) ++
          pyramid(xOffset , yOffset + 434) ++ pyramid(xOffset + 184, yOffset + 434) ++ pyramid(xOffset + 26, yOffset + 32, 2, 2) ++
          pyramid(xOffset + 26, yOffset + 32, 2, 2) ++ pyramid(xOffset + 26, yOffset + 465, 2, 2) ++
          pyramid(xOffset + 210, yOffset + 465, 2, 2) ++
          List(tileImage(xOffset + 50, yOffset + 62, tilesHandler.getRandomTile), tileImage(xOffset + 235, yOffset + 62, tilesHandler.getRandomTile),
            tileImage(xOffset + 50, yOffset + 495, tilesHandler.getRandomTile), tileImage(xOffset + 235, yOffset + 495, tilesHandler.getRandomTile),
            tileImage(xOffset, yOffset + 62*4+31, tilesHandler.getRandomTile), tileImage(xOffset + 276, yOffset + 62*4+31, tilesHandler.getRandomTile)) ++
          stripe()

      }
    }
  }

  def pyramid(x: Double, y: Double, nx: Int = 3, ny: Int = 3): Seq[ImageView] =
    for(i <- 0 until nx;
        j <- 0 until ny)
    yield tileImage(x + i * 46, y + j * 62, tilesHandler.getRandomTile)


  def stripe(): Seq[ImageView] =
    for (i <- 0 until 5;
         j <- 0 until 2)
    yield tileImage(xOffset + 46 * (i+1), yOffset + 62*(4 + j), tilesHandler.getRandomTile)


  def tileImage(xr: Double, yr: Double, img_id: Int) = new ImageView {
    x = xr
    y = yr
    val img = img_id
    private var shadow = 0
    image = new Image(s"file:mahjong/src/main/scala/mahjong_tiles/tiles-${getNumber(img)}-00.png")
    onMouseClicked = () => {
      if (curr == img && currTile != this) {
        currTile.image = null
        image = null
        curr = -1
        currTile = null
      } else {
        shadow = 1 - shadow
        image = new Image(s"file:mahjong/src/main/scala/mahjong_tiles/tiles-${getNumber(img)}-${switchShadow(shadow)}.png")
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


  def square(xr: Double, yr: Double, color: Color) = new Rectangle {
    x = xr
    y = yr
    width = 48
    height = 48
    fill = color
    onMouseClicked = () => println(s"Clicked $xr $yr")
  }
}