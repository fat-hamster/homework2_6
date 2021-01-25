import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class SimpleClient {
    private DataOutputStream dataOutputStream;

    public SimpleClient() {
        try {
            openConnection();
            while (true) {
                String outputString = new Scanner(System.in).nextLine();
                sendMessage(outputString);
                if(ClientHandler.FINN.equals(outputString)) {
                    dataOutputStream.close();
                    return;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void openConnection() throws IOException {
        Socket socket = new Socket(ClientHandler.ADDRESS, ClientHandler.PORT);
        dataOutputStream = new DataOutputStream(socket.getOutputStream());
        new ClientHandler("Server", socket);
    }

    public void sendMessage(String message) {
        try {
            dataOutputStream.writeUTF(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
