import models.Product;
import models.Supplier;
import dao.ProductDAO;
import dao.SupplierDAO;
import java.util.HashMap;
import java.util.Scanner;
public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        ProductDAO productdao = new ProductDAO();
        SupplierDAO supplierdao = new SupplierDAO();
        while (true) {
            System.out.println("\n Inventory Management ");
            System.out.println("1. Add Supplier");
            System.out.println("2. View All Suppliers");
            System.out.println("3. Add Product");
            System.out.println("4. View All Products");
            System.out.println("5. Update Product Quantity");
            System.out.println("6. Delete Product");
            System.out.println("7. Exit");
            System.out.print("Choose option: ");
            int choice = Integer.parseInt(sc.nextLine());
            if (choice == 1) {
                System.out.print("Enter Supplier Name: ");
                String sName = sc.nextLine();
                System.out.print("Enter Contact: ");
                String sContact = sc.nextLine();
                System.out.print("Enter Email: ");
                String sEmail = sc.nextLine();
                System.out.print("Enter Address: ");
                String sAddress = sc.nextLine();
                Supplier supplier = new Supplier(0, sName, sContact, sEmail, sAddress);
                if (supplierdao.addSupplier(supplier)) {
                    System.out.println("Supplier added successfully!");
                } else {
                    System.out.println("Failed to add supplier.");
                }
            } else if (choice == 2) {
                HashMap<Integer, Supplier> suppliers = supplierdao.getAllSuppliers();
                System.out.println("\n All Suppliers ");
                for (Supplier s : suppliers.values()) {
                    s.display();
                }
            } else if (choice == 3) {
                System.out.print("Enter Supplier ID: ");
                int supplierID = Integer.parseInt(sc.nextLine());
                System.out.print("Enter Product Name: ");
                String pName = sc.nextLine();
                System.out.print("Enter Price: ");
                double price = Double.parseDouble(sc.nextLine());
                System.out.print("Enter Quantity: ");
                int qty = Integer.parseInt(sc.nextLine());
                System.out.print("Enter Category: ");
                String category = sc.nextLine();
                Product product = new Product(0, supplierID, pName, price, qty, category);
                if (productdao.addProduct(product)) {
                    System.out.println("Product added successfully!");
                } else {
                    System.out.println("Failed to add product.");
                }
            } else if (choice == 4) {
                HashMap<Integer, Product> products = productdao.getAllProducts();
                System.out.println("\n All Products ");
                for (Product p : products.values()) {
                    p.display();
                }
            } else if (choice == 5) {
                System.out.print("Enter Product ID to update quantity: ");
                int updateID = Integer.parseInt(sc.nextLine());
                System.out.print("Enter new quantity: ");
                int newQty = Integer.parseInt(sc.nextLine());
                if (productdao.updateQuantity(updateID, newQty)) {
                    System.out.println("Stock updated!");
                } else {
                    System.out.println("Product not found.");
                }
            } else if (choice == 6) {
                System.out.print("Enter Product ID to delete: ");
                int deleteID = Integer.parseInt(sc.nextLine());
                if (productdao.deleteProduct(deleteID)) {
                    System.out.println("Product deleted!");
                } else {
                    System.out.println("Product not found.");
                }
            } else if (choice == 7) {
                System.out.println("Exiting!");
                sc.close();
                break;
            } else {
                System.out.println("Invalid choice!Try again.");
            }
        }
    }
}
