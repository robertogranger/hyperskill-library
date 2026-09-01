import java.util.Scanner;

public class CoffeeMachine {

    public static void main(String[] args) {
        ConsoleInteraction consoleInteraction = new ConsoleInteraction();
        Machine coffeMachine = new Machine();
        consoleInteraction.displayMenu(coffeMachine);
    }

    public enum Coffee {
        ESPRESSO(250, 0, 16, 4),
        LATTE(350, 75, 20, 7),
        CAPPUCCINO(200, 100, 12, 6);

        private final int waterAmount;
        private final int milkAmount;
        private final int beansAmount;
        private final int price;

        Coffee(int waterAmount, int milkAmount, int beansAmount, int price) {
            this.waterAmount = waterAmount;
            this.milkAmount = milkAmount;
            this.beansAmount = beansAmount;
            this.price = price;
        }

        public int getWaterAmount() {
            return waterAmount;
        }

        public int getMilkAmount() {
            return milkAmount;
        }

        public int getBeansAmount() {
            return beansAmount;
        }

        public int getPrice() {
            return price;
        }
    }

    public enum MachineActions {
        BUY, FILL, TAKE, REMAINING, CLEAN, EXIT
    }

    public static class Machine {
        private int availableWater = 400;
        private int availableMilk = 540;
        private int availableBeans = 120;
        private int availableCups = 9;
        private int availableMoney = 550;
        private boolean needsCleaning = false;
        private static final int CLEANING_THRESHOLD = 10;
        private int cupsMadeSinceCleaning = 0;

        private void displayStatistics() {
            String statisticsMessage = """
                    The coffee machine has:
                    %d ml of water
                    %d ml of milk
                    %d g of coffee beans
                    %d disposable cups
                    $%d of money%n%n""";

            System.out.printf(statisticsMessage, availableWater,availableMilk, availableBeans, availableCups, availableMoney);
        }

        private void makeCoffeeCup(Coffee coffee) {
            if (canMakeCoffee(coffee)) {
                System.out.println("I have enough resources, making you a coffee!");

                availableWater -= coffee.getWaterAmount();
                availableMilk -= coffee.getMilkAmount();
                availableBeans -= coffee.getBeansAmount();
                availableCups--;
                availableMoney += coffee.getPrice();
                cupsMadeSinceCleaning++;

                if (cupsMadeSinceCleaning >= CLEANING_THRESHOLD) needsCleaning = true;
            }
        }

        private boolean canMakeCoffee(Coffee coffee) {
            if (availableWater < coffee.getWaterAmount()) {
                System.out.println("Sorry, not enough water!");
                return false;
            } else if (availableMilk < coffee.getMilkAmount()) {
                System.out.println("Sorry, not enough milk!");
                return false;
            } else if (availableBeans < coffee.getBeansAmount()) {
                System.out.println("Sorry, not enough coffee beans!");
                return false;
            } else if (availableCups < 1) {
                System.out.println("Sorry, not enough cups!");
                return false;
            }

            return true;
        }

        private void addWater(int amount) {
            availableWater += amount;
        }

        private void addMilk(int amount) {
            availableMilk += amount;
        }

        private void addBeans(int amount) {
            availableBeans += amount;
        }

        private void addCups(int amount) {
            availableCups += amount;
        }

        private int takeMoney() {
            int removedMoney = availableMoney;
            availableMoney = 0;
            return removedMoney;
        }

        private boolean getMachineCleaningStatus() {
            return needsCleaning;
        }

        private void clean() {
            needsCleaning = false;
            cupsMadeSinceCleaning = 0;
            System.out.println("I have been cleaned!");
        }
    }

    public static class ConsoleInteraction {
        private final Scanner scanner = new Scanner(System.in);

        private int validateIntegerInput(String message) {
            int userInput;

            while (true) {
                try {
                    System.out.println(message);
                    userInput = scanner.nextInt();
                    break;
                } catch (Exception e) {
                    System.out.println("Wrong input!");
                    scanner.next();
                }
            }

            return userInput;
        }

        private MachineActions askMachineAction() {
            MachineActions action;

            while (true) {
                System.out.println("Write action (buy, fill, take, clean, remaining, exit):");

                try {
                    String userInput = scanner.next();
                    action = MachineActions.valueOf(userInput.toUpperCase());
                    break;
                } catch (IllegalArgumentException e) {
                    System.out.printf("Wrong input!%n");
                }
            }

            return action;
        }

        private void displayMenu(Machine machine) {
            whileLoop:
            while (true) {
                MachineActions action = askMachineAction();

                switch (action) {
                    case BUY -> {
                        if (machine.getMachineCleaningStatus()) {
                            System.out.println("I need cleaning!");
                        } else {
                            System.out.println();
                            String coffeeChoice = askCoffeeChoice();

                            if (!coffeeChoice.equalsIgnoreCase("back")) {
                                machine.makeCoffeeCup(Coffee.values()[Integer.parseInt(coffeeChoice) - 1]);
                            }

                            System.out.println();
                        }
                    }
                    case FILL -> {
                        System.out.println();
                        machine.addWater(validateIntegerInput("Write how many ml of water you want to add:"));
                        machine.addMilk(validateIntegerInput("Write how many ml of milk you want to add:"));
                        machine.addBeans(validateIntegerInput("Write how many grams of coffee beans you want to add:"));
                        machine.addCups(validateIntegerInput("Write how many disposable cups you want to add:"));
                        System.out.println();
                    }
                    case TAKE -> {
                        int moneyTaken = machine.takeMoney();
                        System.out.printf("I gave you $%d%n%n", moneyTaken);
                    }
                    case REMAINING -> machine.displayStatistics();
                    case CLEAN -> machine.clean();
                    case EXIT -> {
                        break whileLoop;
                    }
                }
            }
        }

        private String askCoffeeChoice() {
            while (true) {
                System.out.println("What do you want to buy? 1 - espresso, 2 - latte, 3 - cappuccino, back - to main menu:");
                String userInput = scanner.next();

                if (userInput.equalsIgnoreCase("back")) {
                    return userInput;
                }

                try {
                    int choice = Integer.parseInt(userInput);
                    if (choice >= 1 && choice <= 3) {
                        return userInput;
                    }
                } catch (NumberFormatException e) {
                    // falls through to loop again
                }

                System.out.println("Wrong input!");
            }
        }
    }
}