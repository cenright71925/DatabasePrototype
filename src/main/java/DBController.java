import java.io.BufferedReader;
import java.io.IOException;
import java.sql.*;
import java.util.LinkedList;


import java.io.File;

public class DBController {
    private static Connection connection;
    private static String path = "PrototypeNodes.csv";
    private static LinkedList<Node> nodeList = new LinkedList<>();

    // getters for class attributes
    public static Connection getConnection() {
        return connection;
    }

    public static String getPath() {
        return path;
    }

    static LinkedList<Node> getNodeList() {
        return nodeList;
    }

    static void setNodeList(LinkedList<Node> nodeList) {
        DBController.nodeList = nodeList;
    }

    /**
     * private static void connection() - connects to the database, class stores the connection object
     */
    private static void connection() {
        try {
            // connects to the database
            DriverManager.registerDriver(new org.apache.derby.jdbc.EmbeddedDriver());
            connection = DriverManager.getConnection("jdbc:derby:myDB;create=true");
        } catch (SQLException e) {
            System.out.println("Connection failed. Check output console.");
            e.printStackTrace();
        }
    }

    /**
     * static void readFile() - reads in the CSV file, populates the table and database
     * with the node information. Iterates per CSV entry, will skip over any incorrectly formatted entries
     */
    static void readFile() {
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

        try {
            // finds the path to the CSV file, works for both Mac and PC
//            URL filePath = new URL(path);
//            File csvFile = new File(filePath.toURI());
            br = new BufferedReader(new java.io.FileReader(path));

            try {
                // tries to create the table, throws an exception if the table already exists
                String createTable = "CREATE TABLE Node (nodeID Varchar(10) Primary Key, xCoord INTEGER, yCoord INTEGER, floor INTEGER, building Varchar(20), nodeType Varchar(4), longName Varchar(200), shortName Varchar(50))";
                PreparedStatement pstmt1 = connection.prepareStatement(createTable);
                pstmt1.executeUpdate();

                System.out.println("created table w/o dropping");

            } catch (SQLException e) {

                System.out.println("got to drop table catch block");

                try {
                    // drops the table then recreates it
                    String dropTable = "DROP TABLE Node";
                    PreparedStatement pstmt0 = connection.prepareStatement(dropTable);
                    pstmt0.executeUpdate();

                    String createTable = "CREATE TABLE Node (nodeID Varchar(10) Primary Key, xCoord INTEGER, yCoord INTEGER, floor INTEGER, building Varchar(20), nodeType Varchar(4), longName Varchar(200), shortName Varchar(50))";
                    PreparedStatement pstmt1 = connection.prepareStatement(createTable);
                    pstmt1.executeUpdate();
                } catch (SQLException e1) {
                    e.printStackTrace();
                }
            }

            // counts the nodes explicitly are they are entered
            int count = 0;

            // reads through each node in the file
            while ((line = br.readLine()) != null) {
                //System.out.println("current line of csv: " + count);
                count++;

                try {
                    // splits each line by "," into an array
                    String[] lineArray = line.split(splitBy);

                    // assigns each array index to its corresponding node
                    tempNodeID = lineArray[0];
                    tempXCoord = lineArray[1];
                    tempYCoord = lineArray[2];
                    tempFloor = lineArray[3];
                    tempBuilding = lineArray[4];
                    tempNodeType = lineArray[5];
                    tempLongName = lineArray[6];
                    tempShortName = lineArray[7];

                    // creates a new node object and adds it to nodeList
                    nodeList.add(new Node(tempNodeID, Integer.parseInt(tempXCoord), Integer.parseInt(tempYCoord),
                            Integer.parseInt(tempFloor), tempBuilding, tempNodeType, tempLongName, tempShortName));

                    // creates a prepared statement to make the node
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
                    // inserts the node into the database
                    pstmt2.executeUpdate();

                    //System.out.println("node inserted:" + count);
                } catch (java.lang.NumberFormatException | SQLException e) {
                    // should only ignore the first node as it is the .csv column formatting
                    System.out.println("Incorrect Node format, ignored");
                }
            }
        } catch (IOException  e) {
            e.printStackTrace();
        }
    }

    /**
     * public static void addNode(Node n) - use to add a node to the table and database
     *
     * @param n the nodeID to add, created a new node object
     * @throws java.sql.SQLException problems sending node data to the database
     */
    static void addNode(Node n) throws java.sql.SQLException {
        try {
            // creates a prepared statement to made the node
            PreparedStatement nodeStatement = connection.prepareStatement("Insert into Node values (?, ?, ?, ?, ?, ?, ?, ?)");

            nodeStatement.setString(1, n.getNodeID());
            nodeStatement.setString(2, String.valueOf(n.getXCoord()));
            nodeStatement.setString(3, String.valueOf(n.getYCoord()));
            nodeStatement.setString(4, String.valueOf(n.getFloor()));
            nodeStatement.setString(5, n.getBuilding());
            nodeStatement.setString(6, n.getNodeType());
            nodeStatement.setString(7, n.getLongName());
            nodeStatement.setString(8, n.getShortName());
            // inserts the node into the database
            nodeStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new java.sql.SQLException("NodeID nodeID:%s already exists", n.getNodeID());
        }
    }

    /**
     * public static void deleteNode(Node n) - use to delete a node object from the table and database
     * @param n the node to delete
     * @throws SQLException problems sending node data to the database
     */
    static void deleteNode(Node n) throws SQLException {
        try {
            // deletes the nodeID n from the database
            Statement nodeDelete = connection.createStatement();
            String deleteStatement = String.format("DELETE FROM Node WHERE nodeID='%s'", n.getNodeID());
            System.out.println(deleteStatement);
            // inserts the node into the database
            nodeDelete.executeUpdate(deleteStatement);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("NodeID:%s does not exist in table", n.getNodeID());
        }
    }

    /**
     * public static void editNode(Node oldNode, Node newNode) - use to edit a node object in the table and database
     *
     * @param oldNode the previous node to be deleted
     * @param newNode the new node to replace the old node
     * @throws SQLException problems sending node data to the database
     */
    static void editNode(Node oldNode, Node newNode) throws SQLException {
        try {
            // nodeID should not be changed
            // sets all parts of the object to account for changes
            // deleting the previous node and adding a new node with the same name is similar to editing the node
            deleteNode(oldNode);
            addNode(newNode);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("Node does not exist");
        }
    }
}