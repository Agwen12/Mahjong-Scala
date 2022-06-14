
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


object SnakeFX extends JFXApp3 {
  override def start(): Unit = {
    stage = new JFXApp3.PrimaryStage {
      title = "Mahjong"
      width = 900
      height = 700
      scene = new Scene(900, 700) {


        content = pyramid(0, 0) ++ pyramid(200, 0) ++ pyramid(0, 300) ++ pyramid(200, 300)



      }
    }
  }

  def pyramid(x: Double, y: Double): Seq[ImageView] =
    for(i <- 0 until 3;
        j <- 0 until 3)
//    yield square(x + i * 50, y + j * 50, Red)
    yield tileImage(x + i * 50, y + j * 60, 1)

  private def longStrip(x: Double, y: Double): Seq[Rectangle] =
    for (i <- 0 until 5;
         j <- 0 until 2)
      yield square(x + i * 50, y + j * 60, Green)


  def tileImage(xr: Double, yr: Double, img: Int) = new ImageView {
    x = xr
    y = yr
    private val img: Int = img
    private var shadow = 0
    var count = img
    image = new Image(s"file:src/main/scala/mahjong_tiles/tiles-${getNumber(img)}-${switchShadow(shadow)}.png")
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
