import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;

public class CSVMaker {

    private static final String FILE_HEADER = "nodeID,xcoord,ycoord,floor,building,nodeType,longName,shortName";
    private static final String COMMA_DELIMITER = ",";
    private static final String NEW_LINE_SEPARATOR = "\n";

    /**
     * makeCSVFile((LinkedList<Node> nodeList) - makes a new csv and populates it with a given list of nodes
     * also creates its own header to the csv
     * @param nodeList list of nodes used to populate the new csv
     */
    public static void makeCSVFile(LinkedList<Node> nodeList)
    {
        FileWriter fw = null;

        String fileName =  System.getProperty("user.dir")+"/newCSV.csv";

        try {
            fw = new FileWriter(fileName);

            //Write the CSV file header
            fw.append(FILE_HEADER.toString());

            //Add a new line separator after the header
            fw.append(NEW_LINE_SEPARATOR);

            //Write a new student object list to the CSV file
            for (Node n: nodeList) {
                fw.append(n.getNodeID());
                fw.append(COMMA_DELIMITER);
                fw.append(String.valueOf(n.getXCoord()));
                fw.append(COMMA_DELIMITER);
                fw.append(String.valueOf(n.getYCoord()));
                fw.append(COMMA_DELIMITER);
                fw.append(String.valueOf(n.getFloor()));
                fw.append(COMMA_DELIMITER);
                fw.append(n.getBuilding());
                fw.append(COMMA_DELIMITER);
                fw.append(n.getNodeType());
                fw.append(COMMA_DELIMITER);
                fw.append(n.getLongName());
                fw.append(COMMA_DELIMITER);
                fw.append(n.getShortName());
                fw.append(NEW_LINE_SEPARATOR);
            }

            System.out.println("CSV file was created successfully !!!");

        } catch (Exception e) {
            System.out.println("Error in CsvFileWriter !!!");
            e.printStackTrace();
        } finally {
            // if the file is created the fileWriter should be destroyed
            try {
                fw.flush();
                fw.close();
            } catch (IOException | NullPointerException e) {
                System.out.println("Error while flushing/closing fileWriter !!!");
                e.printStackTrace();
            }
        }
    }
}





