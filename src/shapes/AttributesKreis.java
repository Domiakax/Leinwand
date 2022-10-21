package shapes;

public enum AttributesKreis{
	
	positionX("positionX"),
	positionY("positionY"),
	radius("radius"),
	farbe("farbe");
	
	private final String name;
	
	private AttributesKreis(String name) {
		this.name = name;
	}
	
	@Override
	public String toString() {
		return name;
	}
	
	public static String getMethodeName(String name) {
		String substring = name.substring(1);
		char firstchar = name.charAt(0);
		String first = Character.toString(firstchar).toUpperCase();
		return "get" + first + substring;
	}

}
