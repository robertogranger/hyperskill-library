package numbers;

import numbers.io.ConsoleInteraction;

import java.util.*;

public class Main {
    private static final String INSTRUCTIONS = """
        Supported requests:
        - enter a natural number to know its properties;
        - enter two natural numbers to obtain the properties of the list:
          * the first parameter represents a starting number;
          * the second parameter shows how many consecutive numbers are to be processed;
        - two natural numbers and properties to search for;
        - a property preceded by minus must not be present in numbers;
        - separate the parameters with one space;
        - enter 0 to exit.""";

    public static void main(String[] args) {
        ConsoleInteraction.print("Welcome to Amazing Numbers!");
        ConsoleInteraction.printEmptyLine();

        ConsoleInteraction.print(INSTRUCTIONS);
        ConsoleInteraction.printEmptyLine();

        while (true) {
            ConsoleInteraction.printPrompt("Enter a request: ");

            Request request = parseRequest(ConsoleInteraction.getLine());
            ConsoleInteraction.printEmptyLine();

            switch (request.type()) {
                case EXIT -> {
                    ConsoleInteraction.print("Goodbye!");
                    return;
                }
                case INSTRUCTIONS -> {
                    ConsoleInteraction.print(INSTRUCTIONS);
                    ConsoleInteraction.printEmptyLine();
                }
                case INVALID_FIRST -> {
                    ConsoleInteraction.print("The first parameter should be a natural number or zero.");
                    ConsoleInteraction.printEmptyLine();
                }
                case INVALID_SECOND -> {
                    ConsoleInteraction.print("The second parameter should be a natural number.");
                    ConsoleInteraction.printEmptyLine();
                }
                case INVALID_PROPERTIES -> {
                    List<String> texts = request.invalidTexts();

                    if (texts.size() == 1) {
                        ConsoleInteraction.print("The property " + texts + " is wrong.");
                    } else {
                        ConsoleInteraction.print("The properties " + texts + " are wrong.");
                    }

                    ConsoleInteraction.print("Available properties:");
                    ConsoleInteraction.print(Arrays.toString(Property.values()));
                    ConsoleInteraction.printEmptyLine();
                }
                case EXCLUSIVE_PROPERTIES -> {
                    ConsoleInteraction.print("The request contains mutually exclusive properties: " + request.criteria());
                    ConsoleInteraction.print("There are no numbers with these properties.");
                    ConsoleInteraction.printEmptyLine();
                }
                case SINGLE -> {
                    ConsoleInteraction.print(new NaturalNumber(request.first()).describe());
                    ConsoleInteraction.printEmptyLine();
                }
                case LIST -> {
                    for (long offset = 0; offset < request.count(); offset++) {
                        ConsoleInteraction.print(new NaturalNumber(request.first() + offset).describeInline());
                    }

                    ConsoleInteraction.printEmptyLine();
                }
                case SEARCH -> {
                    printMatches(request);
                    ConsoleInteraction.printEmptyLine();
                }
            }
        }
    }

    private static void printMatches(Request request) {
        long current = request.first();
        long found = 0;

        while (found < request.count()) {
            NaturalNumber number = new NaturalNumber(current);

            if (number.matchesAll(request.criteria())) {
                ConsoleInteraction.print(number.describeInline());
                found++;
            }

            // Stop before current++ would wrap around past Long.MAX_VALUE.
            if (current == Long.MAX_VALUE) {
                return;
            }

            current++;
        }
    }

    private static OptionalLong parseLong(String text) {
        Scanner scanner = new Scanner(text);
        if (!scanner.hasNextLong()) {
            return OptionalLong.empty();
        }
        return OptionalLong.of(scanner.nextLong());
    }

    private static Request parseRequest(String userInput) {
        String trimmedInput = userInput.trim();

        if (trimmedInput.isEmpty()) {
            return new Request(RequestType.INSTRUCTIONS, 0, 0);
        }

        if (trimmedInput.equals("0")) {
            return new Request(RequestType.EXIT, 0, 0);
        }

        String[] tokens = trimmedInput.split("\\s+");
        OptionalLong first = parseLong(tokens[0]);

        if (first.isEmpty() || first.getAsLong() <= 0) {
            return new Request(RequestType.INVALID_FIRST, 0, 0);
        }

        if (tokens.length == 1) {
            return new Request(RequestType.SINGLE, first.getAsLong(), 0);
        }

        OptionalLong count = parseLong(tokens[1]);

        if (count.isEmpty() || count.getAsLong() <= 0) {
            return new Request(RequestType.INVALID_SECOND, 0, 0);
        }

        if (tokens.length == 2) {
            if (count.getAsLong() - 1 > Long.MAX_VALUE - first.getAsLong()) {
                return new Request(RequestType.INVALID_SECOND, 0, 0);
            }

            return new Request(RequestType.LIST, first.getAsLong(), count.getAsLong());
        }

        return parsePropertyRequest(first.getAsLong(), count.getAsLong(), tokens);
    }

    private static Request parsePropertyRequest(long first, long count, String[] tokens) {
        List<String> wrongTexts = new ArrayList<>();
        Set<Criterion> criteria = new LinkedHashSet<>(); // a Set ignores duplicates

        for (int i = 2; i < tokens.length; i++) {
            String token = tokens[i];
            boolean excluded = token.startsWith("-");
            String name = excluded ? token.substring(1) : token;

            Optional<Property> property = Property.fromText(name);

            if (property.isPresent()) {
                criteria.add(new Criterion(property.get(), excluded));
            } else {
                wrongTexts.add(token.toUpperCase());
            }
        }

        if (!wrongTexts.isEmpty()) {
            return new Request(RequestType.INVALID_PROPERTIES, 0, 0, List.of(), wrongTexts);
        }

        Optional<List<Criterion>> conflict = findConflict(criteria);

        if (conflict.isPresent()) {
            return new Request(RequestType.EXCLUSIVE_PROPERTIES, 0, 0, conflict.get(), List.of());
        }

        return new Request(RequestType.SEARCH, first, count, List.copyOf(criteria), List.of());
    }

    private static Optional<List<Criterion>> findConflict(Set<Criterion> criteria) {
        for (Criterion criterion : criteria) {
            Property property = criterion.property();

            // A property together with its own negation, like gapful -gapful.
            Criterion negation = new Criterion(property, !criterion.excluded());

            if (criteria.contains(negation)) {
                return Optional.of(List.of(criterion, negation));
            }

            Optional<Property> opposite = property.opposite();

            if (opposite.isEmpty()) {
                continue;
            }

            Criterion pairedWith = new Criterion(opposite.get(), criterion.excluded());

            if (!criteria.contains(pairedWith)) {
                continue;
            }

            // Required pair (even odd): never satisfiable. Excluded pair (-even -odd):
            // only unsatisfiable when every number has one of the two.
            if (!criterion.excluded() || property.splitsAllNumbers()) {
                return Optional.of(List.of(criterion, pairedWith));
            }
        }

        return Optional.empty();
    }
}