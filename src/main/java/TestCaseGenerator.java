import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Random;
import java.util.Set;

/**
 * Generate test cases for testing the functionality of a single linked list.
 * Created by Xing HU on 9/30/14.
 */
public class TestCaseGenerator {
    // current test case index
    private int tcIdx_;

    // random number generator
    private final Random rand_;
    // default ranges
    private int defRangeVal_;    // value range
    private int defRangeIdx_;    // index range
    private final int RANDOM_RANGE_ = -1;   // random range indicator

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

    private int genVal(int range) {
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

    private int genIdx(int range) {
        if (range == RANDOM_RANGE_)
            return rand_.nextInt(defRangeIdx_);
        else
            return rand_.nextInt(range);
    }

    private String genHeader() {
        // init value set
        valSet_ = new HashSet<Integer>();

        return "TESTCASE " + tcIdx_++ + "\n";
    }

    private String printList(LinkedList<Integer> list) {
        String tc = "print =";
        for (Integer elem : list)
            tc += " " + elem.toString();
        tc += "\n";

        return tc;
    }

    // Supported functionality:
    //   print
    //   insert(position, value)
    //   append(value)
    //   prepend(value)
    //   deleteElemAt(position)
    //   findElem(value)
    //   readElemAt(position)

    // Test Cases
    // 1. Operations
    // 1.1 print (empty list)
    public String genTC1dot1(String tc) {
        tc += "print =\n";

        return tc;
    }

    // 1.2 print (non-empty list)
//    public String genTC1dot2(String tc) {
//        tc = genTC1dot4(tc);
//
//        return tc;
//    }

    // 1.3 insert elements (empty or non-empty list)
    public String genTC1dot3(String tc) {
        // construct a linked list to generate result
        LinkedList<Integer> list = new LinkedList<>();

        int noe = genIdx(RANDOM_RANGE_) + 1; // number of elements
        for (int idx = 1; idx <= noe; idx++) {
            int position = genIdx(idx);
            int value = genVal(RANDOM_RANGE_);
            tc += "insert " + position + " " + value + "\n";

            list.add(position, value);
        }

        // print result
        tc += printList(list);

        return tc;
    }

    // 1.4 append elements (empty or non-empty list)
    public String genTC1dot4(String tc) {
        // construct a linked list to generate result
        LinkedList<Integer> list = new LinkedList<>();

        int noe = genIdx(RANDOM_RANGE_) + 1; // number of elements
        for (int idx = 1; idx <= noe; idx++) {
            int value = genVal(RANDOM_RANGE_);
            tc += "append " + value + "\n";

            list.addLast(value);
        }

        // print result
        tc += printList(list);

        return tc;
    }

    // 1.5 prepend elements (empty or non-empty list)
    public String genTC1dot5(String tc) {
        // construct a linked list to generate result
        LinkedList<Integer> list = new LinkedList<>();

        int noe = genIdx(RANDOM_RANGE_) + 1; // number of elements
        for (int idx = 1; idx <= noe; idx++) {
            int value = genVal(RANDOM_RANGE_);
            tc += "prepend " + value + "\n";

            list.addFirst(value);
        }

        // print result
        tc += printList(list);

        return tc;
    }

    // 1.6 delete elements (non-empty list)
    public String genTC1dot6(String tc) {
        // construct a linked list to generate result
        LinkedList<Integer> list = new LinkedList<>();

        // first create a list with elements
        int noe = genIdx(RANDOM_RANGE_) + 1; // number of elements
        for (int idx = 1; idx <= noe; idx++) {
            int value = genVal(RANDOM_RANGE_);
            tc += "append " + value + "\n";

            list.addLast(value);
        }

        // then delete some or all of the elements
        int noe2delete = genIdx(noe) + 1;
        for (int idx = noe2delete; idx > 0; idx--) {
            int position = genIdx(idx);
            tc += "deleteElemAt " + position + "\n";

            list.remove(position);
        }

        // print result
        tc += printList(list);

        return tc;
    }

    // 1.7 find elements (non-empty list)
    public String genTC1dot7(String tc) {
        // construct a linked list to generate result
        LinkedList<Integer> list = new LinkedList<>();

        // first create a list with elements
        int noe = genIdx(RANDOM_RANGE_) + 1; // number of elements
        Set<Integer> values = new HashSet<Integer>();
        for (int idx = 1; idx <= noe; idx++) {
            int value = genVal(RANDOM_RANGE_);
            tc += "append " + value + "\n";
            values.add(value);

            list.addLast(value);
        }

        // then find elements
        for (Integer value : values)
            tc += "findElem " + value.toString() + " = " + list.indexOf(value) + "\n";

        // print result
        tc += printList(list);

        return tc;
    }

    // 1.8 read elements (non-empty list)
    public String genTC1dot8(String tc) {
        // construct a linked list to generate result
        LinkedList<Integer> list = new LinkedList<>();

        // first create a list with elements
        int noe = genIdx(RANDOM_RANGE_) + 1; // number of elements
        for (int idx = 1; idx <= noe; idx++) {
            int value = genVal(RANDOM_RANGE_);
            tc += "append " + value + "\n";

            list.addLast(value);
        }

        // then read some or all of the elements
        int noe2delete = genIdx(noe) + 1;
        for (int idx = noe2delete; idx > 0; idx--) {
            int position = genIdx(idx);
            tc += "readElemAt " + position + " = " + list.get(position) + "\n";
        }

        // print result
        tc += printList(list);

        return tc;
    }


    // 2. Abnormal cases
    // 2.1 delete elements (empty list)
    public String genTC2dot1(String tc) {
        int noe2delete = genIdx(RANDOM_RANGE_) + 1;
        for (int idx = noe2delete; idx > 0; idx--) {
            int position = genIdx(idx);
            tc += "deleteElemAt " + position + "\n";
        }

        return tc;
    }

    // 2.2 delete out-of-boundary elements
    public String genTC2dot2(String tc) {
        // first create a list with elements
        int noe = genIdx(RANDOM_RANGE_) + 1; // number of elements
        for (int idx = 1; idx <= noe; idx++) {
            int value = genVal(RANDOM_RANGE_);
            tc += "append " + value + "\n";
        }

        int noe2delete = genIdx(RANDOM_RANGE_) + 1;
        for (int idx = 1; idx <= noe2delete; idx++) {
            int position = genIdx(idx) + noe;
            tc += "deleteElemAt " + position + "\n";
        }

        return tc;
    }

    // 2.3 find elements (empty list)
    public String genTC2dot3(String tc) {
        int noe = genIdx(RANDOM_RANGE_) + 1;
        for (int idx = 1; idx <= noe; idx++) {
            int value = genVal(RANDOM_RANGE_);
            tc += "findElem " + value + "\n";
        }

        return tc;
    }


    // 2.4 find non-existing elements
    public String genTC2dot4(String tc) {
        // first create a list with elements
        int noe = genIdx(RANDOM_RANGE_) + 1; // number of elements
        Set<Integer> values = new HashSet<Integer>();
        for (int idx = 1; idx <= noe; idx++) {
            int value = genVal(RANDOM_RANGE_);
            tc += "append " + value + "\n";
            values.add(value);
        }

        // find non-existing elements
        int noe2find = genIdx(RANDOM_RANGE_) + 1;
        for (int idx = 1; idx <= noe2find; idx++) {
            int value = genVal(5 * defRangeVal_);
            if (values.contains(value)) {
                idx--;
                continue;
            }
            tc += "findElem " + value + "\n";
        }

        return tc;
    }

    // 2.5 read elements (empty list)
    public String genTC2dot5(String tc) {
        int noe = genIdx(RANDOM_RANGE_) + 1;
        for (int idx = 1; idx <= noe; idx++) {
            int position = genVal(RANDOM_RANGE_);
            tc += "readElemAt " + position + "\n";
        }

        return tc;
    }

    // 2.6 read out-of-boundary elements
    public String genTC2dot6(String tc) {
        // first create a list with elements
        int noe = genIdx(RANDOM_RANGE_) + 1; // number of elements
        for (int idx = 1; idx <= noe; idx++) {
            int value = genVal(RANDOM_RANGE_);
            tc += "append " + value + "\n";
        }

        int noe2delete = genIdx(RANDOM_RANGE_) + 1;
        for (int idx = 1; idx <= noe2delete; idx++) {
            int position = genIdx(idx) + noe;
            tc += "readElemAt " + position + "\n";
        }

        return tc;
    }





    public static void main (String[] args) throws IOException {
        FileWriter fw = new FileWriter("test.txt");

        TestCaseGenerator tcg = new TestCaseGenerator();
        String tc = "";
        tc += tcg.genHeader();
        tc = tcg.genTC1dot1(tc);

//        tc += tcg.genHeader();
//        tc = tcg.genTC1dot2(tc);

        tc += tcg.genHeader();
        tc = tcg.genTC1dot3(tc);

        tc += tcg.genHeader();
        tc = tcg.genTC1dot4(tc);

        tc += tcg.genHeader();
        tc = tcg.genTC1dot5(tc);

        tc += tcg.genHeader();
        tc = tcg.genTC1dot6(tc);

        tc += tcg.genHeader();
        tc = tcg.genTC1dot7(tc);

        tc += tcg.genHeader();
        tc = tcg.genTC1dot8(tc);
//
//
//        tc += tcg.genHeader();
//        tc = tcg.genTC2dot1(tc);
//
//        tc += tcg.genHeader();
//        tc = tcg.genTC2dot2(tc);
//
//        tc += tcg.genHeader();
//        tc = tcg.genTC2dot3(tc);
//
//        tc += tcg.genHeader();
//        tc = tcg.genTC2dot4(tc);
//
//        tc += tcg.genHeader();
//        tc = tcg.genTC2dot5(tc);
//
//        tc += tcg.genHeader();
//        tc = tcg.genTC2dot6(tc);

        try (BufferedWriter bw = new BufferedWriter(fw)) {
            bw.write(tc);
        }
        System.out.println(tc);


//        LinkedList<Integer> list = new LinkedList<>();
//        list.add(0, 11);
//        list.add(13);
//        list.addFirst(10);
//        list.addLast(15);
//        System.out.println(list.peek());
    }
}
