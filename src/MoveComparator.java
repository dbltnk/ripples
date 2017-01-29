/**
 * Created by dbltnk on 29.01.2017.
 */

import java.util.Comparator;

public class MoveComparator implements Comparator<Move>
{
    @Override
    public int compare(Move x, Move y)
    {
        // Assume neither int is null. Real code should
        // probably be more robust

        if (x.scoreChange < y.scoreChange)
        {
            return 1;
        }
        if (x.scoreChange > y.scoreChange)
        {
            return -1;
        }
        return 0;
    }
}