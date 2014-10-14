import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Stack;

/**
 * Created by Xing HU on 10/11/14.
 */
public class TCGStack extends TestCaseGenerator {

    private String printStack(Stack<Integer> stack) {
        String tc = "print =";
        for (Integer elem : stack)
            tc += " " + elem.toString();
        tc += "\n";

        return tc;
    }

    // Supported functionality:
    //   print
    //   peek
    //   push(value)
    //   pop

    // Test Cases
    // 1. Operations
    // 1.1 print (empty stack)
    public String genTC1dot1(String tc) {
        tc += "print =\n";

        return tc;
    }

    // 1.2 peek (non-empty stack)
    public String genTC1dot2(String tc) {
        // construct a stack to generate result
        Stack<Integer> stack = new Stack<>();

        int noe = genIdx(RANDOM_RANGE_) + 1; // number of elements
        for (int idx = 1; idx <= noe; idx++) {
            int value = genVal(RANDOM_RANGE_);
            tc += "push " + value + "\n";

            stack.push(value);
        }

        // peek
        tc += "peek = " + stack.peek().toString() + "\n";

        // print result
        tc += printStack(stack);

        return tc;
    }

    // 1.3 push (empty or non-empty stack)
    public String genTC1dot3(String tc) {
        // construct a stack to generate result
        Stack<Integer> stack = new Stack<>();

        int noe = genIdx(RANDOM_RANGE_) + 1; // number of elements
        for (int idx = 1; idx <= noe; idx++) {
            int value = genVal(RANDOM_RANGE_);
            tc += "push " + value + "\n";

            stack.push(value);
        }

        // print result
        tc += printStack(stack);

        return tc;
    }

    // 1.4 pop (non-empty stack)
    public String genTC1dot4(String tc) {
        // construct a stack to generate result
        Stack<Integer> stack = new Stack<>();

        // first create a stack with elements
        int noe = genIdx(RANDOM_RANGE_) + 1; // number of elements
        for (int idx = 1; idx <= noe; idx++) {
            int value = genVal(RANDOM_RANGE_);
            tc += "push " + value + "\n";

            stack.push(value);
        }

        // then pop
        tc += "pop = " + stack.pop().toString() + "\n";

        // print result
        tc += printStack(stack);

        return tc;
    }

    // 2. Order
    // 2.1 push more than pop (result in non-empty stack)
    public String genTC2dot1(String tc) {
        // construct a stack to generate result
        Stack<Integer> stack = new Stack<>();

        // first create a stack with elements
        int noe = genIdx(RANDOM_RANGE_) + 1; // number of elements
        for (int idx = 1; idx <= noe; idx++) {
            int value = genVal(RANDOM_RANGE_);
            tc += "push " + value + "\n";

            stack.push(value);
        }

        // then pop some
        int noe2delete = genIdx(noe);
        for (int idx = noe2delete; idx > 0; idx--)
            tc += "pop = " + stack.pop().toString() + "\n";


        // print result
        tc += printStack(stack);

        return tc;
    }

    // 2.2 push the same with pop (result in empty stack)
    public String genTC2dot2(String tc) {
        // construct a stack to generate result
        Stack<Integer> stack = new Stack<>();

        // first create a stack with elements
        int noe = genIdx(RANDOM_RANGE_) + 1; // number of elements
        for (int idx = 1; idx <= noe; idx++) {
            int value = genVal(RANDOM_RANGE_);
            tc += "push " + value + "\n";

            stack.push(value);
        }

        // then pop all of them
        int noe2delete = noe;
        for (int idx = noe2delete; idx > 0; idx--)
            tc += "pop = " + stack.pop().toString() + "\n";


        // print result
        tc += printStack(stack);

        return tc;
    }

    // 3. Abnormal
    // 3.1 peek from empty stack
    public String genTC3dot1(String tc) {
        tc += "peek = \n";

        return tc;
    }

    // 3.2 pop from empty stack
    public String genTC3dot2(String tc) {
        // construct a stack to generate result
        tc += "pop = \n";;

        return tc;
    }

    // 3.3 pop more than push
    public String genTC3dot3(String tc) {
        // construct a stack to generate result
        Stack<Integer> stack = new Stack<>();

        // first create a stack with elements
        int noe = genIdx(RANDOM_RANGE_) + 1; // number of elements
        for (int idx = 1; idx <= noe; idx++) {
            int value = genVal(RANDOM_RANGE_);
            tc += "push " + value + "\n";

            stack.push(value);
        }

        // then pop all of them
        int noe2delete = noe + genIdx(RANDOM_RANGE_) + 1;
        for (int idx = 0; idx < noe2delete; ++idx) {
            if (idx < noe)
                tc += "pop = " + stack.pop().toString() + "\n";
            else
                tc += "pop = \n";
        }

        // print result
        tc += printStack(stack);

        return tc;
    }

    // 3.4 push to full stack (not for linked list implementation)


    public static void main(String[] args) throws IOException {
        TCGStack tcg = new TCGStack();
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
