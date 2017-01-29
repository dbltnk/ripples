
enum Action { RED, BLUE, WHITE, FLIP, NOTHING, LINE_RED, LINE_BLUE, LINE_FLIP }

public class Card {
	private Action[] actions;
	private Color color;
	private String name;

	public Card(Action[] actions, Color color, String name) {
		this.actions = actions;
		this.color = color;
		this.name = name;
	}

	public Action[] getActions() {
		return actions;
	}

	public Color getColor() {
		return color;
	}

	public String getName() {
		return name;
	}

}
