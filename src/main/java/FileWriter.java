import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.LinkedList;

import com.opencsv.CSVWriter;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;

public class FileWriter {

    //FileReader.deleteRow(nodeId)

    //public static void deleterRow(String nID) {
//        try {
//            CSVReader reader = new CSVReader(new FileReader("old.csv"));
//            CSVWriter writer = new CSVWriter(new FileWriter("new.csv"));
//            String[] nextLine;
//            while ((nextLine = reader.readNext()) != null) {
//                List<String> lineAsList = new ArrayList<String>(Arrays.asList(nextLine));
//                /* Add stuff using linesAsList.add(index, newValue) as many times as you need. */
//                writer.writeNext(lineAsList.toArray());
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }


   // }

//    public static void updateCSVFile()
//    {
//        //String csvPath = DBController.getPath();
//        String csvPath = "file:///" + System.getProperty("user.dir") +  "\\TempCSV.csv";
//
//        try (
//                Writer writer = Files.newBufferedWriter(Paths.get(csvPath));
//        ) {
//            StatefulBeanToCsv<Node> beanToCsv = new StatefulBeanToCsvBuilder(writer)
//                    .withQuotechar(CSVWriter.NO_QUOTE_CHARACTER)
//                    .build();
//
//            //List<MyUser> myUsers = new ArrayList<>();
//
//           // LinkedList<Node> nodeList = DBController.getNodeList();
//
//            List<Node> nodeList = new ArrayList<>();
//
//            nodeList.add(new Node("hall1", 34, 45, 4, "fuller", "hallway", "fullHall", "fullerHallway"));
//            //nodeList.add(new Node("Satya Nadella", "satya.nadella@outlook.com", "+1-1111111112", "India"));
//
//            beanToCsv.write(nodeList);
//        } catch (IOException | CsvDataTypeMismatchException |
//        CsvRequiredFieldEmptyException e)
//        {
//            e.printStackTrace();
//        }
//    }


}
