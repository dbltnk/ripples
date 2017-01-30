import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Comparator;
import java.util.PriorityQueue;

public class DayAndNight {
	private static int gamesToPlay = 100;
	private List<Hexagon> hexagons = new ArrayList<>();
	private Player playerRed = new PlayerRed();
	private Player playerBlue = new PlayerBlue();
	private Random random = new Random();
	private GameScore gameScore = new GameScore();
	static CardStatistics cardStatistics = new CardStatistics();

    private Coordinate[] directions = {
		new Coordinate(+1, -1,  0), new Coordinate(+1,  0, -1), new Coordinate( 0, +1, -1),
		new Coordinate(-1, +1,  0), new Coordinate(-1,  0, +1), new Coordinate( 0, -1, +1)
	};

	public static void main(String[] args) {
		GameStatistics statistics = new GameStatistics();
		for (int i = 0; i < gamesToPlay; i++) {
			DayAndNight dan = new DayAndNight();
			if (gamesToPlay == 1) {
				//System.out.println("--- Power Level Pre-Game ---");
				//Evaluate(dan.hexagons, dan.playerRed, dan.playerBlue);
				//System.out.println("----------------------------");
			}
			statistics.gameScores.add(dan.playGame());
			if (gamesToPlay == 1) {
				//System.out.println(dan);
				System.out.print("Red: " + statistics.gameScores.get(0).scoreRed + " | ");
				System.out.print("Blue: " + statistics.gameScores.get(0).scoreBlue + " | ");
				System.out.print("White: " + statistics.gameScores.get(0).scoreWhite + "\n");
				//System.out.println("--- Power Level Post-Game ---");
				//Evaluate(dan.hexagons, dan.playerRed, dan.playerBlue);
				//System.out.println("----------------------------");
			}
		}
		if (gamesToPlay > 1) {
			statistics.showStatistics();
			cardStatistics.showStatistics();
		}
	}

	private DayAndNight() {
		SetupPlayfield();
	}

	private GameScore playGame() {

		List<Player> turnOrder = new ArrayList<>();
		turnOrder.add(playerRed);
		turnOrder.add(playerBlue);
		turnOrder.add(playerRed);
		turnOrder.add(playerBlue);
		turnOrder.add(playerBlue);
		turnOrder.add(playerRed);
		turnOrder.add(playerBlue);
		turnOrder.add(playerRed);
		Player currentPlayer;

		for (int i = 0; i < turnOrder.size(); i++) {
			currentPlayer = turnOrder.get(i);
			makeInformedDecision(currentPlayer);
			//playCardRandomly(currentPlayer);
		}

		// old turn order
		/*
		Player currentPlayer = playerRed;
		do {
			//playCardRandomly(currentPlayer);
			makeInformedDecision(currentPlayer);
			currentPlayer = selectNextPlayer(currentPlayer);
		} while (playerRed.hasCards() || playerBlue.hasCards());
		*/

		scorePlayfield();
		return gameScore;
	}

