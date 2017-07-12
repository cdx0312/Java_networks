import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * Created by cdxu0 on 2017/7/11.
 */
public class Client extends Application{
    //IO stream
    DataOutputStream toServer = null;
    DataInputStream fromServer = null;

    @Override
    public void start(Stage primaryStage) {
        //panel p to hold the label and text field
        BorderPane paneForTextField = new BorderPane();
        paneForTextField.setPadding(new Insets(5,5,5,5));
        paneForTextField.setStyle("-fx-border-color: #dc2737");
        paneForTextField.setLeft(new Label("Enter a radius: "));

        TextField textField = new TextField();
        textField.setAlignment(Pos.BOTTOM_RIGHT);
        paneForTextField.setCenter(textField);

        BorderPane mainPane = new BorderPane();
        //textarea to display content
        TextArea ta = new TextArea();
        mainPane.setCenter(new ScrollPane(ta));
        mainPane.setTop(paneForTextField);
        //create a scene and place it on the stage
        Scene scene = new Scene(mainPane, 450, 200);
        primaryStage.setTitle("Client");
        primaryStage.setScene(scene);
        primaryStage.show();

        textField.setOnAction(event -> {
            try {
                //get radius from the text field
                double radius = Double.parseDouble(textField.getText().trim());
                //send the radius to server
                toServer.writeDouble(radius);
                toServer.flush();
                //getarea from server
                double area = fromServer.readDouble();
                //display to the text area
                ta.appendText("Radius is " + radius + "\n");
                ta.appendText("Area received from the server is " + area + '\n');
            } catch (IOException ex) {
                System.err.println(ex);
            }
        });

        try {
            //create a socket to connect to the server
            Socket socket = new Socket("localhost", 8000);
            //create an input stream to receive data from service
            fromServer = new DataInputStream(socket.getInputStream());
            //create an output stream to send data to server
            toServer = new DataOutputStream(socket.getOutputStream());
        } catch (IOException ex) {
            ta.appendText(ex.toString() + "\n");
        }

    }
}
