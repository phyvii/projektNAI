import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.sql.SQLOutput;

public class Client {
    public static int count = 0;

    public Client(){
        try (Socket socket = new Socket("localhost", 7777);
             BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter writer = new PrintWriter(socket.getOutputStream(),true);
        ) {

            int id = count;
            count++;
            writer.println("Hello! THERE is this working mate pls");
            System.out.println(reader.readLine());

            Thread.sleep(300);
            writer.println("bye");


        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    public static void main(String[] args) {
//        for (int i = 0; i < 20; i++) {
//            new Thread(()->{
//                new Client();
//                try {
//                    Thread.sleep(300);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            } ).start();
//
//        }
//        Client client = new Client();
        int numClients = 15;
        for (int i = 0; i < numClients; i++) {
            new Thread(() -> {
                try {
                    SocketChannel clientChannel = SocketChannel.open(new InetSocketAddress("localhost", 7777));
                    ByteBuffer buffer = ByteBuffer.allocate(1024);
                    buffer.put("Hello, world!".getBytes());
                    buffer.flip();
                    clientChannel.write(buffer);
                    Thread.sleep(100);
                    buffer.clear();
                    buffer.put("bye".getBytes());
                    buffer.flip();

                    clientChannel.close();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }).start();
        }

    }
}
