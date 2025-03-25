import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

class Product {
    private String name;
    private double price;
    public int quantity;

    public Product(String name, double price, int quantity) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    @Override
    public String toString() {
        return name + " - $" + price + " - Quantity: " + quantity;
    }
}

class SupplyChain {
    private ArrayList<Product> products = new ArrayList<>();
    private String dataFileName = "products.txt";

    public SupplyChain() {
        loadProductsFromFile();
    }

    public void addProduct(String name, double price, int quantity) {
        products.add(new Product(name, price, quantity));
        saveProductsToFile();
        System.out.println("Product added: " + name);
    }

    public void purchaseProduct(String name) {
        for (int i = 0; i < products.size(); i++) {
            if (products.get(i).getName().equalsIgnoreCase(name)) {
                Scanner scanner=new Scanner(System.in);
                System.out.print("Enter the quantity:");
                int purquan = scanner.nextInt();
                if(purquan<0)
                    System.out.println("Quantity cannot be negative");
                else{
                if (products.get(i).getQuantity() >= purquan) {
                    System.out.println("Product available.\n Congrats!! Placed an order.\nWait for further communication");
                    products.get(i).quantity -= purquan;// Reduce available quantity
                    if(products.get(i).quantity==0){products.remove(i);}
                    else{};
                    saveProductsToFile();
                } else {
                    System.out.println("Required quantity unavailable.");
                }
                
                }
                return;
            }
        }
        System.out.println("Product not found: " + name);
    }

    public int listProducts(int flag) {
        if (products.isEmpty()) {
            System.out.println("No products in the supply chain.");
            flag=1;
            return (flag);
        } 
        else {
            System.out.println("Products in the supply chain:");
            for (Product product : products) {
                System.out.println(product);
            }
            return (flag);
        }
    }

    private void saveProductsToFile() {
        try (FileOutputStream fos = new FileOutputStream(dataFileName);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(products);
        } catch (IOException e) {
        }
    }

    private void loadProductsFromFile() {
        try (FileInputStream fis = new FileInputStream(dataFileName);
             ObjectInputStream ois = new ObjectInputStream(fis)) {
            products = (ArrayList<Product>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            // Handle file not found or other errors
        }
    }
}

public class Main {
    public static void main(String[] args) {
        SupplyChain supplyChain = new SupplyChain();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\nSupply Chain Management Mode Menu:");
            System.out.println("1. Supplier");
            System.out.println("2. Purchaser");
            System.out.println("3. List Products");
            System.out.println("4. Exit");
            System.out.print("Enter your choice: ");

            int flag=0;
            int choice = scanner.nextInt();
            scanner.nextLine(); 
            
            switch (choice) {
                case 1:
                    System.out.print("Enter product name: ");
                    String productName = scanner.nextLine();
                    System.out.print("Enter product price: ");
                    double productPrice = scanner.nextDouble();
                    System.out.print("Enter the product quantity:");
                    int productQuantity = scanner.nextInt();
                    supplyChain.addProduct(productName, productPrice, productQuantity);
                    break;
                case 2:
                    flag=supplyChain.listProducts(flag);
                    if(flag==1){break;}
                    else{ 
                        System.out.print("Enter the name of the product to purchase: ");
                        String productToPurchase = scanner.nextLine();
                        supplyChain.purchaseProduct(productToPurchase);
                    }
                    break;
                case 3:
                    supplyChain.listProducts(flag);
                    break;
                case 4:
                    System.out.println("Exiting Supply Chain Management System.");
                    scanner.close();
                    System.exit(0);
                default:
                    if(choice<0){
                        System.out.println("Negative options cannot be given.Please enter a positive integer.");
                        break;
                    }
                    System.out.println("Invalid choice. Please enter a valid option.");
            }
        }
    }
}