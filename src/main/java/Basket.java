import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.*;
import java.util.Arrays;

public class Basket {
    int[] prices;
    String[] products;
    int[] quantities;

    public Basket(int[] prices, String[] products) {
        this.prices = prices;
        this.products = products;
        this.quantities = new int[products.length];
    }

    public Basket() {

    }

    public void addToCart(int productNumber, int productCount) {
        quantities[productNumber] += productCount;
    }

    public void printCart() {
        int totalPrice = 0;
        System.out.println("Лист покупок");
        for (int i = 0; i < products.length; i++) {
            if (quantities[i] > 0) {
                int currentPrice = prices[i] * quantities[i];
                totalPrice += currentPrice;
                System.out.println(products[i] + " " + +quantities[i] + " шт " + prices[i] + " руб/шт " + currentPrice + " руб в сумме");
            }
        }
        System.out.println("Итог: " + totalPrice + " руб");
    }

    public void saveJSON(File textFile) {
        try (PrintWriter out = new PrintWriter(textFile)) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            String json = gson.toJson(this);
            out.print(json);
            for (String product : products) {
                out.print(product + " ");
            }
            out.println();

            for (int price : prices) {
                out.print(price + " ");
            }
            out.println();

            for (int quantity : quantities) {
                out.print(quantity + " ");
            }
            out.println();

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static Basket loadFromJSONFile(File textFile) throws IOException {
        Basket basket = new Basket();
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(textFile))) {
            StringBuilder builder = new StringBuilder();
            String line = null;
            while ((line = bufferedReader.readLine()) != null) {
                builder.append(line);
            }
            Gson gson = new Gson();
            basket = gson.fromJson(builder.toString(), Basket.class);
        }
        return basket;
    }
}
