package zongming.snippets;

import java.io.*;
import java.lang.System;
import java.net.Socket;
//
// This is an implementation of a simplified version of a command
// line ftp client. The program always takes two arguments
//

public class CSftp {

    static final int MAX_LEN = 255;
    static final int ARG_CNT = 2;

    public static void main(String[] args) {
        byte cmdString[] = new byte[MAX_LEN];

        // Get command line arguments and connected to FTP
        // If the arguments are invalid or there aren't enough of them
        // then exit.

        if (args.length != ARG_CNT) {
            System.out.print("Usage: cmd ServerAddress ServerPort\n");
            return;
        }

        try {
            Socket socket = null;
            boolean need_socket = true;
            boolean first_time = true;

            for (int len = 1; len > 0; ) {
                System.out.print("csftp> ");
                len = System.in.read(cmdString);
                if (len <= 0)
                    break;

                if (socket != null) {
                    if (!socket.isConnected() || socket.isClosed()) {
                        need_socket = true;
                    } else {
                        need_socket = false;
                    }

                }

                try {
                    if (need_socket || socket.equals(null)) {
                        if (args[1] != null) {
                            socket = new Socket(args[0], Integer.parseInt(args[1]));

                        } else {
                            args[1] = "21";
                            socket = new Socket(args[0], 21);
                        }
                        need_socket = false;
                    }
                } catch (Exception e) {
                    System.out.println(
                            "0xFFFC Control connection to " + args[0] + " on port " + args[1] + " failed to open");
                    return;
                }

                String command = new String(cmdString);
                command = command.substring(0, len - 1);
                command = command + " ";
                String command1 = command.substring(0, command.indexOf(" "));
                String command2 = null;
                boolean wrong_num_arg = false;

                if (command1.length() + 1 < command.length()) {

                    command2 = command.substring(command.indexOf(" ") + 1, command.length() - 1);
                    command2 = command2.trim();

                    if (!command2.equals("")) {
                        if (command2.contains(" ")) {
                            wrong_num_arg = true;
                        }
                    }

                }
                command = command.substring(0, command.length() - 1);

                // TODO: implement each case according to the a1 requirements

                BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
                BufferedReader socketIn = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

                if (first_time) {
                    if (socketIn.ready())
                        socketIn.readLine();
                    first_time = false;
                }

                switch (command1.toLowerCase()) {
                    case "username": {
                        try {
                            if (command2 == null || wrong_num_arg) {
                                System.out.println("0x002 Incorrect number of arguments");
                            } else
                                do_username(stdIn, socketIn, out, command2);
                        } catch (Exception e) {
                            System.out.println("0xFFFD Control connection I/O error, closing control connection.");
                            socket.close();
                        }
                        break;

                    }

                    case "pw": {

                        try {
                            input_message(out, "PW " + command2);
                            if (command2 == null || wrong_num_arg) {
                                System.out.println("0x002 Incorrect number of arguments");
                            } else {
                                get_message(socketIn);
                            }
                        } catch (Exception e) {
                            System.out.println("0xFFFD Control connection I/O error, closing control connection.");
                            socket.close();
                        }

                        break;
                    }

                    case "quit": {
                        try {
                            if (command2 != null || wrong_num_arg) {
                                System.out.println("0x002 Incorrect number of arguments");
                            } else {
                                input_message(out, "QUIT");
                                get_message(socketIn);
                                return;
                            }
                        } catch (Exception e) {
                            System.out.println("0xFFFD Control connection I/O error, closing control connection.");
                            socket.close();
                        }

                        break;

                    }

                    case "get": {

                        // TODO: implement get and test it

                        try {
                            Socket data_transfer = do_pasv(socketIn, out);

                            if (command2 == null || wrong_num_arg) {
                                System.out.println("0x002 Incorrect number of arguments");
                            } else if (data_transfer != null && command2 != null) {

                                BufferedReader data_transfer_reader = new BufferedReader(
                                        new InputStreamReader(data_transfer.getInputStream()));

                                input_message(out, "RETR " + command2);
                                out.flush();

                                String server_output = get_message(socketIn);

                                String code = get_code(server_output);

                                if (code.equals("226")) {
                                    File f = new File("./" + command2);
                                    FileWriter fw = new FileWriter(f);

                                    BufferedWriter br = new BufferedWriter(fw);

                                    try {

                                        String data_transfer_output = null;

                                        //data_transfer_output = get_message(data_transfer_reader);
                                        while (true) {

                                            for (int i = 0; i < 500000; i++) {
                                                if (data_transfer_reader.ready()) {
                                                    break;
                                                }
                                            }

                                            if (data_transfer_reader.ready()) {
                                                if ((data_transfer_output = data_transfer_reader.readLine()) != null) {
                                                    System.out.println("<-- " + data_transfer_output);
                                                    fw.write(data_transfer_output);
                                                }
                                            } else {
                                                break;
                                            }
                                        }

                                        br.close();

                                    } catch (Exception e) {
                                        System.out.println(
                                                "0x3A7 Data transfer connection I/O error, closing data connection");
                                        data_transfer.close();
                                    }

                                } else {
                                    System.out.println("0x38E Access to local file " + command2 + " denied");
                                }

                            }
                        } catch (Exception e) {
                            System.out.println("0xFFFD Control connection I/O error, closing control connection.");
                            socket.close();
                        }

                        break;
                    }

                    case "features": {

                        try {
                            if (command2 != null || wrong_num_arg) {
                                System.out.println("0x002 Incorrect number of arguments");
                            } else {
                                input_message(out, "FEAT");
                                get_message(socketIn);
                            }
                        } catch (Exception e) {
                            System.out.println("0xFFFD Control connection I/O error, closing control connection.");
                            socket.close();
                        }

                        break;
                    }
                    case "cd": {
                        if (command2 == null || wrong_num_arg) {
                            System.out.println("0x002 Incorrect number of arguments");
                        } else {

                            input_message(out, "CWD " + command2);
                            get_message(socketIn);
                        }

                        break;
                    }
                    case "dir": {

                        try {
                            Socket data_transfer = do_pasv(socketIn, out);
                            if (command2 != null || wrong_num_arg) {
                                System.out.println("0x002 Incorrect number of arguments");
                            } else if (data_transfer != null) {
                                BufferedReader data_reader = new BufferedReader(
                                        new InputStreamReader(data_transfer.getInputStream()));
                                input_message(out, "LIST");
                                get_message(socketIn);
                                try {
                                    get_message(data_reader);
                                } catch (IOException e) {
                                    System.out.println("0x3A7 Data transfer connection I/O error, closing data connection");
                                    data_transfer.close();
                                }

                            }
                        } catch (Exception e) {
                            System.out.println("0xFFFD Control connection I/O error, closing control connection.");
                            socket.close();
                        }
                        break;
                    }
                    default: {
                        System.out.println("0x001 Invalid command.");
                        break;
                    }
                }
                //System.out.println(command.substring(0,len-1));

                //System.out.println("900 Invalid command.");
            }
        } catch (Exception exception) {
            if (exception instanceof IOException) {
                System.err.println("0xFFFE Input error while reading commands, terminating");
            } else {
                System.err.println(exception.getMessage());
            }
        }

    }

