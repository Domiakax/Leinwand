package shapes;

public class Linie {
	
	private int positionX_Start, positionY_Start, positionX_Ende, positionY_Ende;

	private String farbe;

	public Linie(int positionX_Start, int positionY_Start, int positionX_Ende, int positionY_Ende, String farbe) {
		super();
		this.positionX_Start = positionX_Start;
		this.positionY_Start = positionY_Start;
		this.positionX_Ende = positionX_Ende;
		this.positionY_Ende = positionY_Ende;
		this.farbe = farbe;
	}

	public int getPositionX_Start() {
		return positionX_Start;
	}

	public void setPositionX_Start(int positionX_Start) {
		this.positionX_Start = positionX_Start;
	}

	public int getPositionY_Start() {
		return positionY_Start;
	}

	public void setPositionY_Start(int positionY_Start) {
		this.positionY_Start = positionY_Start;
	}

	public int getPositionX_Ende() {
		return positionX_Ende;
	}

	public void setPositionX_Ende(int positionX_Ende) {
		this.positionX_Ende = positionX_Ende;
	}

	public int getPositionY_Ende() {
		return positionY_Ende;
	}

	public void setPositionY_Ende(int positionY_Ende) {
		this.positionY_Ende = positionY_Ende;
	}

	public String getFarbe() {
		return farbe;
	}

	public void setFarbe(String farbe) {
		this.farbe = farbe;
	}
	

	
}
