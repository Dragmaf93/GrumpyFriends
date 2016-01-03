package menu;

import javafx.scene.paint.Color;

public interface PageComponent {

	public static final Color STROKE_COLOR = new Color(32d/255d,207d/255d,208d/255d,1);
	
	abstract public double getHeightComponent();
	abstract public double getWidthComponent();
	abstract public String getNameComponent();
}
