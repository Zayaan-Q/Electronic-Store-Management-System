import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Comparator;
public class ElectronicStore {
    public final int MAX_PRODUCTS = 10; //Maximum number of products the store can have
    private int curProducts;
    private String name;
    private Map<Product, Integer> Products;
    private double revenue;
    private List<Customer> Customers;

    public ElectronicStore(String initName) {
        revenue = 0.0;
        name = initName;
        Products = new HashMap<>();
        curProducts = 0;
        Customers = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    //Adds a product and returns true if there is space in the array
    //Returns false otherwise
    public boolean addProduct(Product newProduct) {
        for (Product p : Products.keySet()) {
            if (p.toString().equalsIgnoreCase(newProduct.toString())) {
                return false;
            }
        }
        if (curProducts < MAX_PRODUCTS) {
            Products.put(newProduct, 0);
            curProducts++;
            return true;
        }
        return false;
    }

    public boolean registerCustomer(Customer customer) {
        for (Customer c : Customers) {
            if (c.getName().equalsIgnoreCase(customer.getName())) {
                return false;
            }
        }
        Customers.add(customer);
        return true;
    }

    public List<Customer> getCustomers() {
        return new ArrayList<>(Customers);
    }

    public List<Product> searchProducts(String x) {
        List<Product> found_Products = new ArrayList<>();
        for (Product p : Products.keySet()) {
            if (p.toString().toLowerCase().contains(x.toLowerCase())) {
                found_Products.add(p);
            }
        }
        return found_Products;
    }


    public List<Product> searchProducts(String x, double minPrice, double maxPrice) {
        List<Product> foundProducts = new ArrayList<>();

        for (Product p : Products.keySet()) {
            x = x.toLowerCase();
            if (p.toString().toLowerCase().contains(x)) {
                double productPrice = p.getPrice();
                if ((minPrice >= 0 && maxPrice < 0 && productPrice >= minPrice) ||
                        (maxPrice >= 0 && minPrice < 0 && productPrice <= maxPrice) ||
                        (maxPrice > 0 && minPrice > 0 && minPrice <= productPrice && productPrice <= maxPrice)) {
                    foundProducts.add(p);
                }

            }
        }
        return foundProducts;
    }

    public boolean addStock(Product p, int amount) {
        if (Products.containsKey(p)) {
            int currentStock = Products.get(p);
            Products.put(p, currentStock + amount);
            return true;
        }
        return false;
    }

    public boolean sellProduct(Product p, Customer c, int amount) {
        if (!Products.containsKey(p) || !Customers.contains(c) || Products.get(p) < amount) {
            return false;
        }
        Products.put(p, Products.get(p) - amount);
        double saleAmount = p.getPrice() * amount;
        c.addToPurchaseHistory(p, amount);
        revenue += saleAmount;
        return true;
    }

    // Returns the revenue of the store
    public double getRevenue() {
        return revenue;
    }

    public List<Customer> getTopXCustomers(int x) {
        List<Customer> topCustomers = new ArrayList<>(Customers);

        // Sort the list of customers by their spending in descending order
        topCustomers.sort(new Comparator<Customer>() {
            @Override
            public int compare(Customer c1, Customer c2) {
                return Double.compare(c2.MoneySpent(), c1.MoneySpent());
            }
        });

        // Return top X customers or all customers if X is greater than the number of customers
        return x <= 0 ? new ArrayList<>() : topCustomers.subList(0, Math.min(x, topCustomers.size()));
    }
}


