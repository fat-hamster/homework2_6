import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;

public class ClientHandler {
    public static final int PORT = 8189;
    public static final String ADDRESS = "localhost";
    public static final String FINN = "/end";

    private Socket socket;
    private DataInputStream dataInputStream;
    private final String name;

    public ClientHandler(String name, Socket socket) {
        this.name = name;
        try {
            this.socket = socket;
            this.dataInputStream = new DataInputStream(socket.getInputStream());
            new Thread(() -> {
                try {
                    readMessages();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    closeConnection();
                }
            }).start();
        } catch (IOException e) {
            System.out.println("Ошибка при создании обработчика");
            e.printStackTrace();
        }
    }

    public void readMessages() throws IOException {
        while (true) {
            String inputString = dataInputStream.readUTF();
            if(ClientHandler.FINN.equals(inputString)) {
                return;
            }
            System.out.println(this.name + ": " + inputString);
        }
    }

    private void closeConnection() {
        try {
            dataInputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
