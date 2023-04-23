import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.URI;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

public class StringServer {
    
    private static String message = "";
    
    public static void main(String[] args) throws Exception {
        if(args.length == 0){
            System.out.println("Missing port number! Try any number");
            return;
        }
        int port = Integer.parseInt(args[0]);
        
        HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);
        server.createContext("/add-message", new MyHandler());
        server.setExecutor(null);
        server.start();
        System.out.println("Server Started! Visit http://localhost:" + port + "/add-message?s=");
    }
    
    static class MyHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange t) throws IOException {
            String response = "";
            URI uri = t.getRequestURI();
            String query = uri.getQuery();
            String[] keyValue = query.split("=");
            
            if (keyValue.length == 2 && keyValue[1] != null && !keyValue[1].isEmpty()) {
                message += "\n" + keyValue[1];
            }
            
            response = message;

            t.sendResponseHeaders(200, response.length());
            OutputStream os = t.getResponseBody();
            os.write(response.getBytes());
            os.close();
        } 
    }
}