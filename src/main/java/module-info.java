module edu.hcmiu.minesweeper {
    requires javafx.controls;
    requires javafx.fxml;


    opens edu.hcmiu.minesweeper to javafx.fxml;
    exports edu.hcmiu.minesweeper;
}