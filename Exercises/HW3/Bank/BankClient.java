package Main;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

import static java.lang.Thread.sleep;

public class BankClient  {
    private Socket socket = null;
    private String bankName;
    private  int bankPort;
    private int dnsServerPort;
    private int clientPort;
    private final String host = "127.0.0.1";



 //   private static final String PATH = "./src/";          // intellij
    private static final String PATH = "./src/main/java/";  // quera

    public BankClient(String bankName, int dnsServerPort) throws IOException {
        socket = new Socket(host, dnsServerPort);
        this.bankName = bankName;
        this.dnsServerPort = dnsServerPort;
        this.clientPort = findFreePort();
    }

    private int findBankPort(String bankName, int dnsPort) throws IOException {
        Socket socket = new Socket(host, dnsPort);
        DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
        dataOutputStream.writeUTF('C' + bankName + "%%%" + Integer.toString(this.clientPort));
        dataOutputStream.flush();
        dataOutputStream.close();
        InputStream inputStream = socket.getInputStream();
        DataInputStream dataInputStream = new DataInputStream(inputStream);
        String str = dataInputStream.readUTF();
        dataInputStream.close();
        socket.close();
        if (str.charAt(0) == 'D') {
            return Integer.parseInt(str.substring(1));
        } else
            return -1;
    }

    public synchronized void sendTransaction(int userId, int amount) throws IOException {
        Socket socket = new Socket(host, bankPort);
        DataOutputStream dout = new DataOutputStream(socket.getOutputStream());
        dout.writeUTF('C' + Integer.toString(userId) + "%%%" + Integer.toString(amount));
        dout.flush();
        dout.close();
        InputStream inputStream = socket.getInputStream();
        DataInputStream dataInputStream = new DataInputStream(inputStream);
        String str = dataInputStream.readUTF();
        dataInputStream.close();
        if (str.charAt(0) == 'B')
            System.out.println(str.substring(1));
        else
            System.out.println("Transaction Error");
        socket.close();
    }

    public void sendAllTransactions(String fileName,  int timeBetweenTransactions) {
         File file = new File(PATH + fileName);
        try {
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()){
                String str =scanner.nextLine();
                String[] port = str.split("\\s+");
                    sendTransaction(Integer.parseInt(port[0]), Integer.parseInt(port[1]));
                if (timeBetweenTransactions > 0) {
                    try {
                        sleep(timeBetweenTransactions);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }scanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private  int findFreePort() {
        ServerSocket socket = null;
        try {
            socket = new ServerSocket(0);
            socket.setReuseAddress(true);
            int port = socket.getLocalPort();
            try {
                socket.close();
            } catch (IOException e) {
                // Ignore IOException on close()
            }
            return port;
        } catch (IOException e) {
        } finally {
            if (socket != null) {
                try {
                    socket.close();
                } catch (IOException e) {
                }
            }
        }
        throw new IllegalStateException("Could not find a free TCP/IP port to start embedded Jetty HTTP Server on");
    }

}