	private void makeInformedDecision(Player currentPlayer) {
		//score playfield
		int initialScore = EvaluatePlayfield(hexagons, currentPlayer);

		//save playfield
		List<Hexagon> initialPlayfield = new ArrayList<>();
		for(Hexagon hex : hexagons) {
			initialPlayfield.add(hex.clone());
		}

		//set up move DB
		Comparator<Move> comparator = new MoveComparator();
		PriorityQueue<Move> moves = new PriorityQueue<Move>(10, comparator);

		//for hexes, cards, rotations
		for (Hexagon hex : hexagons) {
			if (hex.blocked == false) {
				for (Card card : currentPlayer.cards) {
					int numberOfRotations = 6;
					for (int i = 0; i < numberOfRotations; i++) {
						Move move = new Move();
						move.hex = hex;
						move.card = card;
						move.rotationOffset = i;
						//make move
						makeSelectedMove(hex, card, i);
						//score playfield
						int newScore = EvaluatePlayfield(hexagons, currentPlayer);
						//calculate change
						int scoreChange = newScore - initialScore;
						move.scoreChange = scoreChange;
						//store change and move in DB
						moves.add(move);
						//load playfield
						int numberOfHexFields = hexagons.size();
						for (int j = 0; j < numberOfHexFields; j++) {
							hexagons.get(j).blocked = initialPlayfield.get(j).blocked;
							hexagons.get(j).color = initialPlayfield.get(j).color;
						}
					}
				}
			}
		}

		//get best move
		List<Move> bestPotentialMoves = new ArrayList<>();
		for (Move move : moves) {
			int highestScore = moves.peek().scoreChange;
			if (move.scoreChange == highestScore) {
				bestPotentialMoves.add(move);
			}
		}
		int numberOfPotentialMoves = bestPotentialMoves.size();
		int randomNumber = random.nextInt(numberOfPotentialMoves);
		Move moveToMake = bestPotentialMoves.get(randomNumber);

		//make move
		makeSelectedMove(moveToMake.hex, moveToMake.card, moveToMake.rotationOffset);
		currentPlayer.cards.remove(moveToMake.card);

		String cardName = moveToMake.card.getName();
		if (cardStatistics.stats.containsKey(cardName)) {
			int oldValue = cardStatistics.stats.get(cardName).intValue();
			cardStatistics.stats.put(cardName, oldValue + moveToMake.scoreChange);
		}
		else {
			cardStatistics.stats.put(cardName, moveToMake.scoreChange);
		}

		//debug code for single games
		if (gamesToPlay == 1) {
			System.out.println("-----------------");
			System.out.println(currentPlayer.color.toString() + " plays card: " + moveToMake.card.getName());
			renderPlayfield(hexagons);
			System.out.println("Red: " + EvaluatePlayfield(hexagons, playerRed) + " Blue: " + EvaluatePlayfield(hexagons, playerBlue));
			System.out.println("-----------------");
		}
	}

	private int EvaluatePlayfield(List<Hexagon> playfield, Player currentPlayer) {
		int redHexes = 0;
		int blueHexes = 0;
		int whiteHexes = 0;
		for (Hexagon hex : playfield) {
			switch (hex.color) {
				case RED:
					redHexes++;
					break;
				case BLUE:
					blueHexes++;
					break;
				case WHITE:
					whiteHexes++;
					break;
				default:
					break;
			}
		}

		if (currentPlayer.color == Color.RED) {
			return redHexes - blueHexes;
		}
		else {
			return blueHexes - redHexes;
		}
	}

	private static void Evaluate(List<Hexagon> playfield, Player playerRed, Player playerBlue) {
		float redPower = 0;
		float bluePower = 0;

		// evaluate playfield
		int redHexes = 0;
		int blueHexes = 0;
		int whiteHexes = 0;
		for (Hexagon hex : playfield) {
			switch (hex.color) {
				case RED:
					redHexes++;
					break;
				case BLUE:
					blueHexes++;
					break;
				case WHITE:
					whiteHexes++;
					break;
				default:
					break;
			}
		}

		// evaluate hands
		float valueHandRed = evaluateHand(playerRed);
		float valueHandBlue = evaluateHand(playerBlue);

		// calculate power
		redPower = redHexes - blueHexes + valueHandRed - valueHandBlue;
		bluePower = blueHexes - redHexes + valueHandBlue - valueHandRed;
		System.out.println("Red player power level: " + redPower);
		System.out.println("Blue player power level: " + bluePower);
	}

