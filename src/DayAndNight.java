import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class DayAndNight {
	private static int gamesToPlay = 1;

	private List<Hexagon> hexagons = new ArrayList<>();
	private Player playerRed = new PlayerRed();
	private Player playerBlue = new PlayerBlue();
	private Random random = new Random();
	private GameScore gameScore = new GameScore();

    private Coordinate[] directions = {
		new Coordinate(+1, -1,  0), new Coordinate(+1,  0, -1), new Coordinate( 0, +1, -1),
		new Coordinate(-1, +1,  0), new Coordinate(-1,  0, +1), new Coordinate( 0, -1, +1)
	};

	public static void main(String[] args) {
		GameStatistics statistics = new GameStatistics();
		for (int i = 0; i < gamesToPlay; i++) {
			DayAndNight dan = new DayAndNight();
			statistics.gameScores.add(dan.playGame());
			if (gamesToPlay == 1) {
				System.out.println(dan);
				System.out.print("Red: " + statistics.gameScores.get(0).scoreRed + " | ");
				System.out.print("Blue: " + statistics.gameScores.get(0).scoreBlue + " | ");
				System.out.print("White: " + statistics.gameScores.get(0).scoreWhite + "\n");
			}
		}
		if (gamesToPlay > 1) {
			statistics.showStatistics();
		}
	}

    private DayAndNight() {
		SetupPlayfield();
	}

    private GameScore playGame() {
		Player currentPlayer = playerRed;
		do {
			playCardRandomly(currentPlayer);
			currentPlayer = selectNextPlayer(currentPlayer);
		} while (playerRed.hasCards() || playerBlue.hasCards());
		scorePlayfield();
		return gameScore;
	}

	private Player selectNextPlayer(Player currentPlayer) {
		if (currentPlayer == playerRed) {
			return playerBlue;
		} else {
			return playerRed;
		}
	}

	private Hexagon selectRandomValidField() {
		Hexagon selectedField;
		do {
			selectedField = hexagons.get(random.nextInt(hexagons.size()));
		} while (selectedField.blocked);
		return selectedField;
	}

	private Card selectAndRemoveRandomCard(Player currentPlayer) {
		Card card = currentPlayer.cards.get(random.nextInt(currentPlayer.cards.size()));
		currentPlayer.cards.remove(card);
		return card;
	}

	private void playCardRandomly(Player currentPlayer) {
		Hexagon selectedField = selectRandomValidField();
		Card card = selectAndRemoveRandomCard(currentPlayer);
		int rotationOffset = random.nextInt(6);
		makeSelectedMove(selectedField, card, rotationOffset);
	}

	private void makeSelectedMove(Hexagon selectedField, Card card, int rotationOffset) {
		selectedField.color = card.getColor();
		selectedField.blocked = true;

		for (Action action : card.getActions()) {
			Coordinate direction = directions[rotationOffset % 6];
			rotationOffset++;

			Coordinate neighbourCoordinate = selectedField.getCoordinate().addCoordinate(direction);
			Hexagon neighbour = getHexagonForCoordinate(neighbourCoordinate);

			if (neighbour != null) {
				switch (action) {
				case RED:
					RecolorOne(Color.RED, neighbour);
					break;
				case BLUE:
					RecolorOne(Color.BLUE, neighbour);
					break;
				case WHITE:
					RecolorOne(Color.WHITE, neighbour);
					break;
                case FLIP:
					FlipcolorOne(neighbour);
					break;
 				case NOTHING:
					// nothing to do
					break;
                case LINE_RED:
					RecolorMany(Color.RED, FindHexagonsInLine(neighbour, direction));
					break;
                case LINE_BLUE:
					RecolorMany(Color.BLUE, FindHexagonsInLine(neighbour, direction));
					break;
                case LINE_FLIP:
					FlipcolorMany(FindHexagonsInLine(neighbour, direction));
					break;
				default:
					break;
				}
			}
		}
	}

	private void RecolorOne(Color color, Hexagon hex) {
		if (hex.color != color) {
			hex.blocked = false;
		}
		hex.color = color;
	}

	private void RecolorMany(Color color, List<Hexagon> hexes) {
		for (Hexagon hexagon : hexes) {
			RecolorOne(color, hexagon);
		}
	}

	private void FlipcolorOne(Hexagon hex) {
		switch (hex.color) {
			case BLUE:
				RecolorOne(Color.RED, hex);
				hex.blocked = false;
				break;
			case RED:
				RecolorOne(Color.BLUE, hex);
				hex.blocked = false;
				break;
			default:
				break;
		}
	}

	private void FlipcolorMany(List<Hexagon> hexes) {
		for (Hexagon hexagon : hexes) {
			FlipcolorOne(hexagon);
		}
	}

	// this includes the origin hex
	private List<Hexagon> FindHexagonsInLine(Hexagon origin, Coordinate direction) {
		List<Hexagon> l = new ArrayList<>();
		Hexagon current = origin;
		l.add(origin);

		if (direction == new Coordinate(0,0,0) ) {
			return l;
		}

		while(true)
		{
			Coordinate nextNeighbourCoordinate = current.getCoordinate().addCoordinate(direction);
			Hexagon nextNeighbour = getHexagonForCoordinate(nextNeighbourCoordinate);
			if (nextNeighbour == null) {
				return l;
			}
			else {
				l.add(nextNeighbour);
				current = nextNeighbour;
			}
		}
	}

	private void scorePlayfield() {
		int numberOfFields = hexagons.size();
		for (int i = 0; i <= numberOfFields - 1; i++) {
			switch (hexagons.get(i).color) {
				case RED:
					gameScore.scoreRed++;
					break;
				case BLUE:
					gameScore.scoreBlue++;
					break;
				case WHITE:
					gameScore.scoreWhite++;
					break;
				default:
					break;
			}
		}
		if (gameScore.scoreRed > gameScore.scoreBlue) {
			gameScore.winsRed++;
		} else if (gameScore.scoreRed < gameScore.scoreBlue) {
			gameScore.winsBlue++;
		} else {
			gameScore.draws++;
		}
	}

	@Override
	public String toString() {
		for (int z = -2; z <= 2; z++) {
			for (int x = -2; x <= 2; x++) {
				for (Hexagon hexagon : hexagons) {
						if (hexagon.getX() == x && hexagon.getZ() == z) {
							if (hexagons.indexOf(hexagon) == 0 || hexagons.indexOf(hexagon) == 16) {
								System.out.print("  ");
							}
							if (hexagons.indexOf(hexagon) == 3 || hexagons.indexOf(hexagon) == 12) {
								System.out.print(" ");
							}
							switch (hexagon.color) {
							case RED:
								if (hexagon.blocked) {
									System.out.print("[R]");
								}
								else {
									System.out.print("(R)");
								}
								break;
							case BLUE:
								if (hexagon.blocked) {
									System.out.print("[B]");
								}
								else {
									System.out.print("(B)");
								}
								break;
							case WHITE:
								if (hexagon.blocked) {
									System.out.print("[W]");
								}
								else {
									System.out.print("(W)");
								}
								break;
							default:
								break;
							}
						}
				}
			}
			System.out.println();
		}
		return super.toString();
	}

	private Hexagon getHexagonForCoordinate(Coordinate coordinate) {
		for (Hexagon hexagon : hexagons) {
			if (hexagon.getCoordinate().equals(coordinate)) {
				return hexagon;
			}
		}
		return null;
	}

	private void SetupPlayfield() {
		hexagons.add(new Hexagon(0, 2, -2));
		hexagons.add(new Hexagon(1, 1, -2));
		hexagons.add(new Hexagon(2, 0, -2));

		hexagons.add(new Hexagon(-1, 2, -1));
		hexagons.add(new Hexagon(0, 1, -1));
		hexagons.add(new Hexagon(1, 0, -1));
		hexagons.add(new Hexagon(2, -1, -1));

		hexagons.add(new Hexagon(-2, 2, 0));
		hexagons.add(new Hexagon(-1, 1, 0));
		hexagons.add(new Hexagon(0, 0, 0));
		hexagons.add(new Hexagon(1, -1, 0));
		hexagons.add(new Hexagon(2, -2, 0));

		hexagons.add(new Hexagon(-2, 1, 1));
		hexagons.add(new Hexagon(-1, 0, 1));
		hexagons.add(new Hexagon(0, -1, 1));
		hexagons.add(new Hexagon(1, -2, 1));

		hexagons.add(new Hexagon(-2, 0, 2));
		hexagons.add(new Hexagon(-1, -1, 2));
		hexagons.add(new Hexagon(0, -2, 2));

		// only for testing, should be deactivated
		/*
		for (Hexagon hexagon : hexagons) {
			hexagon.color = Color.RED;
			//hexagon.blocked = true;
		}
		*/
	}

}
