package org.example.Client_Server;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import org.example.DTOs.Game_Information;

import java.awt.*;
import java.io.*;
import java.lang.reflect.Type;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.Buffer;
import java.util.Collection;
import java.util.Scanner;

public class Client
{
    public static void main(String[] args) {
        Client client = new Client();
        client.start();
    }

    public void start() {

        try (   //New socket to connect to server
                Socket socket = new Socket("localhost", 8888);
                // get the socket's input and output streams, and wrap them in writer and readers
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        ) {
            System.out.println("Client message: The Client is running and has connected to the server");
            //Setting up to take input from user.
            Scanner consoleInput = new Scanner(System.in);
            System.out.println("Valid commands are: Type 1 to Display Entity by Id ,Type 2 to Display all Entities,Type 3 to “Add an Entity");
            System.out.println("Please enter a command: ");
            String userRequest = consoleInput.nextLine();
            // Instantiate (create) a Gson Parser
            Gson gsonParser = new Gson();
            while(true) {
                // sending the command to the server on the socket
                out.println(userRequest);      // write the request to socket along with a newline terminator (which is required)

                // process the answer returned by the server
                //
                //COMMAND 1 to Display Entity by Id
                //If users request is 1
                if (userRequest.equals("1")) {
                    String JsonGameId = in.readLine();  // gets response from server and then we get JSON and put it into the string
                    //We then convert this JSON to a gameinfo object
                    //System.out.println("Client message: Response from server after \"1\" request: " + JsonGameId);
                    Game_Information game = null;
                    //Parsing the JSON string into a gameInformation object.
                    try {
                        game = gsonParser.fromJson(JsonGameId, Game_Information.class);
                    }
                    catch (JsonSyntaxException ex) {
                        System.out.println("Jason syntax error encountered. " + ex);
                    }
                    System.out.println("Your game is " + game);
                }
                else if(userRequest.equals("2")){
                    String JsonGameId = in.readLine();

                    try {        //We get an array of objects instead of 1 object.
                        Type collectionClient = new TypeToken<Collection<Game_Information>>(){}.getType();      //Created type object of Game_Information Collections- Making the empty variable a base of Game_Information
                        Collection<Game_Information> game = gsonParser.fromJson(JsonGameId, collectionClient);  //Filling in the Collection with allocated variables from the server
                        System.out.println(game);
                    } catch (JsonSyntaxException e) {
                        throw new RuntimeException(e);
                    }


                }
                else if (userRequest.equals("3")){

                }
                else if (userRequest.equals("4")){
                    System.out.println("What game would you like to DELETE: ");
                    Scanner kb = new Scanner(System.in);
                    String JsonGameId = in.readLine();


                    try(BufferedOutputStream bos = new BufferedOutputStream(socket.getOutputStream())) {

                        int deleteId = kb.nextInt();

                        bos.write((deleteId ));
                        bos.flush();

                        Type collectionClient = new TypeToken<Collection<Game_Information>>(){}.getType();
                        Collection<Game_Information> game = gsonParser.fromJson(JsonGameId, collectionClient);
                        System.out.println(game);


                    }
                    catch (JsonSyntaxException ex) {
                        System.out.println("Jason syntax error encountered. " + ex);
                    }



                }
                else
                {
                    System.out.println("Not a valid user request.");
                    System.out.println("Current userrequest whicih resulted in error:"+userRequest);
                }
                //Taking in input
                consoleInput = new Scanner(System.in);
                System.out.println("Valid commands are: Type 1 to Display Entity by Id ,Type 2 to Display all Entities,Type 3 to “Add an Entity");
                System.out.println("Please enter a command: ");
                userRequest = consoleInput.nextLine();
            }
        } catch (IOException e) {
            System.out.println("Client message: IOException: " + e);
        }

        System.out.println("Exiting client, Server could still be running");
    }
}
