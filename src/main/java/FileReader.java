import java.io.BufferedReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.LinkedList;
import main.java.Node;
import java.io.File;
import java.nio.file.Files;

public class FileReader
{
    //nodeID,
    // xcoord,
    // ycoord,
    // floor,
    // building,
    // nodeType,
    // longName,
    // shortName

    private static void connection()
    {
        try {
            // substitute your database name for myDB
            Connection connection = DriverManager.getConnection("jdbc:derby:myDB;create=true");
            // create table -- insert data


        } catch (SQLException e) {
            System.out.println("Connection failed. Check output console.");
            e.printStackTrace();
        }

    }

    private static LinkedList<Node> nodeList = new LinkedList<>();
    static void readFile()
    {
        connection();

        String fileName = "PrototypeNodes.csv";

        BufferedReader br;
        String line;
        String splitBy = ",";

        String tempNodeID;
        int tempXCoord; //long or int???
        int tempYCoord;
        int tempFloor;
        String tempBuilding;
        String tempNodeType;
        String tempLongName;
        String tempShortName;

        try{

            br = new BufferedReader(new java.io.FileReader("src\\main\\resources\\PrototypeNodes.csv"));

           while((line = br.readLine()) != null)
           {
               try {
                   String[] lineArray = line.split(splitBy);

                   tempNodeID = lineArray[0];
                   tempXCoord = Integer.parseInt(lineArray[1]); //long or int???
                   tempYCoord = Integer.parseInt(lineArray[2]);
                   tempFloor = Integer.parseInt(lineArray[3]);
                   tempBuilding = lineArray[4];
                   tempNodeType = lineArray[5];
                   tempLongName = lineArray[6];
                   tempShortName = lineArray[7];

                   System.out.println("tempNodeID is: " + tempNodeID);

                   nodeList.add(new Node(tempNodeID, tempXCoord, tempYCoord, tempFloor,
                           tempBuilding, tempNodeType, tempLongName, tempShortName));
               }
               catch(java.lang.NumberFormatException e){
                   System.out.println("Incorrect Node format, ignored");
               }
           }


        }  catch (IOException e) {
            e.printStackTrace();
        }

    }

    public LinkedList<Node> getNodes()
    {
        return nodeList;
    }


}
