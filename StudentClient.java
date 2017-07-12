import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * Created by cdxu0 on 2017/7/12.
 */
public class StudentClient extends Application{
    private TextField textFieldName = new TextField();
    private TextField textFieldStreent = new TextField();
    private TextField textFieldCity = new TextField();
    private TextField textFieldState = new TextField();
    private TextField textFieldZip = new TextField();
    //Button for sending a student to the server
    private Button buttonRegister = new Button("Register to the server");

    String host = "localhost";

    @Override
    public void start(Stage primaryStage) {
        GridPane pane = new GridPane();
        pane.add(new Label("Name"), 0, 0);
        pane.add(textFieldName,1,0);
        pane.add(new Label("Streey"),0,1);
        pane.add(textFieldStreent,1,1);
        pane.add(new Label("City"),0,2);
        HBox hBox = new HBox(2);
        pane.add(hBox,1,2);
        hBox.getChildren().addAll(textFieldCity, new Label("State"),
                textFieldState, new Label("Zip"),textFieldZip);
        pane.add(buttonRegister,1,3);
        GridPane.setHalignment(buttonRegister, HPos.RIGHT);

        pane.setAlignment(Pos.CENTER);
        textFieldName.setPrefColumnCount(15);
        textFieldStreent.setPrefColumnCount(15);
        textFieldCity.setPrefColumnCount(10);
        textFieldState.setPrefColumnCount(2);
        textFieldZip.setPrefColumnCount(3);

        buttonRegister.setOnAction(new ButtonListener());

        Scene scene = new Scene(pane, 450,200);
        primaryStage.setTitle("StudentClient");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private class ButtonListener implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent e) {
            try {
                Socket socket = new Socket(host, 8000);
                ObjectOutputStream toServer = new ObjectOutputStream(socket.getOutputStream());
                String name = textFieldName.getText().trim();
                String street = textFieldStreent.getText().trim();
                String city = textFieldCity.getText().trim();
                String state = textFieldState.getText().trim();
                String zip = textFieldZip.getText().trim();

                StudentAddress studentAddress = new StudentAddress(name, street,city,state,zip);
                toServer.writeObject(studentAddress);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }

    }


}
