import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;

/**
 * Created by cdxu0 on 2017/7/11.
 */
public class MultiThreadServer extends Application{
    //text area for displaying contents
    TextArea textArea = new TextArea();
    //Number a client
    private int clientNo = 0;

    @Override
    public void start(Stage primaryStage) {
        //create a scene and place it on stage
        Scene scene = new Scene(new ScrollPane(textArea), 450, 200);
        primaryStage.setTitle("MultiThreadServer");
        primaryStage.setScene(scene);
        primaryStage.show();

        new Thread(()->{
            try {
                //create a server socket
                ServerSocket serverSocket = new ServerSocket(8000);
                textArea.appendText("MultiThreadServer started at " + new Date() + "\n");

                while (true) {
                    //Listen for a connection request
                    Socket socket = serverSocket.accept();
                    clientNo++;
                    Platform.runLater(() -> {
                        //Display the client number
                        textArea.appendText("Starting thread for client " + clientNo + " at " + new Date() + "\n");
                        InetAddress inetAddress = socket.getInetAddress();
                        textArea.appendText("Client " + clientNo + "'s host name is " + inetAddress.getHostName() + "\n");
                        textArea.appendText("Client " + clientNo + "'s IP address is " + inetAddress.getHostAddress() + "\n");
                    });
                    //create and start a new thread for the connection
                    new Thread(new HandleAClient(socket)).start();
                }
            } catch (IOException ex) {
                System.err.println(ex);
            }
        }).start();
    }

    class HandleAClient implements Runnable {
        private Socket socket;
        public HandleAClient(Socket socket){
            this.socket = socket;
        }
        @Override
        public void run() {
            try {
                DataInputStream inputFromClient = new DataInputStream(socket.getInputStream());
                DataOutputStream outputToClient = new DataOutputStream(socket.getOutputStream());

                while (true) {
                    double radius = inputFromClient.readDouble();
                    double area = radius * radius * Math.PI;
                    outputToClient.writeDouble(area);
                    Platform.runLater(()->{
                        textArea.appendText("radius received from client " + radius + "\n");
                        textArea.appendText("area found " + area + "\n");
                    });
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
}
