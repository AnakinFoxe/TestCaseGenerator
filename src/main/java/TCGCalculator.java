import util.Calculator;

import java.util.BitSet;
import java.util.HashSet;

/**
 * Created by Xing HU on 10/13/14.
 */
public class TCGCalculator extends TestCaseGenerator {

    private final int BEGIN_ = 0;
    private final int LEFT_BRACKET_ = 1;
    private final int RIGHT_BRACKET_ = 2;
    private final int NUMBER_ = 3;
    private final int OPERATOR_ = 4;
    private final int ASSIGNMENT_ = 5;
    private final int ERROR_ = -1;

    private final int MAGIC_NUM_ = 24;

    private int getDice(int diceNum) {
        int value = genVal(MAGIC_NUM_);

        int step = MAGIC_NUM_ / diceNum;
        int step1 = 0;
        int step2 = step;
        while (diceNum > 0) {
            --diceNum;

            if ((value >= step1) && (value < step2))
                return diceNum;

            step1 = step2;
            step2 = step;
        }

        return diceNum;
    }

    private String genOperator() {
        String operator;
        switch (genVal(4)) {
            case 0:
                operator = "+";
                break;
            case 1:
                operator = "-";
                break;
            case 2:
                operator = "*";
                break;
            case 3:
                operator = "/";
                break;
            default:
                operator = "";
                break;
        }

        return operator;
    }


    public void genTC(String tc) {
        int numLeftBrackets = 0;
        int prevSymbol = BEGIN_;
        int dice = 0;

        String formula = "";

        Calculator cal = new Calculator();

        while (prevSymbol != ERROR_) {
            switch (prevSymbol) {
                case BEGIN_:
                    // same as left bracket
                case LEFT_BRACKET_:
                    if (getDice(2) > 0) {
                        prevSymbol = LEFT_BRACKET_;
                        formula += "(";
                        ++numLeftBrackets;
                    } else {
                        prevSymbol = NUMBER_;
                        formula += Integer.valueOf(genVal(RANDOM_RANGE_));
                    }
                    break;
                case RIGHT_BRACKET_:
                    dice = getDice(3);
                    switch (dice) {
                        case 0:
                            if (numLeftBrackets > 0) {
                                prevSymbol = RIGHT_BRACKET_;
                                formula += ")";
                                --numLeftBrackets;
                            } else {
                                prevSymbol = ASSIGNMENT_;
                                formula += "=";
                            }
                            break;
                        case 1:
                            prevSymbol = NUMBER_;
                            formula += Integer.valueOf(genVal(RANDOM_RANGE_));
                            break;
                        case 2:
                            prevSymbol = OPERATOR_;
                            formula += genOperator();
                            break;
                        default:
                            prevSymbol = ERROR_;
                            break;
                    }
                    break;
                case NUMBER_:
                    if (getDice(2) > 0) {
                        if (numLeftBrackets > 0) {
                            prevSymbol = RIGHT_BRACKET_;
                            formula += ")";
                            --numLeftBrackets;
                        } else {
                            prevSymbol = ASSIGNMENT_;
                            formula += "=";
                        }
                    } else {
                        prevSymbol = OPERATOR_;
                        formula += genOperator();
                        break;
                    }
                    break;
                case OPERATOR_:
                    if (getDice(2) > 0) {
                        prevSymbol = LEFT_BRACKET_;
                        formula += "(";
                        ++numLeftBrackets;
                    } else {
                        prevSymbol = NUMBER_;
                        formula += Integer.valueOf(genVal(RANDOM_RANGE_));
                    }
                    break;
                case ASSIGNMENT_:
//                    double answer = cal.solve(formula);
                    tc += formula;
                    System.out.println(tc);
                    return;
                default:
                    System.out.println("Something wrong:\n" + formula);
                    break;
            }
        }
    }

    public static void main(String[] args) {

        TCGCalculator tcg = new TCGCalculator();
        tcg.setValDistinct_(false);

        for (int i = 0; i < 10; ++i) {
            String tc = i + ": ";
            tcg.genTC(tc);
        }
    }

}
