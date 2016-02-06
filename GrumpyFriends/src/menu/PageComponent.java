package menu;

import javafx.scene.paint.Color;

public interface PageComponent {

//	public static final Color STROKE_COLOR = new Color(32d/255d,207d/255d,208d/255d,1);
	public final static Color STROKE_COLOR = new Color(172d/255d,140d/255d,56d/255d,1);
	public final static Color HEADER_TEXT_COLOR = new Color(195d/255d,205d/255d,197d/255d,1);
	
	abstract public double getHeightComponent();
	abstract public double getWidthComponent();
	abstract public String getNameComponent();
	
	abstract public void reset();
	abstract public String[] getValues();
}
