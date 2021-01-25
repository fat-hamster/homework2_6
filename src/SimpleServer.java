import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class SimpleServer {

    public SimpleServer() {
        try(ServerSocket server = new ServerSocket(ClientHandler.PORT)) {
            System.out.println("Ожидаем клиента");
            Socket socket = server.accept();
            System.out.println("Клиент подключился");
            new ClientHandler("Client", socket);

            DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
            while (true) {
                String message = new Scanner(System.in).nextLine();
                dataOutputStream.writeUTF(message);
                if(ClientHandler.FINN.equals(message)) {
                    dataOutputStream.close();
                    return;
                }
            }
        } catch (IOException e) {
            System.out.println("Ошибка в работе сервера");
            e.printStackTrace();
        }
    }
}
