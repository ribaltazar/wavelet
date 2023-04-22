import java.io.IOException;
import java.net.URI;

public class StringServer{
    String runningString = " ";
    String message;

    public static void main(String[] args)throws IOException {
        if(args.length == 0){
            
        }
    }

    public String addMessage(URI url){
         message = url.getQuery();
         runningString += "\n" + message;
         return runningString;
    }

}