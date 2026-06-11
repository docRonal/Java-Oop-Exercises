# Java OOP Exercises

# Collection of practical Java assignments. Focus on core syntax, OOP, and CLI utilities.
#📂 Project List

      Shopping List – Main.java

           File I/O, LinkedHashMap, interactive CLI menu.

      Quadratic Equation – QuadraticEquation.java

           CLI arguments, discriminant math, error handling.

      Text Converter – TextConverter.java

           String manipulation, StringBuilder, case transformation.
      Phone Book Manager  – `Main.java`
      
           Features: A polymorphic phone directory using a custom sorting key. Implements safe element removal during iteration.
           Concepts: Abstract classes (`Wpis`), Inheritance (`Osoba`, `Firma`), Interfaces (`Comparable`), Data Structures (`TreeMap`, `HashSet`), and Collection Iterators.
      
      Vector Addition Calculator** – `Main.java`, `WektoryLogika.java`, `WektoryRoznejDlugosciException.java`
    
          Features:** Strict input validation, interactive retry loop, and a custom exception mechanism that prevents operations on vectors of different sizes while providing informative error feedback.
          Concepts:** Custom Exceptions (`extends Exception`), Error Handling (`try-catch`, `throws`), String Parsing (`split`, `Integer.parseInt`), `ArrayList` manipulation, and Separation of Concerns (business logic isolated from the UI).

          
This project is a classic **Checkers** board game implemented in Java, showcasing the core principles of Object-Oriented Programming (OOP). It is developed as part of a Java OOP exercises collection.

## 📋 Project Overview

The main goal of this project is to apply object-oriented design to model a real-world game scenario. The board, game pieces, rules, and turn-based logic are decoupled into independent classes, ensuring clean, modular, and maintainable code.

## Key Features
      
      * **Classic Ruleset:** Features standard diagonal movement, mandatory capturing, and piece promotion to "Kings".
      * **Local Multiplayer:** A hotseat mode allowing two players to compete on the same screen.
      * **Move Validation:** The system dynamically verifies each move according to the game state and highlights available legal moves.
      * **AI Opponent (Optional):** *[If applicable]* A basic computer opponent utilizing a random-move algorithm or a simple Minimax strategy.
      * **User Interface:** *[Choose your option: Console-based (CLI) / Graphical User Interface (Swing or JavaFX)]*.

## 🧩 OOP Principles Applied

The architecture demonstrates the fundamental pillars of OOP:
      
      1. **Encapsulation:** Object states and logic are hidden. For instance, the `Piece` class securely encapsulates its color, position, and rank (Pawn vs. King), while the `Board` class manages the 8x8 grid exclusively through public APIs.
      2. **Inheritance:** A base structure is established for players or pieces, allowing specialized classes to inherit common attributes while adding distinct characteristics.
      3. **Polymorphism:** The move validation engine (`isValidMove()`) changes its behavior dynamically depending on whether the active piece is a regular pawn or a king.
      4. **Abstraction:** Irrelevant complexities are filtered out, focusing only on essential properties of the board game elements to build a robust model.

## 💻 Core Class Structure

      * `Main` – The entry point that initializes the game loop.
      * `GameController` / `Game` – Manages game states, handles turn rotations, and evaluates win/loss conditions.
      * `Board` – Models the 8x8 grid and controls piece positioning and board updates.
      * `Piece` / `Checker` – Represents an individual checker with color and status properties.
      * `Player` – Models a participant, keeping track of their color, name, and score.

# 🛠 Tech Stack

    Language: Java 17+

    Concepts: Collections, Exception Handling, File I/O, CLI Args.

# 🚀 Quick Start

    Bash

    javac FileName.java

    Run:
    Bash

    java FileName [args]

# Autor:  docRonal
