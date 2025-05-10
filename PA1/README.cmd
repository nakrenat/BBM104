# PA1 s  
**Course:** BBM104 
**University:** Hacettepe University  
**Term:** Spring 2024  
**Student:** Erkan TAN  
**Student ID:** B2220356098



# MazeSolver

## Overview
`MazeSolver` is a simple Java program that solves a maze using a recursive depth-first search (DFS) strategy, starting from a specified entry point. The maze is read from a text file and is expected to contain walls (`#`), open paths (`.`), and a designated exit (`X`).

## Features
- Reads a maze from a text file.
- Uses DFS to search for the exit.
- Marks the path taken with `*`.
- Detects and prints whether a path to the exit was found.
- Handles invalid input and file reading exceptions.

## Usage

### Compilation
```bash
javac MazeSolver.java
