//package main.java;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;


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
    private void checkTable(Connection connection) throws java.sql.SQLException{
        // checks if the table exists
        // try: command to add the table
        try{
            Statement nodeTable = connection.createStatement();
            String addNodeTableStatement = "CREATE TABLE Node (nodeID Varchar(10) Primary Key, xCoord INTEGER, yCoord INTEGER, floor INTEGER, building Varchar(20), nodeType Varchar(4), longName Varchar(200), shortName Varchar(50))";
            nodeTable.execute(addNodeTableStatement);
            // auto commit is turned off
            connection.commit();
        }
        // table already exists
        catch(java.sql.SQLException e){
            throw new java.sql.SQLException("Node table already exists");
        }

    }

    // adds a node to the given connection
    public void addNode(Connection connection) throws java.sql.SQLException{
        try{
            PreparedStatement nodeStatement = connection.prepareStatement("Insert into Node values (?, ?, ?, ?, ?, ?, ?, ?)");

            // can you mix prepared statement types? setString vs setNString?
            nodeStatement.setString(1, nodeID);
            nodeStatement.setString(2, String.valueOf(xCoord));
            nodeStatement.setString(3, String.valueOf(yCoord));
            nodeStatement.setString(4, String.valueOf(floor));
            nodeStatement.setString(5, building);
            nodeStatement.setString(6, nodeType);
            nodeStatement.setString(7, longName);
            nodeStatement.setString(8, shortName);

            nodeStatement.execute();
            // auto commit is turned off
            connection.commit();
        }
        // table does not exist or some other error happened
        catch (java.sql.SQLException e){
            // calls check table to make sure it exists
            try{
                checkTable(connection);
                // calls the function again now that the table exists
                addNode(connection);
            }
            // table already exists, handled here
            catch(java.sql.SQLException ie){
                // ie passed from checkTable()
                System.out.println(ie.getErrorCode());
                throw ie;
            }
        }
    }

    public void deleteNode(Connection connection, String nodeName) throws java.sql.SQLException{
        try{
            Statement nodeDelete = connection.createStatement();
            // the string format seems unnecessary
            String deleteStatement = String.format("DELETE FROM Node WHERE nodeID=nodeName:%s", nodeName);
            nodeDelete.execute(deleteStatement);
        }
        catch(java.sql.SQLException e){
            try{
                checkTable(connection);
            }
            // table exists, but the node doesn't exist
            catch(java.sql.SQLException ie){
                System.out.println(ie.getErrorCode());
                throw new java.sql.SQLException("Node does not exist in table");
            }
        }
    }

    public void editNode(Connection connection, String nodeName) throws java.sql.SQLException{

    }
}
