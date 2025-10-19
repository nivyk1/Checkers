package com.axioma.checkers.controller;

import com.axioma.checkers.model.Board;
import com.axioma.checkers.model.Game;
import com.axioma.checkers.model.GameService;
import com.axioma.checkers.model.AssignmentStrategy;
import com.axioma.checkers.model.ComputerStrategy;
import com.axioma.checkers.view.Renderer;
import com.axioma.checkers.view.InputHandler;

public final class Main {
    public static void main(String[] args) {
        Board board = new Board();
        board.setupInitial();
        Game game = new Game();
        GameService logic = new GameService();
        Renderer renderer = new Renderer();
        InputHandler input = new InputHandler();
        ComputerStrategy ai = new AssignmentStrategy();

        new GameController(board, game, logic, renderer, input, ai).run();
    }
}