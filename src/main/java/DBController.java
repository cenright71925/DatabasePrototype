import java.io.BufferedReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.sql.*;
import java.util.LinkedList;
//import main.java.Node;
//import main.java.Node;

import java.io.File;

public class DBController
{
    private static Connection connection;
    private static String path = "file:///" + System.getProperty("user.dir") +  File.separator + "PrototypeNodes.csv";

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

    public static String getPath()
    {
        return path;
    }


    static LinkedList<Node> getNodeList()
    {
        return nodeList;
    }

    static void setNodeList(LinkedList<Node> nodeList) {
        DBController.nodeList = nodeList;
    }


    static void readFile()
    {
        connection();

        System.out.println(path);

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

            //String path = "file:///" + System.getProperty("user.dir") + File.separator + "src" +  File.separator + "main" + File.separator + "resources" + File.separator + "PrototypeNodes.csv";
            //String path = "file:///" + System.getProperty("user.dir") +  "\\PrototypeNodes.csv";

            URL filePath = new URL(path);
            File csvFile = new File(filePath.toURI());
            br = new BufferedReader(new java.io.FileReader(csvFile));

            try{
                String createTable = "CREATE TABLE Node (nodeID Varchar(10) Primary Key, xCoord INTEGER, yCoord INTEGER, floor INTEGER, building Varchar(20), nodeType Varchar(4), longName Varchar(200), shortName Varchar(50))";
                PreparedStatement pstmt1 = connection.prepareStatement(createTable);
                pstmt1.executeUpdate();

                System.out.println("created table w/o dropping");

            }
            catch(SQLException e){

                System.out.println("got to drop table catch block");

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

            int count = 0;

            while((line = br.readLine()) != null)
            {

                System.out.println("current line of csv: " + count);
                count++;

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

                    System.out.println("node inserted:" + count);

                }
                catch(java.lang.NumberFormatException | SQLException e){
                    System.out.println("Incorrect Node format, ignored");
                }
            }

//            try{
//                connection.close();
//            }
//            catch (SQLException e){
//                System.out.println("connection not closed successfully");
//            }


        }  catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }

    }

    public static Connection getConnection() {
        return connection;
    }

    public static void addNode(Node n) throws java.sql.SQLException{

        try{
            PreparedStatement nodeStatement = connection.prepareStatement("Insert into Node values (?, ?, ?, ?, ?, ?, ?, ?)");

            // can you mix prepared statement types? setString vs setNString?
            nodeStatement.setString(1, n.getNodeID());
            nodeStatement.setString(2, String.valueOf(n.getXCoord()));
            nodeStatement.setString(3, String.valueOf(n.getYCoord()));
            nodeStatement.setString(4, String.valueOf(n.getFloor()));
            nodeStatement.setString(5, n.getBuilding());
            nodeStatement.setString(6, n.getNodeType());
            nodeStatement.setString(7, n.getLongName());
            nodeStatement.setString(8, n.getShortName());

            nodeStatement.execute();
        }
        // table does not exist or some other error happened
        catch (java.sql.SQLException e) {
            //String exceptionString = String.format("NodeID nodeID:%s already exists", n.getNodeID());
            //System.out.println(exceptionString);
            //throw new java.sql.SQLException(exceptionString);
            e.printStackTrace();
        }
    }


    public static void deleteNode(Node n) throws java.sql.SQLException{

        try{
            Statement nodeDelete = connection.createStatement();
            String deleteStatement = String.format("DELETE FROM Node WHERE nodeID='%s'", n.getNodeID());
            System.out.println(deleteStatement);
            nodeDelete.executeUpdate(deleteStatement);

        }
        catch(java.sql.SQLException e){
            String exceptionString = String.format("NodeID:%s does not exist in table", n.getNodeID());
            System.out.println(exceptionString);
            throw new java.sql.SQLException(exceptionString);
        }

    }

    public static void editNode(Node oldNode, Node newNode) throws java.sql.SQLException{
        try{
            // nodeID should not be changed
            // sets all parts of the object to account for changes

            deleteNode(oldNode);
            addNode(newNode);


//            n.set = xCoord;
//            this.yCoord = yCoord;
//            this.floor = floor;
//            this.building = building;
//            this.nodeType = nodeType;
//            this.longName = longName;
//            this.shortName = shortName;
//
//            PreparedStatement editStatement = connection.prepareStatement("UPDATE Node SET XCOORD=?,YCOORD=?,FLOOR=?,BUILDING=?,NODETYPE=?,LONGNAME=?,SHORTNAME=? WHERE NODEID=?");
//
//            editStatement.setString(1, String.valueOf(xCoord));
//            editStatement.setString(2, String.valueOf(yCoord));
//            editStatement.setString(3, String.valueOf(floor));
//            editStatement.setString(4, building);
//            editStatement.setString(5, nodeType);
//            editStatement.setString(6, longName);
//            editStatement.setString(7, shortName);
//            editStatement.setString(8, nodeID);
//            // System.out.println(editStatement);
//            editStatement.executeUpdate();

        }
        catch(java.sql.SQLException e){
            throw new java.sql.SQLException("Node does not exist");
        }
    }


}