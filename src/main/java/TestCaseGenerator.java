import java.util.HashSet;
import java.util.Random;
import java.util.Set;

/**
 * Created by Xing HU on 10/11/14.
 */
public class TestCaseGenerator {
    // current test case index
    private int tcIdx_;

    // random number generator
    private final Random rand_;
    // default ranges
    protected int defRangeVal_;    // value range
    protected int defRangeIdx_;    // index range
    protected final int RANDOM_RANGE_ = -1;   // random range indicator

    // distinct flag
    private boolean isValDistinct_;
    private Set<Integer> valSet_;

    public TestCaseGenerator() {
        tcIdx_ = 1;  // starting from 1

        rand_ = new Random();
        defRangeVal_ = 30;
        defRangeIdx_ = 10;

        isValDistinct_ = true;
        valSet_ = new HashSet<Integer>();
    }

    public void resetTcIdx() {tcIdx_ = 1;}

    public int getDefRangeVal_() {
        return defRangeVal_;
    }

    public void setDefRangeVal_(int defRangeVal_) {
        this.defRangeVal_ = defRangeVal_;
    }

    public int getDefRangeIdx_() {
        return defRangeIdx_;
    }

    public void setDefRangeIdx_(int defRangeIdx_) {
        this.defRangeIdx_ = defRangeIdx_;
    }

    public boolean isValDistinct_() {
        return isValDistinct_;
    }

    public void setValDistinct_(boolean isValDistinct_) {
        this.isValDistinct_ = isValDistinct_;
    }

    protected int genVal(int range) {
        int value;

        while (true) {
            if (range == RANDOM_RANGE_)
                value = rand_.nextInt(defRangeVal_);
            else
                value = rand_.nextInt(range);

            if (isValDistinct_()) {
                if (valSet_.contains(value))
                    continue;
                else
                    valSet_.add(value);
            }

            break;
        }

        return value;
    }

    protected int genIdx(int range) {
        if (range == RANDOM_RANGE_)
            return rand_.nextInt(defRangeIdx_);
        else
            return rand_.nextInt(range);
    }

    public String genHeader() {
        // init value set
        valSet_ = new HashSet<Integer>();

        return "TESTCASE " + tcIdx_++ + "\n";
    }


}
