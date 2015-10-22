package mapEditor;

import java.util.concurrent.atomic.AtomicInteger;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;
import javafx.stage.Stage;

class DrawingPane extends Pane
{
  public DrawingPane( Image image, final Polygon poly )
  {
    poly.setFill( Color.web( "ANTIQUEWHITE", 0.8 ) );
    poly.setStroke( Color.web( "ANTIQUEWHITE" ) );
    poly.setStrokeWidth( 2 );

    getChildren().addAll( new ImageView( image ), poly );

    for ( int i = 0; i < poly.getPoints().size(); i += 2 ) {
      Circle circle = new Circle( poly.getPoints().get( i ), poly.getPoints().get( i + 1 ), 5 );
      circle.setFill( Color.web( "PERU", 0.8 ) );
      circle.setStroke( Color.PERU );
      circle.setStrokeWidth( 2 );

      final AtomicInteger polyCoordinateIndex = new AtomicInteger( i );
      circle.centerXProperty().addListener( new ChangeListener<Number>() {
        @Override
        public void changed( ObservableValue<? extends Number> observable, Number oldValue, Number newValue ) {
          poly.getPoints().set( polyCoordinateIndex.get(), newValue.doubleValue() );
        }
      } );
      circle.centerYProperty().addListener( new ChangeListener<Number>() {
        @Override
        public void changed( ObservableValue<? extends Number> observable, Number oldValue, Number newValue ) {
          poly.getPoints().set( polyCoordinateIndex.get() + 1, (Double) newValue );
        }
      } );
      setDragHandler( circle );
      getChildren().add( circle );
    }
  }

  private double dragDeltaX, dragDeltaY; 

  private void setDragHandler( final Circle circle )
  {
    circle.setOnMousePressed( new EventHandler<MouseEvent>() {
      @Override public void handle( MouseEvent mouseEvent ) {
        dragDeltaX = circle.getCenterX() - mouseEvent.getSceneX();
        dragDeltaY = circle.getCenterY() - mouseEvent.getSceneY();
      }
    } );

    circle.setOnMouseDragged( new EventHandler<MouseEvent>() {
      @Override public void handle( MouseEvent mouseEvent ) {
        circle.setCenterX( mouseEvent.getSceneX() + dragDeltaX );
        circle.setCenterY( mouseEvent.getSceneY() + dragDeltaY );
        circle.setCursor( Cursor.MOVE );
      }
    } );

    circle.setOnMouseEntered( new EventHandler<MouseEvent>() {
      @Override public void handle( MouseEvent mouseEvent ) {
        circle.setCursor( Cursor.HAND );
      }
    } );

    circle.setOnMouseReleased( new EventHandler<MouseEvent>() {
      @Override public void handle( MouseEvent mouseEvent ) {
        circle.setCursor( Cursor.HAND );
      }
    } );
  }
}

public class JavafxDemo extends Application
{
  @Override
  public void start( Stage stage )
  {
    Image image = new Image( "http://tours-tv.com/uploads/maps/map-Medizinische-Hochschule-Hannover-karta.jpg" );
    Polygon poly = new Polygon( 10, 10, 100, 10, 200, 100, 50, 200 );

    stage.setScene( new Scene( new DrawingPane( image, poly ), 450, 300 ) );
    stage.show();
  }
  
  public static void main(String[] args) {
	launch(args);
}
  
}