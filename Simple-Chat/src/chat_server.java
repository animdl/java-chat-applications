import javax.swing.*;
import java.awt.*;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class chat_server extends JFrame {

    static ServerSocket ss;
    static Socket socket;

    static DataInputStream dis;
    static DataOutputStream dos;

    JPanel panel;
    static JTextArea message_output;
    JTextField message_input;
    JButton message_send;

    public chat_server () {
        panel = new JPanel();
        message_output = new JTextArea();
        message_input = new JTextField("Input Here");
        message_send = new JButton("Send");

        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        message_output.setPreferredSize(new Dimension(450,375));

        message_send.addActionListener(e -> {
            try {
                String message;
                message = message_input.getText().trim();
                message_output.setText(message_output.getText().trim() + "\nServer: " + message);
                dos.writeUTF(message);
            } catch(Exception ex) {
                ex.printStackTrace();
            }
        });

        panel.add(message_output);
        panel.add(message_input);
        panel.add(message_send);

        this.add(panel);
        this.setSize(new Dimension(500, 500));
        this.setContentPane(panel);
        this.setVisible(true);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    public chat_server(String title) {
        this();
        this.setTitle(title);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new chat_server("Chat Server"));

        String message = "";

        try {
            ss = new ServerSocket(1201);
            socket = ss.accept();

            dis = new DataInputStream(socket.getInputStream());
            dos = new DataOutputStream(socket.getOutputStream());

            while(!message.equals("exit")) {
                message = dis.readUTF();
                message_output.setText(message_output.getText().trim() + "\nClient: " + message);
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

}
