package readability;

import readability.io.ConsoleInteraction;
import readability.io.FileInteraction;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        if (args.length == 0 || !FileInteraction.isReadable(args[0])) {
            ConsoleInteraction.print("No valid file was provided");
            return;
        }

        Text text;

        try {
            text = new Text(FileInteraction.readFile(args[0]));
        } catch (IOException e) {
            ConsoleInteraction.print(e.getMessage());
            return;
        }

        if (text.sentenceCount() == 0) {
            ConsoleInteraction.print("The file contains no text.");
            return;
        }

        ConsoleInteraction.print(text.report());

        ConsoleInteraction.print("Enter the score you want to calculate (ARI, FK, SMOG, CL, all):");
        List<ReadabilityIndex> scoreList = select(ConsoleInteraction.getLine().trim());

        if (scoreList.isEmpty()) {
            ConsoleInteraction.print("Unknown score.");
            return;
        }

        List<IndexScore> results = new ArrayList<>();

        for (ReadabilityIndex index : scoreList) {
            results.add(new IndexScore(index.score(text), index));
        }

        ConsoleInteraction.printEmptyLine();

        for (IndexScore result : results) {
            ConsoleInteraction.print(result.format());
        }

        double totalAge = 0.0;

        for (IndexScore result : results) {
            totalAge += result.maxAge();
        }

        ConsoleInteraction.printEmptyLine();
        ConsoleInteraction.print(String.format("This text should be understood in average by %.2f-year-olds.", totalAge / results.size()));
    }

    private static List<ReadabilityIndex> select(String input) {
        if (input.equalsIgnoreCase("all")) {
            return List.of(ReadabilityIndex.values());
        }

        for (ReadabilityIndex index : ReadabilityIndex.values()) {
            if (index.name().equalsIgnoreCase(input)) {
                return List.of(index);
            }
        }

        return List.of();
    }
}