package dao;
import db.DBConnection;
import models.Product;
import java.sql.*;
import java.util.HashMap;

public class ProductDAO {
    public boolean addProduct(Product product) {
        String sql = "INSERT INTO products (supplierID, name, price, quantity, category) VALUES (?, ?, ?, ?, ?)";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, product.getSupplierID());
            preparedStatement.setString(2, product.getName());
            preparedStatement.setDouble(3, product.getPrice());
            preparedStatement.setInt(4, product.getQuantity());
            preparedStatement.setString(5, product.getCategory());
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    public boolean updateQuantity(int productID, int quantity) {
        String sql = "UPDATE products SET quantity = ? WHERE productID = ?";
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, quantity);
            preparedStatement.setInt(2, productID);
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    public boolean deleteProduct(int productID) {
        String sql = "DELETE FROM products WHERE productID = ?";
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, productID);
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    public HashMap<Integer, Product> getAllProducts() {
        HashMap<Integer, Product> productMap = new HashMap<>();
        String sql = "SELECT * FROM products";
        try (Connection connection = DBConnection.getConnection();
             Statement statement = connection.createStatement();
             ResultSet rs = statement.executeQuery(sql)) {
            while (rs.next()) {
                Product p = new Product(
                        rs.getInt("productID"),
                        rs.getInt("supplierID"),
                        rs.getString("name"),
                        rs.getDouble("price"),
                        rs.getInt("quantity"),
                        rs.getString("category")
                );
                productMap.put(p.getProductID(), p);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return productMap;
    }
}

