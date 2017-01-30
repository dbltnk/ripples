public class PlayerRed extends Player {

	public PlayerRed() {

		color = Color.RED;
		Action[] actions1 = { Action.WHITE, Action.WHITE, Action.BLUE, Action.RED, Action.RED, Action.BLUE };
		cards.add(new Card(actions1, Color.RED, "R:w/w/b/r/r/b"));
		Action[] actions2 = { Action.RED, Action.RED, Action.RED, Action.BLUE, Action.BLUE, Action.BLUE };
		cards.add(new Card(actions2, Color.RED, "R:r/r/r/b/b/b"));
		Action[] actions3 = { Action.RED, Action.NOTHING, Action.BLUE, Action.NOTHING, Action.RED, Action.NOTHING };
		cards.add(new Card(actions3, Color.WHITE, "W:r/n/b/n/r/n"));
		Action[] actions4 = { Action.FLIP, Action.FLIP, Action.FLIP, Action.FLIP, Action.BLUE, Action.BLUE };
		cards.add(new Card(actions4, Color.RED, "R:f/f/f/f/b/b"));
		Action[] actions5 = { Action.WHITE, Action.BLUE, Action.RED, Action.WHITE, Action.BLUE, Action.RED };
		cards.add(new Card(actions5, Color.RED, "R:w/b/r/w/b/r"));
		Action[] actions6 = { Action.RED, Action.FLIP, Action.BLUE, Action.RED, Action.BLUE, Action.FLIP };
		cards.add(new Card(actions6, Color.RED, "R:r/f/b/r/b/b"));
		Action[] actions7 = { Action.RED, Action.BLUE, Action.RED, Action.BLUE, Action.RED, Action.BLUE };
		cards.add(new Card(actions7, Color.RED, "R:r/b/r/b/r/b"));
		Action[] actions8 = { Action.WHITE, Action.NOTHING, Action.FLIP, Action.RED, Action.BLUE, Action.BLUE };
		cards.add(new Card(actions8, Color.RED, "R:w/n/f/r/b/b"));
	}

}
