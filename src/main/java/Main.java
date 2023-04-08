import org.xml.sax.SAXException;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException, ParserConfigurationException, SAXException {
        XMLSetReader settings = new XMLSetReader(new File("shop.xml"));
        File loadFile = new File(settings.loadFile);
        File saveFile = new File(settings.loadFile);
        File logFile = new File(settings.logFile);


        String[] products = {"Хлеб", "Яблоки", "Молоко"};
        int[] prices = {100, 200, 300};
        Scanner scanner = new Scanner(System.in);

        Basket basket = null;
        if (saveFile.exists()) {
            basket = Basket.loadFromJSONFile(saveFile);
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

        Basket basket = createBasket(loadFile, settings.isLoad, settings.loadFormat);
        ClientLog log = new ClientLog();
        while (true) {
            System.out.println("Выберите товар и количество или введите `end`");
            String input = scanner.nextLine();
            if (input.equals("end")) {
                if (settings.isLoad) {
                    log.exportAsCSV(logFile);
                    break;
                }

                String[] partsSplit = input.split(" ");
                int productNumber = Integer.parseInt(partsSplit[0]) - 1;
                int productCount = Integer.parseInt(partsSplit[1]);
                basket.addToCart(productNumber, productCount);
                if (settings.isLog) {
                    log.log(productNumber, productCount);
                } if (settings.isSave) {
                    switch (settings.saveFormat){
                        case "json" -> basket.saveJSON(saveFile);
                        case "txt" -> basket.saveTxt(saveFile);
                    }
                }
                basket.saveJSON(saveFile);

                totalCount[productNumber] += productCount;
            }
            basket.printCart();
        }

        private static Basket createBasket (File loadFile, boolean isLoad, String loadFormat) {
            Basket basket;
            if (isLoad && loadFile.exists()) {
                basket = switch (loadFormat) {
                    case "json" -> Basket.loadFromJSONFile(loadFile);
                    case "txt" -> Basket.loadFromTxtFile();
                    default -> new Basket(prices,products);
                };
            } else {
                basket = new Basket(prices, products);
            }
            return basket;
        }
    }
}