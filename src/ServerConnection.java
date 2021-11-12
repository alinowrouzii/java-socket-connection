import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class ServerConnection implements Runnable {

    private Socket client;
    private BufferedReader in;

    public ServerConnection(Socket client) throws IOException {
        this.client = client;
        this.in = new BufferedReader(new InputStreamReader(client.getInputStream()));
    }
    @Override
    public void run() {
        try{
            while(true){
                String line = in.readLine();

                if(line == null){
                    break;
                }
                System.out.println(line);
            }
        }catch(IOException e){
            System.err.println(e);
        }finally{
            try{
                in.close();
            }catch(IOException e){
                System.err.println(e);
            }
        }
    }
}
