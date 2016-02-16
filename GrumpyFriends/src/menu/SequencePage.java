package menu;

import java.util.ArrayList;
import java.util.List;

import javax.sound.midi.Sequence;

public class SequencePage {

	private List<MenuPage> pages;
	private int currentPage;

	public SequencePage(List<MenuPage> pages) {
		this.pages = pages;
		currentPage = -1;
	}

	public SequencePage(MenuPage... pages) {
		this.pages = new ArrayList<MenuPage>();

		for (MenuPage menuPage : pages) {
			this.pages.add(menuPage);
		}

		currentPage = -1;
	}

	public MenuPage nextPage() {

		if (currentPage + 1 >= pages.size())
			return null;
		currentPage++;
		return pages.get(currentPage);
	}

	public void restartSequence() {
			for (MenuPage menuPage : pages) {
				menuPage.reset();
			}
			currentPage = -1;
	}

	public MenuPage currentPage(){
		if(currentPage<0) return null;
		
		return pages.get(currentPage);
	}
	
	public MenuPage prevPage() {

		if (currentPage - 1 < 0)
			return null;
		
		currentPage--;
		return pages.get(currentPage);
	}

	public List<MenuPage> getMenuPages() {
		return pages;
	}
}
