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

class DrawingPanelForCurve extends Pane
{
	MapEditor mapEditor;
	
  public DrawingPanelForCurve( final Curve curve, MapEditor mapEditor )
  {
//    poly.setFill( Color.web( "ANTIQUEWHITE", 0.8 ) );
//    poly.setStroke( Color.web( "ANTIQUEWHITE" ) );
//    poly.setStrokeWidth( 2 );

//    getChildren().addAll(poly);

	this.mapEditor = mapEditor;
  
	for ( int i = 0; i < curve.getPoints().size(); i += 2 ) {
		
//		double y1 = 2*start.getY() - getStartY()/2 -getEndY()/2;
//		double pointC = Math.abs((-curve.getStartY()/2 -curve.getEndY()/2 - curve.getRealPoints().get(2).getY())/ 2);
//		System.out.println(pointC);
//		Circle circle;
//		if (i < curve.getPoints().size()-2)
		Circle circle = new Circle( curve.getPoints().get( i ), curve.getPoints().get( i + 1 ), 5 );
//		else
//			circle = new Circle( curve.getPoints().get( i ), pointC, 5 );
			
	  circle.setFill( Color.web( "PERU", 0.8 ) );
      circle.setStroke( Color.PERU );
      circle.setStrokeWidth( 2 );

      final AtomicInteger polyCoordinateIndex = new AtomicInteger( i );
      circle.centerXProperty().addListener( new ChangeListener<Number>() {
        @Override
        public void changed( ObservableValue<? extends Number> observable, Number oldValue, Number newValue ) {
          curve.getPoints().set( polyCoordinateIndex.get(), newValue.doubleValue() );
        }
      } );
      
      circle.centerYProperty().addListener( new ChangeListener<Number>() {
        @Override
        public void changed( ObservableValue<? extends Number> observable, Number oldValue, Number newValue ) {
          curve.getPoints().set( polyCoordinateIndex.get() + 1, (Double) newValue );
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
    	  for (int i = 0; i < mapEditor.getDraggedCurve().getRealPoints().size(); i++) {
    		  if (circle.getCenterX() == mapEditor.getDraggedCurve().getRealPoints().get(i).getX() && 
    				  circle.getCenterY() == mapEditor.getDraggedCurve().getRealPoints().get(i).getY())
    		  {
    			  circle.setCenterX( mouseEvent.getSceneX() + dragDeltaX );
    			  circle.setCenterY( mouseEvent.getSceneY() + dragDeltaY );
    			  mapEditor.getDraggedCurve().modifyPositionWithVertex(mapEditor.getDraggedCurve().getRealPoints().get(i), 
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
