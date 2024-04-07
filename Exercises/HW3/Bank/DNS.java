package Main;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Array;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class DNS  {
    private final HashMap<String, Integer> bankPorts = new HashMap<>();
    private int dnsPort;
    private Thread dnsThread;
    private final String host = "127.0.0.1";

    //
    public DNS(int dnsPort) throws IOException {
        this.dnsPort = dnsPort;
        System.out.println("Starting DNS");
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
    }

    private int getDsnPort() {
        return dnsPort;
    }

    //
    public int getBankServerPort(String bankName) {
        for (String key : bankPorts.keySet()) {
            if (key.equalsIgnoreCase( bankName)) return bankPorts.get(key);
        }
        return -1;
    }

    private int setBankServerPort(String infos) {
        String[] info = infos.split("%%%");
        for (String i : bankPorts.keySet()) {
            if (i == info[0]) return bankPorts.get(i);
        }
        bankPorts.put(info[0], Integer.parseInt(info[1]));
        return 0;
    }

    private void main() throws IOException {
        System.out.println("DNS Server is Activated");
        ServerSocket serverSocket = new ServerSocket(getDsnPort());
        String str;
        int i = 0;
        do {
            Socket s = serverSocket.accept();
            DataInputStream dis = new DataInputStream(s.getInputStream());
            str = (String) dis.readUTF();
            if (str.charAt(0) == 'B')
                setBankServerPort(str.substring(1));
            else if (str.charAt(0) == 'C') {
                sendBankPort(str.substring(1));
            }
            s.close();
        } while (true);
    }

    private void sendBankPort(String infos) throws IOException {
        String[] info = infos.split("%%%");
        int bankPort = -1;
        for (String i : bankPorts.keySet()) {
            if (i == info[0]) bankPort = bankPorts.get(i);
        }
        Socket socket = new Socket(host, Integer.parseInt(info[1]));
        DataOutputStream dout = new DataOutputStream(socket.getOutputStream());
        dout.writeUTF('D' + Integer.toString(bankPort));
        dout.flush();
        dout.close();
    }

}
