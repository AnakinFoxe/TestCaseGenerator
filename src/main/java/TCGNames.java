import util.HotPotato;
import util.Queue;

import java.io.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;

/**
 * Created by Xing HU on 11/13/14.
 */
public class TCGNames {
    private Random RAND_;

    public TCGNames() {
        RAND_ = new Random();
    }

    private List<String> readNames(String path) throws IOException {
        List<String> names = new ArrayList<>();

        FileReader fr = new FileReader(path);
        try (BufferedReader br = new BufferedReader(fr)) {
            String name;
            while ((name = br.readLine()) != null) {
                name = name.trim().replaceAll("[^a-zA-Z]", "");
                if (name.length() > 0)
                    names.add(name);
            }
        }

        return names;
    }

    private String pickName(List<String> names) {
        int pos = RAND_.nextInt(names.size());
        return names.get(pos);
    }

    private String genQuestion(List<String> names) {
        HashSet<String> usedNames = new HashSet<>();
        int size = RAND_.nextInt(10) + 1;
        StringBuilder question = new StringBuilder();
        Queue<String> questionNames = new Queue<>();

        // add names
        for (int idx = 0; idx < size; ++idx) {
            String name = pickName(names);
            if (!usedNames.contains(name)) {
                question.append(name);
                question.append(" ");

                questionNames.enqueue(name);

                usedNames.add(name);
            } else
                --idx;

        }

        // add a random number
        int randNum = RAND_.nextInt(30);
        question.append(randNum);

        // add assignment
        question.append(" = ");

        // add answer
        HotPotato hp = new HotPotato();
        String answer = hp.play(questionNames, randNum);
        question.append(answer);

        // EOL
        question.append("\n");

        return question.toString();
    }

    public void run() throws IOException {
        List<String> names = readNames("src/main/resources/names.txt");
        FileWriter fw = new FileWriter("test.txt");

        try (BufferedWriter bw = new BufferedWriter(fw)) {

            for (int idx = 0; idx < 50; ++idx) {
                String question = genQuestion(names);
                System.out.print(question);
                bw.write(question);
            }
        }

    }

    public static void main(String[] args) throws IOException {
        TCGNames tcg = new TCGNames();
        tcg.run();
    }
}
