package models;

public class Product {
    private final int productID;
    private final int supplierID;
    private final String name;
    private final double price;
    private int quantity;
    private final String category;

    public Product(int productID, int supplierID, String name, double price, int quantity, String category) {
        this.productID = productID;
        this.supplierID = supplierID;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.category = category;
    }
    public int getProductID() { return productID; }
    public int getSupplierID() { return supplierID; }
    public String getName() { return name; }
    public double getPrice() { return price; }
    public int getQuantity() { return quantity; }
    public String getCategory() { return category; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
    public void display() {
        System.out.println("ProductID: " + productID +
                ", Name: " + name +
                ", Price: " + price +
                ", Quantity: " + quantity +
                ", Category: " + category +
                ", SupplierID: " + supplierID);
    }
}

