package Main;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;

public class BankServer {
    private  ServerSocket serverSocket = null;
    private String bankName;
    private int dnsPort;
    private final String host = "127.0.0.1";
    private ArrayList<Integer> allPorts = new ArrayList<>();


    private  final HashMap<Integer, Integer> accounts = new HashMap<>();

    public BankServer(String bankName, int dnsPort) throws IOException {
        this.bankName = bankName;
        this.dnsPort = dnsPort;
        System.out.println("Starting BankServer");
           new Thread (new Runnable() {
               public void run() {
                   try {
                       System.out.println("Bank Server is running");
                       main();
                   } catch (Exception e) {
                       System.out.println(e);
                   }
               }
           }).start();
        introduceBankServerToDnsServer(bankName, dnsPort);
    }

    public  int getBalance(int userId) {
        int balance = -1;
        for (Integer i : accounts.keySet()) {
            if (i == userId) balance = accounts.get(i);
        }
        return balance;
    }

    public  void setBalance(int userId, int amount) {
        for (Integer i : accounts.keySet()) {
            if (i == userId) accounts.replace(i, accounts.get(i) + amount);
        }
        return;
    }

    public int getNumberOfConnectedClients() {
        return allPorts.size();
    }

    private void main() throws IOException {
        System.out.println("Bank Server is Activated");
        do {
            Socket socket = serverSocket.accept();
            for(Integer port: allPorts) {
                if(port.equals(socket.getPort())){
                    allPorts.add(socket.getPort());
                }
            }
            InputStream inputStream = socket.getInputStream();
            DataInputStream dataInputStream = new DataInputStream(inputStream);
            String str = dataInputStream.readUTF();
            dataInputStream.close();
            String message = "";
            if (str.charAt(0) == 'C') {
                String[] info = str.substring(1).split("%%%");
                int userId = Integer.parseInt(info[0]);
                if (getBalance(userId) >= Integer.parseInt(info[1])) {
                    setBalance(userId, Integer.parseInt(info[1]));
                    message = 'B' + info[0] + "%%%" + Integer.toString(getBalance(userId));
                } else {
                    message = "Transaction Error";
                }
            } else message = "Transaction Error";
            DataOutputStream dout = new DataOutputStream(socket.getOutputStream());
            dout.writeUTF(message);
            dout.flush();
            dout.close();
            socket.close();
        } while (true);
    }


    private void introduceBankServerToDnsServer(String bankName, int dnsPort) throws IOException {
        Socket socket = new Socket(host, dnsPort);
        DataOutputStream dout = new DataOutputStream(socket.getOutputStream());
        dout.writeUTF('B' + bankName + "%%%" + Integer.toString(findFreePort()));
        dout.flush();
        dout.close();
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
