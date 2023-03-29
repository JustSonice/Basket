import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {

        String[] products = {"Хлеб", "Яблоки", "Молоко"};
        int[] prices = {100, 200, 300};
        Scanner scanner = new Scanner(System.in);

        File saveFile = new File("Basket.bin");

        Basket basket = null;
        if (saveFile.exists()){
            basket = Basket.loadFromBin(saveFile);
        } else {
            basket = new Basket(prices, products);
        }

        System.out.println("Список возможных товаров для покупки");
        for (int i = 0; i < products.length; i++) {
            if (products[i].contains("Хлеб")) {
                System.out.println("1 " + products[i] + " " + prices[i] + " руб/шт");
            } else if (products[i].contains("Яблоки")) {
                System.out.println("2 " + products[i] + " " + prices[i] + " руб/шт");
            } else {
                System.out.println("3 " + products[i] + " " + prices[i] + " руб/шт");
            }
        }
        int[] totalCount = new int[3];

        while (true) {
            System.out.println("Выберите товар и количество или введите `end`");
            String input = scanner.nextLine();
            if (input.equals("end")) {
                break;
            }

            String[] partsSplit = input.split(" ");
            int productNumber = Integer.parseInt(partsSplit[0]) - 1;
            int productCount = Integer.parseInt(partsSplit[1]);
            basket.addToCart(productNumber, productCount);
            basket.saveBin(saveFile);

            totalCount[productNumber] += productCount;
        }
        basket.printCart();
    }
}