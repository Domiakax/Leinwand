package shapes;


public class Main {
	
	public static void main(String[] args) {
		Leinwand l = new Leinwand();
//		l.drawWithoutCoordinateSystem();
		Rechteck r = new Rechteck(200, 100, 100, 50, "schwarz");
		l.zeichne(r);
		Linie line = new Linie(100, 100, 300, 300, "rot");
		l.zeichne(line);
		
	}

}
