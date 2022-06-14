
import scalafx.application.{JFXApp3, Platform}
import scalafx.event.ActionEvent
import scalafx.scene.Scene
import scalafx.scene.control.{Button, ComboBox, Label, ListView, TextArea}
import scalafx.scene.input.MouseButton.Primary
import scalafx.scene.paint.Color
import scalafx.scene.paint.Color._
import scalafx.scene.shape.{Box, Rectangle}
import scalafx.Includes._
import scalafx.event.ActionEvent
import scalafx.scene.image.{Image, ImageView}
import scalafx.scene.input.MouseEvent
import scalafx.scene.layout.{BorderPane, HBox}

import scala.io.Source


object SnakeFX extends JFXApp3 {
  override def start(): Unit = {

    val fileLines = Source.fromFile("src/main/scala/Map1.txt").getLines.toList
    val arr = fileLines.filterNot(_.isEmpty).map {
      line => (line.toList).filter(e => e != ' ') }.toArray
    println(arr.mkString(",\n"))
    println(arr(0))

    println(arr(0))
    stage = new JFXApp3.PrimaryStage {
      title = "Mahjong"
      width = 900
      height = 700
      scene = new Scene(900, 700) {
        content = pyramid(0, 0) ++ pyramid(200, 0) ++ pyramid(0, 300) ++ pyramid(200, 300) ++ pyramid(26, 32, 2, 2)
      }
    }
  }

  def pyramid(x: Double, y: Double, nx: Int =3, ny: Int=3): Seq[ImageView] =
    for(i <- 0 until nx;
        j <- 0 until ny)
    yield tileImage(x + i * 46, y + j * 62, 1)


  def tileImage(xr: Double, yr: Double, img: Int) = new ImageView {
    x = xr
    y = yr
    private val img: Int = img
    private var shadow = 0
    var count = img
    image = new Image(s"file:src/main/scala/mahjong_tiles/tiles-${getNumber(img)}-00.png")
    onMouseClicked = () => {
      shadow = 1 - shadow
      image = new Image(s"file:src/main/scala/mahjong_tiles/tiles-${getNumber(img)}-${switchShadow(shadow)}.png")
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
