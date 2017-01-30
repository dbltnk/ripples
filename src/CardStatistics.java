import java.util.HashMap;
import java.util.Arrays;

/**
 * Created by dbltnk on 30.01.2017.
 */
public class CardStatistics {
    HashMap<String, Integer> stats = new HashMap<String, Integer>();

    void showStatistics() {
        System.out.println("-------------");
        System.out.println("Card values:");
        System.out.println(Arrays.asList(stats));
    }
}
