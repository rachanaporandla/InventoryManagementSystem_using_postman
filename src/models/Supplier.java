package models;

public class Supplier {
    private final int supplierID;
    private final String name;
    private final String contact;
    private final String email;
    private final String address;

    public Supplier(int supplierID, String name, String contact, String email, String address) {
        this.supplierID = supplierID;
        this.name = name;
        this.contact = contact;
        this.email = email;
        this.address = address;
    }
    public int getSupplierID() { return supplierID; }
    public String getName() { return name; }
    public String getContact() { return contact; }
    public String getEmail() { return email; }
    public String getAddress() { return address; }
    public void display() {
        System.out.println("SupplierID: " + supplierID +
                ", Name: " + name +
                ", Contact: " + contact +
                ", Email: " + email +
                ", Address: " + address);
    }
}
