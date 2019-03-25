
import java.io.BufferedReader;
import java.io.IOException;
import java.sql.*;
import java.util.LinkedList;
import main.java.Node;

import javax.xml.transform.Result;
import java.io.File;
import java.nio.file.Files;

public class FileReader
{

    private static Connection connection;
    private static void connection()
    {
        try {
            // substitute your database name for myDB
            connection = DriverManager.getConnection("jdbc:derby:myDB;create=true");
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
        String tempXCoord; //long or int???
        String tempYCoord;
        String tempFloor;
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
                    tempXCoord = lineArray[1];
                    tempYCoord = lineArray[2];
                    tempFloor = lineArray[3];
                    tempBuilding = lineArray[4];
                    tempNodeType = lineArray[5];
                    tempLongName = lineArray[6];
                    tempShortName = lineArray[7];

                    System.out.println("tempNodeID is: " + tempNodeID);

                    //use this to check if its already in the database
                    //SELECT A,B
                    //FROM Test
                    //WHERE (B,A) IN (SELECT B,A FROM Test);

                    Statement checkStmt = connection.createStatement();
                    String checkQ = "SELECT * FROM NODE";

                    ResultSet rsExist = checkStmt.executeQuery(checkQ);

                    int count = 0;

                    while (rsExist.next())
                    {
                        count++;
                    }

                    System.out.println("Current count is " + count);

                    if (count == 0) {

                        nodeList.add(new Node(tempNodeID, Integer.parseInt(tempXCoord), Integer.parseInt(tempYCoord),
                                Integer.parseInt(tempFloor), tempBuilding, tempNodeType, tempLongName, tempShortName));

                        System.out.println("Node was added to linked list");

                        String insertLine = "Insert into Node values (?, ?, ?, ?, ?, ?, ?, ?)";

                        PreparedStatement pstmt1 = connection.prepareStatement(insertLine);

                        pstmt1.setString(1, tempNodeID);
                        pstmt1.setString(2, tempXCoord);
                        pstmt1.setString(3, tempYCoord);
                        pstmt1.setString(4, tempFloor);
                        pstmt1.setString(5, tempBuilding);
                        pstmt1.setString(6, tempNodeType);
                        pstmt1.setString(7, tempLongName);
                        pstmt1.setString(8, tempShortName);

                        pstmt1.executeUpdate();

                        System.out.println("Node added to database");
                    }
                }
                catch(java.lang.NumberFormatException e){
                    System.out.println("Incorrect Node format, ignored");
                }
            }


        }  catch (IOException | SQLException e) {
            e.printStackTrace();
        }

    }

    public LinkedList<Node> getNodes()
    {
        return nodeList;
    }


    public static void displayDBdata()
    {
        try
        {
            Statement dispStmt = connection.createStatement();
            String dispQ = "SELECT * FROM NODE";

            ResultSet nodesRS = dispStmt.executeQuery(dispQ);

            int nodeNum = 1;

            while(nodesRS.next())
            {

                //nodeID,
                // xcoord,
                // ycoord,
                // floor,
                // building,
                // nodeType,
                // longName,
                // shortName

                String strNodeID  = nodesRS.getString("nodeID");
                String strXCoord  = nodesRS.getString("xCoord");
                String strYCoord  = nodesRS.getString("yCoord");
                String strFloor  = nodesRS.getString("floor");
                String strBuilding  = nodesRS.getString("building");
                String strNodeType  = nodesRS.getString("nodeType");
                String strLongName = nodesRS.getString("longName");
                String strShortName = nodesRS.getString("shortName");

                System.out.println("Node #" + nodeNum);
                System.out.println("nodeID: " + strNodeID);
                System.out.println("xCoord: " + strXCoord);
                System.out.println("yCoord: " + strYCoord);
                System.out.println("floor: " + strFloor);
                System.out.println("building: " + strBuilding);
                System.out.println("nodeType: " + strNodeType);
                System.out.println("longName: " + strLongName);
                System.out.println("shortName: " + strShortName);
                System.out.println("\n");

                nodeNum++;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}

