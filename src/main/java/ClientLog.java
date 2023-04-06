import au.com.bytecode.opencsv.CSVWriter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ClientLog {

    private List<String[]> base = new ArrayList<>();


    public void log(int productNum, int amount) {
        base.add(new String[]{
                "" + productNum + amount
        });
    }

    public void exportAsCSV(File txtFile) {
        if (!txtFile.exists()) {
            base.add(0, new String[]{"productNum, amount"});
        }
        try (CSVWriter wrier = new CSVWriter(new FileWriter(txtFile, true))) {
            wrier.writeAll(base);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
