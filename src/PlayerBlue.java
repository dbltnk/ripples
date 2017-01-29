public class PlayerBlue extends Player {

	public PlayerBlue() {

		color = Color.BLUE;
		/*
		Action[] actions1 = { Action.WHITE, Action.WHITE, Action.RED, Action.BLUE, Action.BLUE, Action.RED };
		cards.add(new Card(actions1, Color.BLUE));
		Action[] actions2 = { Action.BLUE, Action.BLUE, Action.BLUE, Action.RED, Action.RED, Action.RED };
		cards.add(new Card(actions2, Color.BLUE));
		*/
		Action[] actions3 = { Action.BLUE, Action.NOTHING, Action.RED, Action.NOTHING, Action.BLUE, Action.NOTHING };
		cards.add(new Card(actions3, Color.WHITE));

		Action[] actions4 = { Action.FLIP, Action.FLIP, Action.FLIP, Action.FLIP, Action.RED, Action.RED };
		cards.add(new Card(actions4, Color.BLUE));
		Action[] actions5 = { Action.WHITE, Action.RED, Action.RED, Action.LINE_BLUE, Action.RED, Action.RED };
		cards.add(new Card(actions5, Color.BLUE));

		Action[] actions6 = { Action.RED, Action.LINE_FLIP, Action.RED, Action.LINE_FLIP, Action.RED, Action.LINE_FLIP };
		cards.add(new Card(actions6, Color.BLUE));
		/*
		Action[] actions7 = { Action.BLUE, Action.RED, Action.BLUE, Action.RED, Action.BLUE, Action.RED };
		cards.add(new Card(actions7, Color.BLUE));
		Action[] actions8 = { Action.WHITE, Action.NOTHING, Action.LINE_FLIP, Action.BLUE, Action.RED, Action.RED };
		cards.add(new Card(actions8, Color.BLUE));
		*/
	}

}
