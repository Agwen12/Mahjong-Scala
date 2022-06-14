
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
import scalafx.scene.input.MouseEvent
import scalafx.scene.layout.{BorderPane, HBox}


object SnakeFX extends JFXApp3 {
  override def start(): Unit = {
    stage = new JFXApp3.PrimaryStage {
      title = "Mahjong"
      width = 900
      height = 700
      scene = new Scene(900, 700) {


        content = pyramid(0, 0) ++ pyramid(200, 0) ++ pyramid(0, 300) ++ pyramid(200, 300) ++ longStrip(50, 175) ++
           List(square(0, 200, Green), square(300, 200, Green))


      }
    }
  }

  def pyramid(x: Double, y: Double) =
    for(i <- 0 until 3;
        j <- 0 until 3)
    yield square(x + i * 50, y + j * 50, Red)

  def longStrip(x: Double, y: Double) =
    for (i <- 0 until 5;
         j <- 0 until 2)
      yield square(x + i * 50, y + j * 50, Green)


  def square(xr: Double, yr: Double, color: Color) = new Rectangle {
    x = xr
    y = yr
    width = 48
    height = 48
    fill = color
    onMouseClicked = () => println(s"Clicked $xr $yr")
  }
}
