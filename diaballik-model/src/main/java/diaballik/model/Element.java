package diaballik.model;

public abstract class Element {
	protected Color color;

	public Element(final Color color) {
		this.color = color;
	}

	public Color getColor() {
		return this.color;
	}

}
