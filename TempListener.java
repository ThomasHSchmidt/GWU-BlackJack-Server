// import java.net.*;
// import javax.swing.*;
// import java.io.*;

// public class TempListener extends Thread {
//     private Socket sock;
//     private JTextArea txtArea;
//     private JList<String> userList;
//     private int[] starpos = {725, 475, 585, 560, 430, 643, 280, 560, 140, 480};
//     private int id;
 
//     public TempListener(Socket sock)
//     {
//         this.sock = sock;
//         this.txtArea = txtArea;
//         this.userList = userList;
//     }

//     public void run()
//     {

//         for(int i = 0; i < BlackjackServer.connections.size(); i++) {
//             if(id == i) {
//                 TableGUI.setStar(starpos[2*i], starpos[(2*i) + 1]);
//                 PrintWriter pw = new PrintWriter(BlackjackServer.connections.get(i).getOutputStream());
//                 pw.println("Place your bet.");
//             }
//         }
//         try
//         {
//             String line;
//             boolean message = false;
//             boolean endOfUserList = false;
//             String users = "";

//             PrintWriter pw = new PrintWriter(sock.getOutputStream());
//             BufferedReader in =
//                 new BufferedReader(
//                         new InputStreamReader(sock.getInputStream()));

//             while ((line = in.readLine()) != null)
//             {
//                 // Check for message
//                 if (line.equals("START MSG"))
//                 {
//                     // Start message, continue to next line
//                     message = true;
//                     continue;
//                 }
//                 else if (line.equals("END MSG"))
//                 {
//                     // End message, continue to next line
//                     // Scroll to bottom
//                     //txtArea.setCaretPosition(txtArea.getDocument().getLength());
//                     message = false;
//                     continue;
//                 }
//                 else if (message) 
//                 {
//                     // Add message to text area
//                     txtArea.append(line + "\n");
//                     continue;
//                 }

//                 // Check for update of user list
//                 if (line.equals("START_CLIENT_LIST"))
//                 {
//                     // Start user list, continue to next line
//                     endOfUserList = true;
//                     continue;
//                 }
//                 else if (line.equals("END_CLIENT_LIST"))
//                 {
//                     // End update of users
//                     endOfUserList = false;
//                     users = users.replaceAll("^,|,$", ""); // Remove first and last comma
//                     // Update user list
//                     userList.setListData(users.split(","));
//                     users = "";
//                     continue;
//                 }
//                 // End of the list = add user to end
//                 else if (endOfUserList)
//                 {
//                     // Add to user list
//                     users += "," + line;
//                     continue;
//                 }
//             }
//             //close the stream
//             pw.close();

//             //close the input stream
//             in.close();
//         }
//         catch(Exception e){ }
//     }
// }
