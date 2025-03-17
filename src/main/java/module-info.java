module proj.projanimacao {
    requires javafx.controls;
    requires javafx.fxml;


    opens proj.projanimacao to javafx.fxml;
    exports proj.projanimacao;
}