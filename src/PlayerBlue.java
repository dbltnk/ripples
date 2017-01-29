public class PlayerBlue extends Player {

	public PlayerBlue() {

		color = Color.BLUE;

		Action[] actions1 = { Action.WHITE, Action.WHITE, Action.RED, Action.BLUE, Action.BLUE, Action.RED };
		cards.add(new Card(actions1, Color.BLUE, "B:w/w/r/b/b/r"));
		Action[] actions2 = { Action.BLUE, Action.BLUE, Action.BLUE, Action.RED, Action.RED, Action.RED };
		cards.add(new Card(actions2, Color.BLUE, "B:b/b/b/r/r/r"));
		Action[] actions3 = { Action.BLUE, Action.NOTHING, Action.RED, Action.NOTHING, Action.BLUE, Action.NOTHING };
		cards.add(new Card(actions3, Color.WHITE, "W:b/n/r/n/b/n"));
		Action[] actions4 = { Action.FLIP, Action.FLIP, Action.FLIP, Action.FLIP, Action.RED, Action.RED };
		cards.add(new Card(actions4, Color.BLUE, "B:f/f/f/f/r/r"));
		Action[] actions5 = { Action.WHITE, Action.RED, Action.RED, Action.LINE_BLUE, Action.RED, Action.RED };
		cards.add(new Card(actions5, Color.BLUE, "B:w/r/r/lb/r/r"));
		Action[] actions6 = { Action.RED, Action.LINE_FLIP, Action.RED, Action.LINE_FLIP, Action.RED, Action.LINE_FLIP };
		cards.add(new Card(actions6, Color.BLUE, "B:r/lf/r/lf/r/lf"));
		Action[] actions7 = { Action.BLUE, Action.RED, Action.BLUE, Action.RED, Action.BLUE, Action.RED };
		cards.add(new Card(actions7, Color.BLUE, "B:b/r/b/r/b/r"));
		Action[] actions8 = { Action.WHITE, Action.NOTHING, Action.LINE_FLIP, Action.BLUE, Action.RED, Action.RED };
		cards.add(new Card(actions8, Color.BLUE, "B:w/n/lf/b/r/r"));
	}

}
