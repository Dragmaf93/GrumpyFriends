package mapEditor;

public class PROVAClone {
	String ciao;
	
	public PROVAClone(String ciao) {
		this.ciao = ciao;
	}
	
	@Override
	protected PROVAClone clone() throws CloneNotSupportedException {
		PROVAClone p = new PROVAClone(this.ciao);
		
//		p.ciao = "OOOOH";
		
		return p;
	}
	
	public static void main(String[] args) throws CloneNotSupportedException {
		PROVAClone p = new PROVAClone("ciao");
		
		PROVAClone p2 = p.clone();
		
		System.out.println(p+" "+p2);
		
	}
}
