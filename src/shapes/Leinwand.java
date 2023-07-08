package shapes;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class Leinwand extends JFrame{

	
	private Zeichenfläche zeichenfläche;
	private Map<Object, ShapeMitFarbe> figurZuShape;
	private List<Object> figuren;
	
	private static final String rechteckClassName = "Rechteck";
	private static final String kreisClassName = "Kreis";
//	private final String dreieckClassName = "Dreieck";
	
	private static final int offsetCoordinateSystem = 4;
	private static final int distanceAxis = 100;
	private boolean drawWithCoordinateSystem = true;
	
	private static final String unbekannteFigurException = "Unbekannte Figur, Schreibweise der Klasse überprüfen.";

	public Leinwand(String titel, int breite, int hoehe) {
		zeichenfläche = new Zeichenfläche();
		setTitle(titel);
		figurZuShape = new HashMap<Object, ShapeMitFarbe>();
		figuren = Collections.synchronizedList(new ArrayList<Object>());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		zeichenfläche.setPreferredSize(new Dimension(breite, hoehe));
		setContentPane(zeichenfläche);
		this.pack();
		setVisible(true);
	}
	
	
	public Leinwand() {
		this("Zeichnung", 650, 550);
	}
	
	private Color stringToColor(String farbname)    {
		switch (farbname) {
			case "rot": return Color.red;
			case "schwarz": return Color.black;
			case "blau": return Color.blue;
			case "gelb": return Color.yellow;
			case "braun": return new Color(97,73,46);
			case "gruen": return Color.green;
			case "lila": return Color.magenta;
			case "weiss": return Color.white;
			default: return Color.black;
		}
    }
	
	public void zeichne(Object figur) {
		String className = figur.getClass().getSimpleName();
		switch (className) {
			case rechteckClassName: rechteckZeichnen(figur);
								    break;
			case kreisClassName: kreisZeichnen(figur);
								 break;
//			case dreieckClassName: dreieckZeichnen(figur);
//			                       break;
			default: throw new IllegalArgumentException(unbekannteFigurException);
		}
	}
	
	private void rechteckZeichnen(Object figur) {
		Field[] fields = figur.getClass().getDeclaredFields();
		Field xPosF = getField(fields, AttributesRechteck.positionX.toString()); 
		Field yPosF = getField(fields, AttributesRechteck.positionY.toString());
		Field breiteF = getField(fields, AttributesRechteck.breite.toString());
		Field hoeheF = getField(fields, AttributesRechteck.hoehe.toString());
		Field farbeF = getField(fields, AttributesRechteck.farbe.toString());
		
		int xPos = getFieldValueInt(xPosF, AttributesRechteck.positionX.toString(), figur);
		int yPos = getFieldValueInt(yPosF, AttributesRechteck.positionY.toString(), figur);
		int breite = getFieldValueInt(breiteF, AttributesRechteck.breite.toString(), figur);
		int hoehe = getFieldValueInt(hoeheF, AttributesRechteck.hoehe.toString(), figur);
		
		if(breite < 0 || hoehe < 0) {
			throw new IllegalArgumentException("Breite bzw. Höhe des Rechtecks ist negativ");
		}
		
		String farbe = getFieldValueString(farbeF, AttributesRechteck.farbe.toString(), figur);
		Rectangle rec = new Rectangle(xPos, yPos, breite, hoehe);
		figuren.remove(figur);
		figuren.add(figur);
		figurZuShape.put(figur, new ShapeMitFarbe(rec, stringToColor(farbe)));
		zeichenfläche.repaint();
	}
	
	
	private String getFieldValueString(Field field, String attribut, Object figur) {
		if(field.getModifiers() != Modifier.PRIVATE) {
			try {
				field.setAccessible(true);
				return (String) field.get(figur);
			} catch (IllegalArgumentException | IllegalAccessException | ClassCastException e) {
				throw new IllegalArgumentException("Falscher Datentyp von " + field.getName());
			}
		}
		else {
			try {
				Method method = figur.getClass().getDeclaredMethod(AttributesKreis.getMethodeName(attribut));
				method.setAccessible(true);
				return (String) method.invoke(figur, (Object[])null);
			} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException
					| SecurityException e) {
				throw new IllegalArgumentException("get-Methode für " + attribut + " fehlt bzw. falsch geschrieben");
			}
		}
		
	}

	private int getFieldValueInt(Field field, String attribut, Object figur) {
		if(field.getModifiers() != Modifier.PRIVATE) {
			try {
				field.setAccessible(true);
				return field.getInt(figur);
			} catch (IllegalArgumentException | IllegalAccessException e) {
//				e.printStackTrace();
				throw new IllegalArgumentException("Falscher Datentyp von " + field.getName());
			}
		}
		else try {
			Method method = figur.getClass().getDeclaredMethod(AttributesKreis.getMethodeName(attribut));
			method.setAccessible(true);
			return (int) method.invoke(figur, (Object[])null);
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException
				| SecurityException e) {
			throw new IllegalArgumentException("get-Methode für " + attribut + " fehlt bzw. falsch geschrieben");
		}
	}
	

	private void kreisZeichnen(Object figur) {
		Field[] fields = figur.getClass().getDeclaredFields();

		Field xPosF = getField(fields, AttributesKreis.positionX.toString()); 
		Field yPosF = getField(fields, AttributesKreis.positionY.toString());
		Field radiusF = getField(fields, AttributesKreis.radius.toString());
		Field farbeF = getField(fields, AttributesKreis.farbe.toString());
		
		int radius = getFieldValueInt(radiusF, AttributesKreis.radius.toString(), figur);
		
		if(radius < 0) {
			throw new IllegalArgumentException("Radius ist negativ");
		}
		
		int xPos = getFieldValueInt(xPosF, AttributesKreis.positionX.toString(), figur);
		int yPos = getFieldValueInt(yPosF, AttributesKreis.positionY.toString(), figur);
		String farbe = getFieldValueString(farbeF, AttributesKreis.farbe.toString(), figur);
		Ellipse2D kreis = new Ellipse2D.Double(xPos-radius, yPos-radius, 2*radius, 2*radius);
		figuren.remove(figur);
		figuren.add(figur);
		figurZuShape.put(figur, new ShapeMitFarbe(kreis, stringToColor(farbe)));
		zeichenfläche.repaint();
	}

	public void warte(long millisekunden) {
		try {
			Thread.sleep(millisekunden);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public int getLeinwandBreite() {
		return this.getContentPane().getWidth();
	}
	
	public int getLeinwandHoehe() {
		return this.getContentPane().getHeight();
	}
	
	public void drawWithCoordinateSystem() {
		drawWithCoordinateSystem = true;
	}
	
	public void drawWithoutCoordinateSystem() {
		drawWithCoordinateSystem = false;
	}

	private Field getField(Field[] fields, String attribut) {
    	for(Field f : fields) {
    		if(f.getName().equals(attribut)) {
    			return f;
    		}
    	}
    	throw new IllegalArgumentException("Unbekanntes Attribut: " + attribut);
    }

	private class ShapeMitFarbe{
		private Shape shape;
		private Color farbe;
		
		public ShapeMitFarbe(Shape shape, Color farbe) {
			this.shape = shape;
			this.farbe = farbe;
		}
		
		public void draw(Graphics2D graphic) {
			graphic.setColor(farbe);
			graphic.fill(shape);
		}
	}
	
	private class Zeichenfläche extends JPanel{
		

		@Override
		public void paintComponent(Graphics g) {
			Graphics2D g2d = (Graphics2D) g;
			g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			g2d.setColor(Color.white);
			g2d.fillRect(0, 0, getWidth(), getHeight());
			if(drawWithCoordinateSystem) {
				drawCoordinateSystem(g2d);
			}
			synchronized (figuren) {
				for(Object figur : figuren) {
					figurZuShape.get(figur).draw(g2d);
				}
			}
		}

		private void drawCoordinateSystem(Graphics2D g2d) {
			int hoehe = this.getHeight();
			int breite = this.getWidth();
			g2d.setColor(Color.BLACK);
			g2d.drawLine(offsetCoordinateSystem, offsetCoordinateSystem, offsetCoordinateSystem, hoehe);
			g2d.drawLine(offsetCoordinateSystem, offsetCoordinateSystem, breite, offsetCoordinateSystem);

			int numberXlines = breite / distanceAxis;
			for(int i = 1; i<= numberXlines; i++) {
				int xPosition = i*distanceAxis;
				g2d.drawLine(xPosition, 0, xPosition, 2*offsetCoordinateSystem);
				String number = xPosition + "";
				g2d.drawString(number, xPosition-2*offsetCoordinateSystem, 5*offsetCoordinateSystem);
			}
			
			int numberYLines = hoehe / distanceAxis;
			for(int i = 1;i<= numberYLines; i++) {
				int yPosition = i*distanceAxis;
				g2d.drawLine(0, yPosition, 2*offsetCoordinateSystem, yPosition);
				String number = yPosition + "";
				g2d.drawString(number, 4*offsetCoordinateSystem, yPosition+offsetCoordinateSystem);
			}
			
		}
	}
}
