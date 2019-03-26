//package main.java;

import java.sql.*;


public class Node
{
    //nodeID,
    // xcoord,
    // ycoord,
    // floor,
    // building,
    // nodeType,
    // longName,
    // shortName

    //BCONF00102,
    // 2150,
    // 1025,
    // 2,
    // 45 Francis,
    // CONF,
    // Duncan Reid Conference Room,
    // Conf B0102


    private String nodeID;
    private int xCoord; //long or int???
    private int yCoord;
    private int floor;
    private String building;
    private String nodeType;
    private String longName;
    private String shortName;

    public Node(String nodeID, int xCoord, int yCoord, int floor, String building,
                String nodeType, String longName, String shortName)
    {
        this.nodeID = nodeID;
        this.xCoord = xCoord;
        this.yCoord = yCoord;
        this.floor = floor;
        this.building = building;
        this.nodeType = nodeType;
        this.longName = longName;
        this.shortName = shortName;
    }

    //all these getters are needed for the table
    public String getNodeID() {
        return nodeID;
    }

    public int getXCoord() {
        return xCoord;
    }

    public int getYCoord() {
        return yCoord;
    }

    public int getFloor() {
        return floor;
    }

    public String getBuilding() {
        return building;
    }

    public String getNodeType() {
        return nodeType;
    }

    public String getLongName() {
        return longName;
    }

    public String getShortName() {
        return shortName;
    }
    // idea: might be good to have a seperate class for holding the connection instead of the file reader class
    // or connection could be established in main and be passed to methods as needed
    // but connection should be private to a class so no one can mess with it.

    // checks if the table already exists
    // passes up an exception if the table doesn't exist
//    private void checkTable(Connection connection) throws java.sql.SQLException{
//        // checks if the table exists
//        // try: command to add the table
//        try{
//            Statement nodeTable = connection.createStatement();
//            String addNodeTableStatement = "CREATE TABLE Node (nodeID Varchar(10) Primary Key, xCoord INTEGER, yCoord INTEGER, floor INTEGER, building Varchar(20), nodeType Varchar(4), longName Varchar(200), shortName Varchar(50))";
//            nodeTable.execute(addNodeTableStatement);
//            // auto commit is turned off
//            connection.commit();
//        }
//        // table already exists
//        catch(java.sql.SQLException e){
//            throw new java.sql.SQLException("Node table already exists");
//        }
//
//    }
    private Connection connection;
    private void connection()
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

    // adds a node to the given connection
//    public void addNode() throws java.sql.SQLException{
//        connection();
//        try{
//            PreparedStatement nodeStatement = connection.prepareStatement("Insert into Node values (?, ?, ?, ?, ?, ?, ?, ?)");
//
//            // can you mix prepared statement types? setString vs setNString?
//            nodeStatement.setString(1, nodeID);
//            nodeStatement.setString(2, String.valueOf(xCoord));
//            nodeStatement.setString(3, String.valueOf(yCoord));
//            nodeStatement.setString(4, String.valueOf(floor));
//            nodeStatement.setString(5, building);
//            nodeStatement.setString(6, nodeType);
//            nodeStatement.setString(7, longName);
//            nodeStatement.setString(8, shortName);
//
//            nodeStatement.execute();
//        }
//        // table does not exist or some other error happened
//        catch (java.sql.SQLException e) {
//            String exceptionString = String.format("NodeID nodeID:%s already exists", nodeID);
//            System.out.println(exceptionString);
//            throw new java.sql.SQLException(exceptionString);
//        }
//        connection.close();
//    }
//
//    public void deleteNode() throws java.sql.SQLException{
//        connection();
//        try{
//            Statement nodeDelete = connection.createStatement();
//            String deleteStatement = String.format("DELETE FROM Node WHERE nodeID='%s'", nodeID);
//            System.out.println(deleteStatement);
//            nodeDelete.executeUpdate(deleteStatement);
//
//        }
//        catch(java.sql.SQLException e){
//            String exceptionString = String.format("NodeID:%s does not exist in table", nodeID);
//            System.out.println(exceptionString);
//            throw new java.sql.SQLException(exceptionString);
//        }
//
//        connection.close();
//    }

    // should only be called to submit an edit, will change the object as necessary
//    public void editNode(int xCoord, int yCoord, int floor, String building,
//                         String nodeType, String longName, String shortName) throws java.sql.SQLException{
//        connection();
//        try{
//            // nodeID should not be changed
//            // sets all parts of the object to account for changes
//
//            this.xCoord = xCoord;
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
//           // System.out.println(editStatement);
//            editStatement.executeUpdate();
//
//        }
//        catch(java.sql.SQLException e){
//            throw new java.sql.SQLException("Node does not exist");
//        }
//        connection.close();
//    }
}