    /*
       Do USER username command
       @ param BufferReader stdIn: bufferReader that connects to user input
       @ param BufferReader sockIn: bufferReader that connects to the server
       @ param PrintWriter out: printWriter that connects to the server
       @ param String userInput: the input from the user
       */

    public static void do_username(BufferedReader stdIn, BufferedReader socketIn, PrintWriter out, String userInput)
            throws IOException {

        String server_output;

        input_message(out, "USER " + userInput);
        server_output = get_message(socketIn);
        String code = get_code(server_output);
        if (code.equals("331")) {
            System.out.print("csftp> ");
            String password = stdIn.readLine();
            input_message(out, password);
            get_message(socketIn);
        }

    }

    /* Do pasv command and return the socket that connects to the address indicated by message returned by server
       @param BufferReader socketIn:  BufferReader connects to the server socket
       @param PrintWriter out:  PrintWriter connects to the server socket
       @return data_transfer: Socket connects to the the address indicated by server
       */
    public static Socket do_pasv(BufferedReader socketIn, PrintWriter out) throws IOException {

        input_message(out, "PASV");

        Socket data_transfer = null;
        String server_output = null;

        String code = null;
        server_output = get_message(socketIn);
        if (server_output != null)
            code = get_code(server_output);

        if (code.equals("227")) {

            String ip_address = server_output.substring(server_output.indexOf("(") + 1, server_output.indexOf(")"));
            //            System.out.println(ip_address);
            String new_address = "";

            for (int i = 0; i < 4; i++) {
                String temp = ip_address.substring(0, ip_address.indexOf(","));
                ip_address = ip_address.substring(ip_address.indexOf(",") + 1);
                new_address += temp + ".";
            }

            String e = ip_address.substring(0, ip_address.indexOf(","));
            ip_address = ip_address.substring(ip_address.indexOf(",") + 1);
            String f = ip_address;
            int port_number = Integer.parseInt(e) * 256 + Integer.parseInt(f);
            new_address = new_address.substring(0, new_address.length() - 1);
            try {
                data_transfer = new Socket(new_address, port_number);
            } catch (Exception exception) {
                System.out.println(
                        "0x3A2 Data transfer connection to" + new_address + "on port" + port_number + "failed to open");
            }
        }
        return data_transfer;
    }

    /* print out the message received by a socket and return the message
       @param BufferReader socketIn: a BufferReader that connects to a socket

*/

    public static String get_message(BufferedReader socketIn) throws IOException {

        String server_output = null;

        while (true) {

            for (int i = 0; i < 500000; i++) {
                if (socketIn.ready()) {
                    break;
                }
            }

            if (socketIn.ready()) {
                if ((server_output = socketIn.readLine()) != null) {
                    System.out.println("<-- " + server_output);
                }
            } else {
                break;
            }
        }
        return server_output;

    }

    /* get the respond code from the input string and return it
       @ param String input: the string to get a code from

*/

    public static String get_code(String input) {
        if (input != null)
            return input.substring(0, input.indexOf(" "));
        else
            return null;
    }

    /* use the printwriter that connects to the socket to write the commands and print out the commands
       @ param PrintWriter out : A printerWriter that connects to a socket
       @ param output: commands to write to the socket

*/

    public static void input_message(PrintWriter out, String output) {
        out.println(output);
        out.flush();
        System.out.println("--> " + output);
    }

}
