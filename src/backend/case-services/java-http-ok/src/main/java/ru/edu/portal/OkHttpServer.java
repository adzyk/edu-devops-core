package ru.edu.portal;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.InetSocketAddress;
import java.util.logging.Logger;


public class OkHttpServer {
    private final static Logger LOG = Logger.getLogger("NOFRAMEWORKS");

    public static void main(String[] args) throws IOException {

        final int port = Integer.parseInt(System.getProperty("appPort", "8500"));


        final HttpServer server = HttpServer.create(new InetSocketAddress(port), 50);
        server.createContext("/").setHandler(new Root());
        server.createContext("/health").setHandler(new Health());

        server.start();
    }

    private static class Root implements HttpHandler {

        public Root() {}

        @Override
        public void handle(HttpExchange exchange) throws IOException {
            exchange.getResponseHeaders().add("Content-Type", "text/html");
            exchange.sendResponseHeaders(200, 0);
            var responseBody = exchange.getResponseBody();
            responseBody.write("<html><body><h1>OK</h1></body></html>".getBytes());
            exchange.getResponseBody().close();
            LOG.info(String.format("[GET] / CODE: 200 - %s", exchange.getRequestHeaders().get("User-Agent")));
        }
    }

    private static class Health implements HttpHandler {

        public Health() {}

        @Override
        public void handle(HttpExchange exchange) throws IOException {
            exchange.getResponseHeaders().add("Content-Type", "text/html");
            exchange.sendResponseHeaders(200, 0);
            exchange.getResponseBody().close();
            LOG.info(String.format("[GET] /health CODE: 200 - %s", exchange.getRequestHeaders().get("User-Agent")));
        }
    }
}
