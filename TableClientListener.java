import java.net.*;
import javax.swing.*;
import java.io.*;

public class TableClientListener extends Thread {
    private Socket sock;
    private JTextArea txtArea;
    private JList<String> userList;
    private int id;
    private int[] starpos = {725, 475, 585, 560, 430, 643, 280, 560, 140, 480};
 
    public TableClientListener(Socket sock, int id)
    {
        this.sock = sock;
        this.id = id;

    }

    public void run()
    {
        for (int i = 0; i < 5; i++) {
            if (id == i) {
                TableGUI.setStar(starpos[2*i], starpos[(2*i)+1]);
            }
        }
        try
        {
            String line;
            boolean message = false;
            boolean endOfUserList = false;
            String users = "";

            PrintWriter pw = new PrintWriter(sock.getOutputStream());
            BufferedReader in =
                new BufferedReader(
                        new InputStreamReader(sock.getInputStream()));

            while ((line = in.readLine()) != null)
            {
                // Check for message
                if (line.equals("START MSG"))
                {
                    // Start message, continue to next line
                    message = true;
                    continue;
                }
                else if (line.equals("END MSG"))
                {
                    // End message, continue to next line
                    // Scroll to bottom
                    //txtArea.setCaretPosition(txtArea.getDocument().getLength());
                    message = false;
                    continue;
                }
                else if (message) 
                {
                    // Add message to text area
                    txtArea.append(line + "\n");
                    continue;
                }

                // Check for update of user list
                if (line.equals("START_CLIENT_LIST"))
                {
                    // Start user list, continue to next line
                    endOfUserList = true;
                    continue;
                }
                else if (line.equals("END_CLIENT_LIST"))
                {
                    // End update of users
                    endOfUserList = false;
                    users = users.replaceAll("^,|,$", ""); // Remove first and last comma
                    // Update user list
                    userList.setListData(users.split(","));
                    users = "";
                    continue;
                }
                // End of the list = add user to end
                else if (endOfUserList)
                {
                    // Add to user list
                    users += "," + line;
                    continue;
                }
            }
            //close the stream
            pw.close();

            //close the input stream
            in.close();
        }
        catch(Exception e){ }
    }
}
