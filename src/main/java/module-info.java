module com.sudoku {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.sudoku to javafx.fxml;
    opens com.sudoku.controller to javafx.fxml;
    opens com.sudoku.model to javafx.fxml;
    opens com.sudoku.view to javafx.fxml;

    exports com.sudoku;
    exports com.sudoku.model;
    exports com.sudoku.service;
    exports com.sudoku.controller;
    exports com.sudoku.view;
}
