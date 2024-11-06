import java.util.HashMap;
import java.util.Map;
public class Customer {
    private String name;
    private Map<Product, Integer> purchaseHistory;

    public Customer(String name) {
        this.name = name;
        purchaseHistory = new HashMap<>();
    }

    public String toString() {
        return "[" + name + "]" + " who has spent $[" + MoneySpent() + "]";
    }

    public void printPurchaseHistory() {
        for (Map.Entry<Product, Integer> entry : purchaseHistory.entrySet()) {
            System.out.printf("%d x %s%n", entry.getValue(), entry.getKey());
        }
    }

    public String getName() {
        return name;
    }
    public double MoneySpent(){
        double total_price = 0;
        for (Map.Entry<Product, Integer> entry : purchaseHistory.entrySet()) {
            Product product = entry.getKey();
            int quantity = entry.getValue();
            total_price = total_price+ product.getPrice() * quantity;
        }
        return total_price;
    }
    public void addToPurchaseHistory(Product product, int quantity) {
        purchaseHistory.put(product, quantity);
    }

}


