package mapEditor;

import java.util.concurrent.atomic.AtomicInteger;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.Cursor;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;

class DrawingPanel extends Pane
{
	MapEditor mapEditor;
	
  public DrawingPanel( final Polygon poly, MapEditor mapEditor )
  {
//    poly.setFill( Color.web( "ANTIQUEWHITE", 0.8 ) );
//    poly.setStroke( Color.web( "ANTIQUEWHITE" ) );
//    poly.setStrokeWidth( 2 );

//    getChildren().addAll(poly);

  this.mapEditor = mapEditor;
  
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
      setDragHandler(circle);
      getChildren().add(circle);
    }
  }

  private double dragDeltaX, dragDeltaY; 

  private void setDragHandler( final Circle circle)
  {
    circle.setOnMousePressed( new EventHandler<MouseEvent>() {
      @Override 
      public void handle( MouseEvent mouseEvent ) {
        dragDeltaX = circle.getCenterX() - mouseEvent.getSceneX();
        dragDeltaY = circle.getCenterY() - mouseEvent.getSceneY();
      }
    } );

    circle.setOnMouseDragged( new EventHandler<MouseEvent>() {
      @Override
      public void handle( MouseEvent mouseEvent ) {
    	  for (int i = 0; i < mapEditor.getDragged().getPointsVertex().size(); i++) {
    		  if (circle.getCenterX() == mapEditor.getDragged().getPointsVertex().get(i).getX() && 
    				  circle.getCenterY() == mapEditor.getDragged().getPointsVertex().get(i).getY())
    		  {
    			  circle.setCenterX( mouseEvent.getSceneX() + dragDeltaX );
    			  circle.setCenterY( mouseEvent.getSceneY() + dragDeltaY );
    			  mapEditor.getDragged().modifyPositionWithVertex(mapEditor.getDragged().getPointsVertex().get(i), 
    					  new Point2D(circle.getCenterX(), circle.getCenterY()));
    		  }
			
		}
    	
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
