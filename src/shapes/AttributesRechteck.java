package shapes;

public enum AttributesRechteck {
	
	positionX("positionX"),
	positionY("positionY"),
	hoehe("hoehe"),
	breite("breite"),
	farbe("farbe");
	
	
	private final String name;
	
	private AttributesRechteck(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return name;
	}
	
//	public static String getMethodeName(AttributesRechteck attribut) {
//		String name = attribut.name;
//		String substring = name.substring(1);
//		char firstchar = name.charAt(0);
//		String first = Character.toString(firstchar).toUpperCase();
//		return "get" + first + substring;
//	}
}
