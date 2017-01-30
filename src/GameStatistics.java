import java.util.ArrayList;
import java.util.List;
import java.text.DecimalFormat;

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

		DecimalFormat df = new DecimalFormat("#.##");
		String formattedWinsRed = df.format(averageWinsRed * 100f);
		String formattedWinsBlue = df.format(averageWinsBlue * 100f);
		String formattedDraws = df.format(averageDraws * 100f);

		System.out.println("Statistics:");
		System.out.println("Games Played: " + totalGamesPlayed);
		System.out.println("-------------");
		System.out.println("Red Tiles Total: " + totalScoreRed + "\tRed Tiles Average: " + averageScoreRed);
		System.out.println("Blue Tiles Total: " + totalScoreBlue + "\tBlue Tiles Average: " + averageScoreBlue);
		System.out.println("White Tiles Total: " + totalScoreWhite + "\tWhite Tiles Average: " + averageScoreWhite);
		System.out.println("-------------");
		System.out.println("Red Victories: " + winsRed + "\tRed Win Rate: " + formattedWinsRed  + "%");
		System.out.println("Blue Victories: " + winsBlue + "\tBlue Win Rate: " + formattedWinsBlue + "%");
		System.out.println("Draws: " + draws + "\tDraw Rate: " + formattedDraws + "%");
	}

}
