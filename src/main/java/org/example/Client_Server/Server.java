package org.example.Client_Server;

import org.example.DAOs.MySqlDao;
import org.example.DAOs.MySqlInfoDao;
import org.example.DTOs.Game_Information;
import org.example.JSonConverter.Json;
import org.example.Exceptions.DaoException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    
    final int SERVER_PORT = 8888;

    public static void main(String[] args) {
        Server server = new Server();
        server.start();
    }

    private void start() {
        ServerSocket serverSocket = null;
        Socket clientSocket = null;

        try{
            serverSocket = new ServerSocket(SERVER_PORT);
            System.out.println("Server Started!!!");
            int clientNumber = 0;

            while(true) {
                System.out.println("Server: Listening for connection on Port "+SERVER_PORT);
                clientSocket = serverSocket.accept();
                clientNumber++;
                System.out.println("Server: Listening for connection on Port "+SERVER_PORT);

                System.out.println("Server: Client "+clientNumber+" connected");
                System.out.println("Server: Port number of remote client: "+clientSocket.getPort());
                System.out.println("Server: Port number of the socket used to talk with client "+ clientSocket.getLocalPort());

                Thread t = new Thread(new ClientHandler(clientSocket, clientNumber));
                t.start();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    class ClientHandler implements Runnable{
        BufferedReader socketReader;
        PrintWriter socketWriter;
        Socket clientSocket;
        final int clientNumber;

        ClientHandler(Socket clientSocket, int clientNumber) {
            this.clientSocket = this.clientSocket;  // store socket for closing later
            this.clientNumber = clientNumber;  // ID number that we are assigning to this client

            try{
                // assign to fields
                this.socketWriter = new PrintWriter(this.clientSocket.getOutputStream(), true);
                this.socketReader = new BufferedReader(new InputStreamReader(this.clientSocket.getInputStream()));

            }
            catch(IOException ex){
                ex.printStackTrace();
            }
        }

        @Override
        public void run() {
            String request;
            try{
                while((request = socketReader.readLine()) != null)
                {
                    System.out.println("Server: (ClientHandler): Read command from client " + clientNumber+": "+ request);

                    //If client wants command 1 runs this etc...
                    if (request.equals("1"))
                    {
                        MySqlInfoDao infoDao =  new MySqlInfoDao();
                        Game_Information gameJson = infoDao.findGameById(3);
                        String gameListJSON = Json.singleGameToJson(gameJson);
                        socketWriter.println(gameListJSON);
                        System.out.println("Server message: JSON sent to client.");
                    }
                    else {
                        socketWriter.println("Error: invalid input!!!");
                        System.out.println("Your request "+ request+": Can't be fulfilled");
                    }
                }
            }  catch (DaoException ex)
            {
                throw new RuntimeException(ex);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            finally
            {
                this.socketWriter.close();
                try {
                    this.socketReader.close();
                    this.clientSocket.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
            System.out.println("Server: (ClientHandler): Handler for Client " + clientNumber + " is terminating .....");

        }
    }

}
