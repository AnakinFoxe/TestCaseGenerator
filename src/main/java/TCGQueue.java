import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

/**
 * Created by Xing HU on 11/8/14.
 */
public class TCGQueue extends TestCaseGenerator {

    private String printQueue(ArrayDeque<Integer> queue) {
        String tc = "print =";
        Iterator it = queue.iterator();
        while (it.hasNext()) {
            tc += " " + it.next().toString();
        }
        tc += "\n";

        return tc;
    }

    // Supported functionality:
    //   print
    //   peek
    //   enqueue(value)
    //   dequeue

    // Test Cases
    // 1. Operations
    // 1.1 print (empty queue)
    public String genTC1dot1(String tc) {
        tc += "print =\n";

        return tc;
    }

    // 1.2 peek (non-empty queue)
    public String genTC1dot2(String tc) {
        // construct a queue to generate result
        ArrayDeque<Integer> queue = new ArrayDeque<>();

        int noe = genIdx(RANDOM_RANGE_) + 1; // number of elements
        for (int idx = 1; idx <= noe; idx++) {
            int value = genVal(RANDOM_RANGE_);
            tc += "enqueue " + value + "\n";

            queue.addLast(value);
        }

        // peek
        tc += "peek = " + queue.peekFirst().toString() + "\n";

        // print result
        tc += printQueue(queue);

        return tc;
    }

    // 1.3 enqueue (empty or non-empty queue)
    public String genTC1dot3(String tc) {
        // construct a queue to generate result
        ArrayDeque<Integer> queue = new ArrayDeque<>();

        int noe = genIdx(RANDOM_RANGE_) + 1; // number of elements
        for (int idx = 1; idx <= noe; idx++) {
            int value = genVal(RANDOM_RANGE_);
            tc += "enqueue " + value + "\n";

            queue.addLast(value);
        }

        // print result
        tc += printQueue(queue);

        return tc;
    }

    // 1.4 dequeue (non-empty queue)
    public String genTC1dot4(String tc) {
        // construct a queue to generate result
        ArrayDeque<Integer> queue = new ArrayDeque<>();

        // first create a queue with elements
        int noe = genIdx(RANDOM_RANGE_) + 1; // number of elements
        for (int idx = 1; idx <= noe; idx++) {
            int value = genVal(RANDOM_RANGE_);
            tc += "enqueue " + value + "\n";

            queue.addLast(value);
        }

        // then dequeue
        tc += "dequeue = " + queue.pollFirst().toString() + "\n";

        // print result
        tc += printQueue(queue);

        return tc;
    }

    // 2. Order
    // 2.1 enqueue more than dequeue (result in non-empty queue)
    public String genTC2dot1(String tc) {
        // construct a queue to generate result
        ArrayDeque<Integer> queue = new ArrayDeque<>();

        // first create a queue with elements
        int noe = genIdx(RANDOM_RANGE_) + 1; // number of elements
        for (int idx = 1; idx <= noe; idx++) {
            int value = genVal(RANDOM_RANGE_);
            tc += "enqueue " + value + "\n";

            queue.addLast(value);
        }

        // then dequeue some
        int noe2delete = genIdx(noe);
        for (int idx = noe2delete; idx > 0; idx--)
            tc += "dequeue = " + queue.pollFirst().toString() + "\n";


        // print result
        tc += printQueue(queue);

        return tc;
    }

    // 2.2 enqueue the same with dequeue (result in empty queue)
    public String genTC2dot2(String tc) {
        // construct a queue to generate result
        ArrayDeque<Integer> queue = new ArrayDeque<>();

        // first create a queue with elements
        int noe = genIdx(RANDOM_RANGE_) + 1; // number of elements
        for (int idx = 1; idx <= noe; idx++) {
            int value = genVal(RANDOM_RANGE_);
            tc += "enqueue " + value + "\n";

            queue.addLast(value);
        }

        // then dequeue all of them
        int noe2delete = noe;
        for (int idx = noe2delete; idx > 0; idx--)
            tc += "dequeue = " + queue.pollFirst().toString() + "\n";


        // print result
        tc += printQueue(queue);

        return tc;
    }

    // 3. Abnormal
    // 3.1 peek from empty queue
    public String genTC3dot1(String tc) {
        tc += "peek = \n";

        return tc;
    }

    // 3.2 dequeue from empty queue
    public String genTC3dot2(String tc) {
        // construct a queue to generate result
        tc += "dequeue = \n";;

        return tc;
    }

    // 3.3 dequeue more than enqueue
    public String genTC3dot3(String tc) {
        // construct a queue to generate result
        ArrayDeque<Integer> queue = new ArrayDeque<>();

        // first create a queue with elements
        int noe = genIdx(RANDOM_RANGE_) + 1; // number of elements
        for (int idx = 1; idx <= noe; idx++) {
            int value = genVal(RANDOM_RANGE_);
            tc += "enqueue " + value + "\n";

            queue.addLast(value);
        }

        // then dequeue all of them
        int noe2delete = noe + genIdx(RANDOM_RANGE_) + 1;
        for (int idx = 0; idx < noe2delete; ++idx) {
            if (idx < noe)
                tc += "dequeue = " + queue.pollFirst().toString() + "\n";
            else
                tc += "dequeue = \n";
        }

        // print result
        tc += printQueue(queue);

        return tc;
    }

    // 3.4 enqueue to full queue (not for linked list implementation)


    public static void main(String[] args) throws IOException {
        TCGQueue tcg = new TCGQueue();
        String tc = "";

        // for text.txt, include all normal cases
        FileWriter fw = new FileWriter("test.txt");

        tc += tcg.genHeader();
        tc = tcg.genTC1dot1(tc);

        for (int repeat = 0; repeat < 10; ++repeat) {
            tc += tcg.genHeader();
            tc = tcg.genTC1dot2(tc);

            tc += tcg.genHeader();
            tc = tcg.genTC1dot3(tc);

            tc += tcg.genHeader();
            tc = tcg.genTC1dot4(tc);

            tc += tcg.genHeader();
            tc = tcg.genTC2dot1(tc);

            tc += tcg.genHeader();
            tc = tcg.genTC2dot2(tc);
        }

        try (BufferedWriter bw = new BufferedWriter(fw)) {
            bw.write(tc);
        }
        System.out.println(tc);


        // for eval.txt, include all kinds of cases
        fw = new FileWriter("eval.txt");
        tcg.resetTcIdx();
        tc = "";

        tc += tcg.genHeader();
        tc = tcg.genTC1dot1(tc);

        tc += tcg.genHeader();
        tc = tcg.genTC3dot1(tc);

        tc += tcg.genHeader();
        tc = tcg.genTC3dot2(tc);

        for (int repeat = 0; repeat < 8; ++repeat) {
            tc += tcg.genHeader();
            tc = tcg.genTC2dot1(tc);

            tc += tcg.genHeader();
            tc = tcg.genTC2dot2(tc);

            tc += tcg.genHeader();
            tc = tcg.genTC1dot2(tc);

            tc += tcg.genHeader();
            tc = tcg.genTC1dot3(tc);

            tc += tcg.genHeader();
            tc = tcg.genTC1dot4(tc);

            tc += tcg.genHeader();
            tc = tcg.genTC3dot3(tc);
        }

        try (BufferedWriter bw = new BufferedWriter(fw)) {
            bw.write(tc);
        }
        System.out.println(tc);

    }
}
