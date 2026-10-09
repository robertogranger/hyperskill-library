# Hyperskill Projects

This repository tracks my progress through the Hyperskill curriculum. Each subfolder is a standalone project completed as part of a Hyperskill track, kept here for reference and to show progression over time.

## About Hyperskill

[Hyperskill](https://hyperskill.org) is a project based learning platform. Instead of isolated exercises, each project builds a small working program in stages, with the full source kept at the end.

## Progress

| Project                                                       | Track       | Language | Status    | Notes                                                                                                        |
|----------------------------------------------------------------|-------------|----------|-----------|---------------------------------------------------------------------------------------------------------------|
| [Simple Bot](simple-bot/SimpleBot.java)                        | Java Basics | Java     | Completed | Console chatbot covering variables, methods, loops, and Scanner input                                         |
| [Zookeeper](zookeeper/Zookeeper.java)                           | Java Basics | Java     | Completed | Prints ASCII animal habitats by number using text blocks and a Scanner-driven input loop                      |
| [Pencil Game](pencil-game/PencilGame.java)                      | Java Basics | Java     | Completed | Two-player pencil-taking game against a computer opponent that plays an unbeatable strategy, with input validation |
| [Cinema Room Manager](cinema-room-manager/CinemaRoomManager.java) | Java Basics | Java     | Completed | Interactive seat-booking console app with a seat map, tiered ticket pricing, and purchase statistics          |
| [Coffee Machine](coffee-machine/CoffeeMachine.java)             | Java Basics | Java     | Completed | Simulates a coffee machine's resource management (water, milk, beans, cups, money) with buy/fill/take/clean actions and a self-cleaning threshold |
| [Tic-Tac-Toe](tic-tac-toe/TicTacToe.java)                       | Java Basics | Java     | Completed | Two-player console tic-tac-toe with move validation, turn switching, and win/draw detection across rows, columns, and diagonals |
| [Chuck Norris](chucknorris/Main.java)                           | Java Basics | Java     | Completed | Encodes and decodes text with a run-length-encoded binary cipher; organized into `cipher` and `io` packages with custom exception handling for invalid input |
| [Battleship](battleship/Main.java)                              | Java Basics | Java     | Completed | Two-player, pass-and-play Battleship with ship placement validation, fog-of-war rendering, and turn-based shooting; split into `field` and `io` packages |
| [Honest Calculator](honestcalculator/Main.java)                 | Java Basics | Java     | Completed | REPL calculator with a single-value memory slot (`M`), sarcastic commentary on lazy equations, and dedicated exceptions for bad operators and division by zero; split into `io` and `parsing` packages |
| [Bulls and Cows](bullscows/Main.java)                           | Java Basics | Java     | Completed | Code-breaking game against a randomly generated secret of unique symbols (up to 36: `0-9`, `a-z`), with configurable length, setup validation, and bull/cow grading via a `Grade` record |
| [Amazing Numbers](numbers/Main.java)                            | Java Basics | Java     | Completed | Number-property explorer for 12 properties (even, odd, buzz, duck, palindromic, gapful, spy, square, sunny, jumping, happy, sad) with list and search requests, `-property` negation, and detection of mutually exclusive filters |
| [Readability Score](readability-score/Main.java)                | Java Basics | Java     | Completed | Reads a text file passed as a command-line argument and computes ARI, Flesch-Kincaid, SMOG, and Coleman-Liau readability scores, mapping each to an age bracket and averaging them; split into `io` package |

**Status legend:** Not Started, In Progress, Completed

## Repository Structure

Each project lives in its own folder at the repository root, named after the project. Inside, you will find the source files for that project, matching the structure Hyperskill expects when you download or clone a project locally.

```
hyperskill-projects/
  simple-bot/
    SimpleBot.java
  zookeeper/
    Zookeeper.java
  pencil-game/
    PencilGame.java
  cinema-room-manager/
    CinemaRoomManager.java
  coffee-machine/
    CoffeeMachine.java
  tic-tac-toe/
    TicTacToe.java
  chucknorris/
    Main.java
    cipher/
      CipherOperation.java
      EncodeOperation.java
      DecodeOperation.java
      InvalidCipherException.java
    io/
      ConsoleInteraction.java
  battleship/
    Main.java
    Game.java
    Player.java
    Coordinate.java
    ShipType.java
    field/
      Field.java
      Ship.java
    io/
      ConsoleInteraction.java
      CoordinateParser.java
  honestcalculator/
    Main.java
    Calculator.java
    Equation.java
    EquationParts.java
    Memory.java
    Operator.java
    DivisionByZeroException.java
    io/
      ConsoleInteraction.java
    parsing/
      EquationParser.java
      InvalidOperatorException.java
      NumberParser.java
  bullscows/
    Main.java
    Game.java
    Grade.java
    io/
      ConsoleInteraction.java
  numbers/
    Main.java
    NaturalNumber.java
    Property.java
    Criterion.java
    Request.java
    RequestType.java
    io/
      ConsoleInteraction.java
  readability-score/
    Main.java
    Text.java
    ReadabilityIndex.java
    AgeBracket.java
    IndexScore.java
    io/
      ConsoleInteraction.java
      FileInteraction.java
  README.md
```

## Why This Repo Exists

Hyperskill does not keep a public, browsable history of finished work by default, so this repo serves as:

- A personal log of what I have completed and what track it belongs to
- A portfolio others can look through without needing a Hyperskill account
- A place to revisit earlier projects and see how my code has evolved
