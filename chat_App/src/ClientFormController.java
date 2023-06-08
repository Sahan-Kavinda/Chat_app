import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ClientFormController {

    @FXML
    private TextArea txtAreaClient;
    @FXML
    private TextField txtFeieldClient;

    final int PORT = 5000;
    Socket socket ;
    DataInputStream dataInputStream ;
    DataOutputStream dataOutputStream ;

    String message = "";

    public void initialize(){
        new Thread(() -> {  //Methanata thread ekak daanna hethuwa api hadanne FX application ekakne hadanne.. Eeka wada karanne wenamama thread ekaka..
            try {
                socket = new Socket("localhost",PORT);

                dataOutputStream = new DataOutputStream(socket.getOutputStream());
                dataInputStream = new DataInputStream(socket.getInputStream());

                while (!message.equals("exit")){
                    message = dataInputStream.readUTF();
//                System.out.println(message);
                    txtAreaClient.appendText("\n Server : " + message);  //text feald ekata msg eka daanne meeken
                }


            } catch (IOException e) {
                e.printStackTrace();
            }

        }).start();
    }

    @FXML
    void btnOnActionSendClient(ActionEvent event) throws IOException {
        dataOutputStream.writeUTF(txtFeieldClient.getText().trim());
        dataOutputStream.flush();
    }

    @FXML
    void Show(KeyEvent event) throws IOException {
        if (event.getCode() == KeyCode.ENTER) {

            dataOutputStream.writeUTF(txtFeieldClient.getText().trim());
            dataOutputStream.flush();
        }
    }
}
