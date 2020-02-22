# Isolation-game-AI-
![screenshot](https://github.com/MaksymPylypenko/Isolation-game-AI-/blob/master/isolation_demo.png)

# Rules of the game
* Each player moves 1 square at a time in any direction.
* If you run out of moves, you lose.

# How to run?
* To play vs AI: `test.jar SmartPlayer`
* To set a custom board size `test.jar SmartPlayer 15,15`
* To add more players `test.jar SmartPlayer SmartPlayer SmartPlayer`
* To watch AI vs Random: `test.jar SmartPlayer RandomPlayer`

# Features implemented
* Minmax + Alpha Beta pruning algorithm 
* Heuristic evaluation.

# Heuristic strategy
When it is no longer possible to look further in the game tree, a SmartPlayer will evaluate the position based on the captured area and the number of invasions. Although the strategy is better than random, it will likely fail vs aggressive players.

![heuristic](https://github.com/MaksymPylypenko/Isolation-game-AI-/blob/master/heuristic.png)
