package shapes;


public class Main {
	
	public static void main(String[] args) {
		Leinwand l = new Leinwand();
		Rechteck r = new Rechteck(200, 100, 100, 50, "schwarz");
		l.zeichne(r);
		
	}

}
