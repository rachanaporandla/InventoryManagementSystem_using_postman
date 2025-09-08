import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpExchange;
import com.google.gson.Gson;
import dao.ProductDAO;
import dao.SupplierDAO;
import models.Product;
import models.Supplier;
import java.io.*;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class InventoryAPI {
    static class UpdateQuantityRequest {
        int productID;
        int quantity;
    }
    public static void main(String[] args) throws IOException {
        ProductDAO productDAO = new ProductDAO();
        SupplierDAO supplierDAO = new SupplierDAO();
        Gson gson = new Gson();
        HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);
        server.createContext("/products", exchange -> {
            if ("GET".equalsIgnoreCase(exchange.getRequestMethod())) { sendStatus(exchange, 405); return; }
            sendJson(exchange, 200, gson.toJson(productDAO.getAllProducts().values()));
        });
        server.createContext("/addProduct", exchange -> {
            if ("POST".equalsIgnoreCase(exchange.getRequestMethod())) { sendStatus(exchange, 405); return; }
            Product p = gson.fromJson(readBody(exchange), Product.class);
            boolean ok = (p != null) && productDAO.addProduct(p);
            sendJson(exchange, ok ? 200 : 400, "{\"success\":" + ok + "}");
        });
        server.createContext("/updateProduct", exchange -> {
            if ("PUT".equalsIgnoreCase(exchange.getRequestMethod())) { sendStatus(exchange, 405); return; }
            UpdateQuantityRequest req = gson.fromJson(readBody(exchange), UpdateQuantityRequest.class);
            boolean ok = (req != null) && productDAO.updateQuantity(req.productID, req.quantity);
            sendJson(exchange, ok ? 200 : 400, "{\"success\":" + ok + "}");
        });
        server.createContext("/deleteProduct", exchange -> {
            if ("DELETE".equalsIgnoreCase(exchange.getRequestMethod())) { sendStatus(exchange, 405); return; }
            Integer id = getIntQueryParam(exchange, "id");
            boolean ok = (id != null) && productDAO.deleteProduct(id);
            sendJson(exchange, ok ? 200 : 400, "{\"success\":" + ok + "}");
        });
        server.createContext("/suppliers", exchange -> {
            if ("GET".equalsIgnoreCase(exchange.getRequestMethod())) { sendStatus(exchange, 405); return; }
            sendJson(exchange, 200, gson.toJson(supplierDAO.getAllSuppliers().values()));
        });
        server.createContext("/addSupplier", exchange -> {
            if ("POST".equalsIgnoreCase(exchange.getRequestMethod())) { sendStatus(exchange, 405); return; }
            Supplier s = gson.fromJson(readBody(exchange), Supplier.class);
            boolean ok = (s != null) && supplierDAO.addSupplier(s);
            sendJson(exchange, ok ? 200 : 400, "{\"success\":" + ok + "}");
        });
        server.setExecutor(null);
        server.start();
        System.out.println(" API is running on http://localhost:8080");
    }
    private static void sendStatus(HttpExchange exchange, int code) throws IOException {
        exchange.sendResponseHeaders(code, -1);
        exchange.close();
    }
    private static void sendJson(HttpExchange exchange, int code, String body) throws IOException {
        exchange.getResponseHeaders().set("Content-Type", "application/json");
        byte[] out = body.getBytes(StandardCharsets.UTF_8);
        exchange.sendResponseHeaders(code, out.length);
        try (OutputStream os = exchange.getResponseBody()) { os.write(out); }
    }
    private static String readBody(HttpExchange exchange) throws IOException {
        try (InputStream is = exchange.getRequestBody()) {
            return new String(is.readAllBytes(), StandardCharsets.UTF_8);
        }
    }
    private static Integer getIntQueryParam(HttpExchange exchange, String key) {
        String q = exchange.getRequestURI().getQuery();
        if (q == null) return null;
        for (String pair : q.split("&")) {
            String[] keyvalue = pair.split("=", 2);
            if (keyvalue.length == 2 && keyvalue[0].equals(key)) {
                try { return Integer.parseInt(keyvalue[1]); } catch (NumberFormatException ignored) {}
            }
        }
        return null;
    }
}
