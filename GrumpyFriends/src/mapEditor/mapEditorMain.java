package mapEditor;

import java.awt.Toolkit;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class mapEditorMain extends Application{

	private Pane panelForObject;
	private Pane panelForMap;
	private GridPane grid;
	
	int larghezza = (int) Toolkit.getDefaultToolkit().getScreenSize().getWidth();
	int altezza = (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight();

	@Override
	public void start(Stage primaryStage) throws Exception {
		
		primaryStage.setTitle("Map Editor");        

		final Text source = new Text(50, 100, "DRAG ME");
		final Text target = new Text(300, 100, "DROP HERE");
		
		grid = new GridPane();
//		grid.setAlignment(Pos.CENTER);
		grid.setHgap(10);
		grid.setVgap(10);
//		grid.setPadding(new Insets(25, 25, 25, 25));
        
        panelForObject = new Pane();
        panelForObject.setPrefSize(larghezza/2, altezza);
        panelForObject.setStyle("-fx-background-color: cyan;");
        
        panelForObject.getChildren().add(source);
        
        panelForMap = new Pane();
        panelForMap.setPrefSize(larghezza/2, altezza);
        panelForMap.setStyle("-fx-background-color: red;");
        
        grid.add(panelForObject,0,0);
        grid.add(panelForMap,1,0);

        source.setOnDragDetected(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent event) {
                /* drag was detected, start a drag-and-drop gesture*/
                /* allow any transfer mode */
                Dragboard db = source.startDragAndDrop(TransferMode.COPY_OR_MOVE);
                
                /* Put a string on a dragboard */
                ClipboardContent content = new ClipboardContent();
                content.putString(source.getText());
                db.setContent(content);
                
                event.consume();
            }
        });
        
        BorderPane root = new BorderPane();
        root.setCenter(grid);
        
        primaryStage.setScene(new Scene(root, larghezza, altezza));
        primaryStage.show();
		
	}
	
	public static void main(String[] args) {
        launch(args);
    }
	
}
