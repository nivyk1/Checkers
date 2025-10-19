# Checkers (Console, MVC) – Queen Race Variant

## Game Rules (short)
- 8×8 board; dark squares are playable.
- White (human) vs Black (computer).
- Pieces move only forward.
- First piece to reach the opponent’s back rank wins immediately (no promotion; forward-only).

- Input: moves like `A3->B4` (Capital Letters are must)
- Quit: `q`
- Invalid/illegal inputs re-prompt


## Architecture (short)
- `model/`: core domain and rules
  - `Board`, `Piece`, `Color`, `Move`, `Game`, `GameService`
  - `ComputerStrategy`, `AssignmentStrategy` (the computers rules)
- `view/`: console UI
  - `Renderer` (board + messages), `InputHandler` (parse `A3->B4`, `q` to quit)
- `controller/`: orchestration
  - `GameController` (game loop), `Main` (entry point)

## How to Run
Prerequisite: Java 17+

Linux/macOS:
```bash
java -jar checkers.jar
```

Windows (CMD):
```cmd
java -jar checkers.jar
```


