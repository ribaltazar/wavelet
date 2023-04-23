import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.URI;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

interface StringURLHandler {
    String addMessage(URI url);
}

class StringServerHttpHandler implements HttpHandler{
    StringURLHandler messageRequest;
    StringServerHttpHandler(StringURLHandler messageRequest){
        this.messageRequest = messageRequest;
    }
    public void handle(final HttpExchange exchange) throws IOException{
        try{
            String ret = messageRequest.addMessage(exchange.getRequestURI());
            exchange.sendResponseHeaders(200, ret.getBytes().length);
            OutputStream os = exchange.getResponseBody();
            os.write(ret.getBytes());
            os.close();
        } catch(Exception e){
            String response = e.toString();
            exchange.sendResponseHeaders(500, response.getBytes().length);
            OutputStream os = exchange.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }
    }
}

public class strServer {
    public static void start(int port, StringURLHandler messageRequest) throws IOException{
        HttpServer strServer = HttpServer.create(new InetSocketAddress(port), 0);

        strServer.createContext("/", new StringServerHttpHandler(messageRequest));

        strServer.start();
        System.out.println("Server Started! Visit http://localhost:" + port + " to visit.");
    }
}