	private static float evaluateHand(Player player) {
		float valuePlayerColor = 1f;
		float valueOpponentColor = -1f;
		float valueWhite = 0f;
		float valueFlip = 0.5f;
		float valueNothing = 0f;
		float lineHexAffectExpectation = 4f;

		Color playerColor = player.color;
		float handValue = 0;
		for (Card card : player.cards) {
			float cardValue = 0;
			switch (card.getColor()) {
				case RED:
					cardValue = cardValue + valuePlayerColor;
					break;
				case BLUE:
					cardValue = cardValue + valueOpponentColor;
					break;
				case WHITE:
					cardValue = cardValue + valueWhite;
					break;
				default:
					break;
			}
			for (Action action : card.getActions()) {
				switch (action) {
					case RED:
						if (playerColor == Color.RED) {
							cardValue = cardValue + valuePlayerColor;
						}
						else {
							cardValue = cardValue + valueOpponentColor;
						}
						break;
					case BLUE:
						if (playerColor == Color.RED) {
							cardValue = cardValue + valueOpponentColor;
						}
						else {
							cardValue = cardValue + valuePlayerColor;
						}
						break;
					case WHITE:
						cardValue = cardValue + valueWhite;
						break;
					case FLIP:
						cardValue = cardValue + valueFlip;
						break;
					case NOTHING:
						cardValue = cardValue + valueNothing;
						break;
					case LINE_RED:
						if (playerColor == Color.RED) {
							cardValue = cardValue + valuePlayerColor * lineHexAffectExpectation;
						}
						else {
							cardValue = cardValue + valueOpponentColor * lineHexAffectExpectation;
						}
						break;
					case LINE_BLUE:
						if (playerColor == Color.RED) {
							cardValue = cardValue + valueOpponentColor * lineHexAffectExpectation;
						}
						else {
							cardValue = cardValue + valuePlayerColor * lineHexAffectExpectation;
						}
						break;
					case LINE_FLIP:
						cardValue = cardValue + valueFlip * lineHexAffectExpectation;
						break;
					default:
						break;
				}
			}
			//System.out.println("Card value: " + cardValue);
			handValue += cardValue;
		}
		//System.out.println("Hand value: " + handValue);
		return handValue;
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
		for (int i = 0; i <numberOfFields; i++) {
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

	private String renderPlayfield(List<Hexagon> playfield) {
		//System.out.print(" ");
		for (int z = -3; z <= 3; z++) {
			for (int x = -3; x <= 3; x++) {
				for (Hexagon hexagon : playfield) {
					if (hexagon.getX() == x && hexagon.getZ() == z) {
						if (playfield.indexOf(hexagon) == 9 || playfield.indexOf(hexagon) == 22) {
							System.out.print(" ");
						}
						if (playfield.indexOf(hexagon) == 4 || playfield.indexOf(hexagon) == 28) {
							System.out.print("   ");
						}
						if (playfield.indexOf(hexagon) == 0 || playfield.indexOf(hexagon) == 33) {
							System.out.print("     ");
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
		// larger field, 37 hexes

		hexagons.add(new Hexagon(0, 3, -3));
		hexagons.add(new Hexagon(1, 2, -3));
		hexagons.add(new Hexagon(2, 1, -3));
		hexagons.add(new Hexagon(3, 0, -3));

		hexagons.add(new Hexagon(-1, 3, -2));
		hexagons.add(new Hexagon(0, 2, -2));
		hexagons.add(new Hexagon(1, 1, -2));
		hexagons.add(new Hexagon(2, 0, -2));
		hexagons.add(new Hexagon(3, -1, -2));

		hexagons.add(new Hexagon(-2, 3, -1));
		hexagons.add(new Hexagon(-1, 2, -1));
		hexagons.add(new Hexagon(0, 1, -1));
		hexagons.add(new Hexagon(1, 0, -1));
		hexagons.add(new Hexagon(2, -1, -1));
		hexagons.add(new Hexagon(3, -2, -1));

		hexagons.add(new Hexagon(-3, 3, 0));
		hexagons.add(new Hexagon(-2, 2, 0));
		hexagons.add(new Hexagon(-1, 1, 0));
		hexagons.add(new Hexagon(0, 0, 0));
		hexagons.add(new Hexagon(1, -1, 0));
		hexagons.add(new Hexagon(2, -2, 0));
		hexagons.add(new Hexagon(3, -3, 0));

		hexagons.add(new Hexagon(-3, 2, 1));
		hexagons.add(new Hexagon(-2, 1, 1));
		hexagons.add(new Hexagon(-1, 0, 1));
		hexagons.add(new Hexagon(0, -1, 1));
		hexagons.add(new Hexagon(1, -2, 1));
		hexagons.add(new Hexagon(2, -3, 1));

		hexagons.add(new Hexagon(-3, 1, 2));
		hexagons.add(new Hexagon(-2, 0, 2));
		hexagons.add(new Hexagon(-1, -1, 2));
		hexagons.add(new Hexagon(0, -2, 2));
		hexagons.add(new Hexagon(1, -3, 2));

		hexagons.add(new Hexagon(-3, 0, 3));
		hexagons.add(new Hexagon(-2, -1, 3));
		hexagons.add(new Hexagon(-1, -2, 3));
		hexagons.add(new Hexagon(0, -3, 3));


		// normal field, 19 hexes
		/*
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
		*/

		// only for testing, should be deactivated
		/*
		for (Hexagon hexagon : hexagons) {
			hexagon.color = Color.RED;
			//hexagon.blocked = true;
		}
		*/
	}

}
