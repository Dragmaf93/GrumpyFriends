package menu.networkMenu;

import menu.AbstractPageComponent;

public class MatchList extends AbstractPageComponent {

	public MatchList(NetworkPage networkPage) {
		super(networkPage);
	}

	@Override
	public double getHeightComponent() {
		return 440;
	}

	@Override
	public double getWidthComponent() {
		return 400;
	}

	@Override
	public String getNameComponent() {
		return "MATCHES LIST";
	}

	@Override
	public void reset() {
		
	}

	@Override
	public String[] getValues() {
		return null;
	}

}
