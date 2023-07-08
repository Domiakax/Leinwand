package shapes;

public enum AttributesLinie {
	xPositionStart("positionX_Start"), 
	yPositionStart("positionY_Start"), 
	xPositionEnde("positionX_Ende"), 
	yPositionEnde("positionY_Ende"), 
	farbe("farbe");

	private final String name;

	private AttributesLinie(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return name;
	}

}
