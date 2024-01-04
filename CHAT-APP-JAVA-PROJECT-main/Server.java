//import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.*;
public class Server{
    ServerSocket serverSocket;
    Socket socket;
    BufferedReader br;
    PrintWriter out;
    public Server(){

        try {
            serverSocket=new ServerSocket(7777);
            System.out.println("Server is ready to accept the connection ");
            System.out.println("waiting...");
            socket=serverSocket.accept();

            br=new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out= new PrintWriter(socket.getOutputStream());
            startReading();
            startWriting();
    
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
    }
    public void startReading(){
        Runnable r1=()->{
          System.out.println("reader strated");

          try{ 

          while(true){
           
            String msg=br.readLine();
            if(msg.equals("exit")){
                System.out.println("Client terminated the chat");
                
                
                socket.close();
                break;
            }
            System.out.println("client: "+msg);
        
          }
        }catch(Exception e){
            //e.printStackTrace();
            System.out.println("connection is closed..");
        }

        };
        new Thread(r1).start();

    }
    public void startWriting(){

        Runnable r2=()->{
            System.out.println("writer started...");

            try{ 
            while(true && !socket.isClosed()){
                
                    BufferedReader br1 = new BufferedReader(new InputStreamReader(System.in));
                    String content = br1.readLine();


                    

                    out.println(content);
                    out.flush();

                    if(content.equals("exit")){
                        socket.close();
                        break;
                    }
                
            }

        } catch (Exception e) {
            // TODO: handle exception
           // e.printStackTrace();
           System.out.println("connection is closed..");

        }

        
        };
        new Thread(r2).start();
    }


    public static void main(String[] args) {
        System.out.println("Server is  getting ready...! ");
        System.out.println("please wait.....!");
        new Server();
    }
}
