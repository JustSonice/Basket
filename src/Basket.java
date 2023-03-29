import java.io.*;
import java.util.Arrays;

public class Basket implements Serializable {

    private static final long serialVersionUID = 1L;
    protected int[] prices;
    protected String[] products;
    protected int[] quantities;

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

    public void saveTxt(File textFile) {
        try (PrintWriter out = new PrintWriter(textFile)) {
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

    public static Basket loadFromTxtFile(File textFile) throws IOException {
        Basket basket = new Basket();
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(textFile))) {
            String productS = bufferedReader.readLine();
            String pricesS = bufferedReader.readLine();
            String quantitiesS = bufferedReader.readLine();

            basket.products = productS.split(" ");
            basket.prices = Arrays.stream(pricesS.split(" "))
                    .map(Integer::parseInt)
                    .mapToInt(i -> i)
                    .toArray();

            basket.quantities = Arrays.stream(quantitiesS.split(" "))
                    .map(Integer::parseInt)
                    .mapToInt(i -> i)
                    .toArray();
        }
        return basket;
    }

    public void saveBin(File file) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file))) {
            oos.writeObject(this);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static Basket loadFromBin(File file) {
        Basket basket = null;
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            basket = (Basket) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return basket;
    }
}
