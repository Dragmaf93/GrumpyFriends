package mapEditor;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.shape.CubicCurve;
import javafx.scene.shape.QuadCurve;
import javafx.stage.Stage;
// w w  w  .  ja  v  a  2  s.c  o m
public class Main extends Application {
  @Override
  public void start(Stage stage) {
    Group root = new Group();
    Scene scene = new Scene(root, 300, 150);
    stage.setScene(scene);
    stage.setTitle("");


    QuadCurve quad = new QuadCurve();
    quad.setStartX(0.0f);
    quad.setStartY(50.0f);
    quad.setEndX(50.0f);
    quad.setEndY(50.0f);
    quad.setControlX(25.0f);
    quad.setControlY(0.0f);
    
//    CubicCurve quad = new CubicCurve();
//    quad.setStartX(0.0f);
//    quad.setStartY(50.0f);
//    quad.setEndX(100.0f);
//    quad.setEndY(50.0f);
//    quad.setControlX1(25.0f);
//    quad.setControlY1(0.0f);
//    quad.setControlX1(75.0f);
//    quad.setControlY1(100.0f);
    
    root.getChildren().add(quad);

    scene.setRoot(root);
    stage.show();
  }

  public static void main(String[] args) {
    launch(args);
  }
}