import java.io.BufferedReader;
import java.io.IOException;
import java.sql.*;
import java.util.LinkedList;
//import main.java.Node;
//import main.java.Node;

import javax.xml.transform.Result;
import java.io.File;
import java.nio.file.Files;

public class FileReader
{

    private static Connection connection;



    private static LinkedList<Node> nodeList = new LinkedList<>();

    private static void connection()
    {
        try {
            // substitute your database name for myDB
            connection = DriverManager.getConnection("jdbc:derby:myDB;create=true");
            // autoCommit should be false so an admin can agree to committing before done
            //connection.setAutoCommit(false);

        } catch (SQLException e) {
            System.out.println("Connection failed. Check output console.");
            e.printStackTrace();
        }

    }

    static LinkedList<Node> getNodeList()
    {
        return nodeList;
    }

    static void setNodeList(LinkedList<Node> nodeList) {
        FileReader.nodeList = nodeList;
    }


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

            try{
                String createTable = "CREATE TABLE Node (nodeID Varchar(10) Primary Key, xCoord INTEGER, yCoord INTEGER, floor INTEGER, building Varchar(20), nodeType Varchar(4), longName Varchar(200), shortName Varchar(50))";
                PreparedStatement pstmt1 = connection.prepareStatement(createTable);
                pstmt1.executeUpdate();
            }
            catch(SQLException e){
                try {
                    String dropTable = "DROP TABLE Node";
                    PreparedStatement pstmt0 = connection.prepareStatement(dropTable);
                    pstmt0.executeUpdate();

                    String createTable = "CREATE TABLE Node (nodeID Varchar(10) Primary Key, xCoord INTEGER, yCoord INTEGER, floor INTEGER, building Varchar(20), nodeType Varchar(4), longName Varchar(200), shortName Varchar(50))";
                    PreparedStatement pstmt1 = connection.prepareStatement(createTable);
                    pstmt1.executeUpdate();
                }
                catch (SQLException e1){
                    e.printStackTrace();
                }
            }

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

                    //MainController.addNode(new Node(tempNodeID, Integer.parseInt(tempXCoord), Integer.parseInt(tempYCoord),
                      //      Integer.parseInt(tempFloor), tempBuilding, tempNodeType, tempLongName, tempShortName));

                    nodeList.add(new Node(tempNodeID, Integer.parseInt(tempXCoord), Integer.parseInt(tempYCoord),
                            Integer.parseInt(tempFloor), tempBuilding, tempNodeType, tempLongName, tempShortName));

                    String insertLine = "Insert into Node values (?, ?, ?, ?, ?, ?, ?, ?)";

                    PreparedStatement pstmt2 = connection.prepareStatement(insertLine);


                    pstmt2.setString(1, tempNodeID);
                    pstmt2.setString(2, tempXCoord);
                    pstmt2.setString(3, tempYCoord);
                    pstmt2.setString(4, tempFloor);
                    pstmt2.setString(5, tempBuilding);
                    pstmt2.setString(6, tempNodeType);
                    pstmt2.setString(7, tempLongName);
                    pstmt2.setString(8, tempShortName);

                    pstmt2.executeUpdate();


                }
                catch(java.lang.NumberFormatException | SQLException e){
                    System.out.println("Incorrect Node format, ignored");
                }
            }

            try{
                connection.close();
            }
            catch (SQLException e){
                System.out.println("connection not closed successfully");
            }


        }  catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static Connection getConnection() {
        return connection;
    }

}