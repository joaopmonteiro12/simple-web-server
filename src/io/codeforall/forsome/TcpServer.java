package io.codeforall.forsome;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class TcpServer {

    public static void main(String[] args) {

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));


        try {
            System.out.print("Port: ");
            int serverPortNumber = Integer.parseInt(reader.readLine());

            ServerSocket serverSocket = new ServerSocket(serverPortNumber);


            while (true) {
                Socket clientSocket = serverSocket.accept();

                InputStreamReader inputStreamReader = new InputStreamReader(clientSocket.getInputStream());
                BufferedReader in = new BufferedReader(inputStreamReader);

                String[] info = in.readLine().split(" ");
                String path = info[1];

                if (path.equals("/index.html")) {

                    File index = new File("src/resources/index.html");

                    BufferedReader indexReader = new BufferedReader(new FileReader(index));

                    String header200document = "HTTP/1.0 200 Document Follows\r\n" +
                            "Content-Type: text/html; charset=UTF-8\r\n" +
                            "Content-Length: " + index.length() + "\r\n" +
                            "\r\n";

                    String line = "";
                    String result = header200document;

                    while ((line = indexReader.readLine()) != null) {
                        result += line + "\n";
                    }

                    System.out.println(header200document);

                    PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
                    out.println(result);
                    out.close();
                    indexReader.close();
                }

                if (path.equals("/404.html")) {

                    File notFound = new File("src/resources/404.html");

                    BufferedReader indexReader = new BufferedReader(new FileReader(notFound));

                    String header404 = "HTTP/1.0 404 Not Found" +
                            "Content-Type: text/html; charset=UTF-8\r\n" +
                            "Content-Length: " + notFound.length() + "\r\n" +
                            "\r\n";

                    String line = "";
                    String result = header404;

                    while ((line = indexReader.readLine()) != null) {
                        result += line + "\n";
                    }

                    System.out.println(header404);

                    PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
                    out.println(result);
                    out.close();
                    indexReader.close();
                }

                if (path.equals("/logo.png")) {
                    File logo = new File("src/resources/logo.png");
                    FileInputStream inputStream = new FileInputStream(logo);

                    byte[] imageData = inputStream.readAllBytes();

                    String header200image = "HTTP/1.0 200 Document Follows\r\n" +
                            "Content-Type: image/png\r\n" +
                            "Content-Length: " + logo.length() + "\r\n" +
                            "\r\n";

                    byte[] headerData = header200image.getBytes();

                    System.out.println(header200image);
                    inputStream.close();

                    DataOutputStream out = new DataOutputStream(clientSocket.getOutputStream());

                    out.write(headerData, 0, headerData.length);
                    out.write(imageData, 0, imageData.length);
                    out.flush();
                    out.close();
                    inputStream.close();
                }

                if (path.equals("/favicon.ico")) {
                    File icon = new File("src/resources/favicon.ico");
                    FileInputStream inputStream = new FileInputStream(icon);

                    byte[] imageData = inputStream.readAllBytes();

                    String header200image = "HTTP/1.0 200 Document Follows\r\n" +
                            "Content-Type: image/vnd.microsoft.icon\r\n" +
                            "Content-Length: " + icon.length() + "\r\n" +
                            "\r\n";

                    byte[] headerData = header200image.getBytes();

                    System.out.println(header200image);
                    inputStream.close();

                    DataOutputStream out = new DataOutputStream(clientSocket.getOutputStream());

                    out.write(headerData, 0, headerData.length);
                    out.write(imageData, 0, imageData.length);
                    out.flush();
                    out.close();
                    inputStream.close();
                }
                clientSocket.close();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }

}
