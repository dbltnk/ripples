import java.util.ArrayList;
import java.util.List;

public class GameStatistics {
	List<GameScore> gameScores = new ArrayList<GameScore>();

	void showStatistics() {
		int totalScoreRed = 0;
		int totalScoreBlue = 0;
		int totalScoreWhite = 0;
		int winsRed = 0;
		int winsBlue = 0;
		int draws = 0;
		for (GameScore gameScore : gameScores) {
			totalScoreRed += gameScore.scoreRed;
			totalScoreBlue += gameScore.scoreBlue;
			totalScoreWhite += gameScore.scoreWhite;
			winsRed += gameScore.winsRed;
			winsBlue += gameScore.winsBlue;
			draws += gameScore.draws;
		}
		double totalGamesPlayed = (double) gameScores.size();
		double averageScoreRed = totalScoreRed / totalGamesPlayed;
		double averageScoreBlue = totalScoreBlue / totalGamesPlayed ;
		double averageScoreWhite = totalScoreWhite / totalGamesPlayed;

		double averageWinsRed = winsRed / totalGamesPlayed;
		double averageWinsBlue = winsBlue / totalGamesPlayed ;
		double averageDraws = draws / totalGamesPlayed;

		System.out.println("Statistics:");
		System.out.println("Games Played: " + totalGamesPlayed);
		System.out.println("-------------");
		System.out.println("Red Tiles Total: " + totalScoreRed + "\tRed Tiles Average: " + averageScoreRed);
		System.out.println("Blue Tiles Total: " + totalScoreBlue + "\tBlue Tiles Average: " + averageScoreBlue);
		System.out.println("White Tiles Total: " + totalScoreWhite + "\tWhite Tiles Average: " + averageScoreWhite);
		System.out.println("-------------");
		System.out.println("Red Victories: " + winsRed + "\tRed Win Rate: " + averageWinsRed * 100f + "%");
		System.out.println("Blue Victories: " + winsBlue + "\tBlue Win Rate: " + averageWinsBlue * 100f + "%");
		System.out.println("Draws: " + draws + "\tDraw Rate: " + averageDraws * 100f + "%");
	}

}
