public class PlayerRed extends Player {

	public PlayerRed() {
		Action[] actions1 = { Action.WHITE, Action.WHITE, Action.BLUE, Action.RED, Action.RED, Action.BLUE };
		cards.add(new Card(actions1, Color.RED));
		Action[] actions2 = { Action.RED, Action.RED, Action.RED, Action.BLUE, Action.BLUE, Action.BLUE };
		cards.add(new Card(actions2, Color.RED));
		Action[] actions3 = { Action.RED, Action.NOTHING, Action.BLUE, Action.NOTHING, Action.RED, Action.NOTHING };
		cards.add(new Card(actions3, Color.WHITE));
		Action[] actions4 = { Action.FLIP, Action.FLIP, Action.FLIP, Action.FLIP, Action.BLUE, Action.BLUE };
		cards.add(new Card(actions4, Color.RED));
		Action[] actions5 = { Action.WHITE, Action.BLUE, Action.BLUE, Action.LINE_RED, Action.BLUE, Action.BLUE };
		cards.add(new Card(actions5, Color.RED));
		Action[] actions6 = { Action.BLUE, Action.LINE_FLIP, Action.BLUE, Action.LINE_FLIP, Action.BLUE, Action.LINE_FLIP };
		cards.add(new Card(actions6, Color.RED));
		Action[] actions7 = { Action.RED, Action.BLUE, Action.RED, Action.BLUE, Action.RED, Action.BLUE };
		cards.add(new Card(actions7, Color.RED));
		Action[] actions8 = { Action.WHITE, Action.NOTHING, Action.LINE_FLIP, Action.RED, Action.BLUE, Action.BLUE };
		cards.add(new Card(actions8, Color.RED));
	}

}
