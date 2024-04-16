package org.example.Client_Server;

import org.example.DAOs.MySqlDao;
import org.example.DAOs.MySqlInfoDao;
import org.example.DTOs.Game_Information;
import org.example.JSonConverter.Json;
import org.example.Exceptions.DaoException;
import org.ietf.jgss.GSSManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

import static java.lang.System.in;

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

                System.out.println("Server: ClientHandler started in " + t.getName() + " for client " + clientNumber + ". ");
            }
        } catch (IOException ex) {
            System.out.println(ex);
        }
        finally{
            try {
                if(clientSocket!=null)
                    clientSocket.close();
            } catch (IOException e) {
                System.out.println(e);
            }
            try {
                if(serverSocket!=null)
                    serverSocket.close();
            } catch (IOException e) {
                System.out.println(e);
            }

        }
        System.out.println("Server Disconnecting!!!!");
    }

    class ClientHandler implements Runnable{
        BufferedReader socketReader;
        PrintWriter socketWriter;
        Socket clientSocket;
        final int clientNumber;

        ClientHandler(Socket clientSocket, int clientNumber) {
            this.clientSocket = clientSocket;  // store socket for closing later
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
            MySqlInfoDao infoDao =  new MySqlInfoDao();
            Game_Information gameJson = null;
            List<Game_Information> gameList;
            String gameListJSON;
            try{
                while((request = socketReader.readLine()) != null)
                {
                    System.out.println("Server: (ClientHandler): Read command from client " + clientNumber+": "+ request);

                    //If client wants command 1 runs this etc...
                    if (request.equals("1"))
                    {

                        gameJson = infoDao.findGameById(3);
                        gameListJSON = Json.singleGameToJson(gameJson);
                        socketWriter.println(gameListJSON);
                        System.out.println("Server message: JSON sent to client.");
                    }
                    else if (request.equals("2"))
                    {
                         gameList = infoDao.findAllGames();
                         gameListJSON = Json.gameListToJson(gameList);
                         socketWriter.println(gameListJSON);
                        System.out.println("Server message: Send Find all Entities in Database to Client");
                    }
                    else if (request.equals("3")){

                    }
                    else if (request.equals("4"))
                    {
                        Scanner in = new Scanner(System.in);
                        int deleteId = in.nextInt();
                        infoDao.deleteGameById(deleteId);
                        gameList = infoDao.findAllGames();
                        gameListJSON = Json.gameListToJson(gameList);
                        socketWriter.println(gameListJSON);
                    }
                    else {
                        socketWriter.println("Error: invalid input!!!");
                        System.out.println("Your request "+ request+": Can't be fulfilled");
                    }
                }
            }  catch (IOException ex) {
                ex.printStackTrace();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            } finally {
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
