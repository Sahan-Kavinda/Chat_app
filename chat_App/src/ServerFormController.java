import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import org.omg.CORBA.PUBLIC_MEMBER;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerFormController {

    @FXML
    private TextArea txtAreaServer;
    @FXML
    private TextField txtFealdServer;

    final int PORT = 5000;
    ServerSocket serverSocket ;
    Socket accept;
    DataInputStream dataInputStream ;
    DataOutputStream dataOutputStream ;
//    BufferedReader bufferedReader ;

    String message = "";



    public void initialize(){
        new Thread(() -> {  //Methanata thread ekak daanna hethuwa api hadanne FX application ekakne hadanne.. Eeka wada karanne wenamama thread ekaka..
            try {
                serverSocket =new ServerSocket(PORT);
//                System.out.println("Server Started..!");
                txtAreaServer.appendText("Server Started..!");
                accept = serverSocket.accept();   //Server ekata request ekak araganna eka...
//                System.out.println("Client Commented..!");   //Client kenek conect wiima...
                txtAreaServer.appendText("\nClient Commented..!");

                dataOutputStream = new DataOutputStream(accept.getOutputStream());
                dataInputStream = new DataInputStream(accept.getInputStream());

                while (!message.equals("exit")){

                    message = dataInputStream.readUTF();
//                System.out.println(message);
                    txtAreaServer.appendText("\n Client : " + message);
                }


            } catch (IOException e) {
                e.printStackTrace();
            }

        }).start();
    }

    @FXML
    void btnOnActionSendServer(ActionEvent event) throws IOException {

        dataOutputStream.writeUTF(txtFealdServer.getText().trim());
        dataOutputStream.flush();
    }

    @FXML
    void Show(KeyEvent event) throws IOException {
        if (event.getCode() == KeyCode.ENTER){
            dataOutputStream.writeUTF(txtFealdServer.getText().trim());
            dataOutputStream.flush();        }
//
//        if (event.getCode() == KeyCode.SPACE){
//            txtFealdServer.setVisible(false);
//        }
    }




}
