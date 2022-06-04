
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Ahan Malli
 */
public class StudentQueries {
    private static Connection connection;
    private static ArrayList<String> faculty = new ArrayList<String>();
    private static PreparedStatement addStudent;
    private static PreparedStatement getStudent;
    private static PreparedStatement dropStudent;
    private static PreparedStatement getScheduledStudentByCourse;
    private static PreparedStatement getStudentList;
    private static ResultSet resultSet;
    
    public static void addStudent(StudentEntry student)
    {
        connection = DBConnection.getConnection();
        try
        {
            addStudent = connection.prepareStatement("insert into APP.STUDENT (STUDENTID,FIRSTNAME,LASTNAME) values (?,?,?)");
            addStudent.setString(1, student.getStudentID());
            addStudent.setString(2, student.getFirstName());
            addStudent.setString(3, student.getLastName());
            addStudent.executeUpdate();
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        
    }
    
    public static ArrayList<StudentEntry> getAllStudents() {
        connection = DBConnection.getConnection();
        ArrayList<StudentEntry> students = new ArrayList<>();
        try
        {
            getStudentList = connection.prepareStatement("select STUDENTID, FIRSTNAME, LASTNAME from APP.STUDENT");
            resultSet = getStudentList.executeQuery();
            
            while(resultSet.next())
            {
                String studentID = resultSet.getString("STUDENTID");
                String firstName = resultSet.getString("FIRSTNAME");
                String lastName = resultSet.getString("LASTNAME");
                
                students.add(new StudentEntry(studentID,firstName,lastName));
            }
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        return students;
    }
    
    public static String getStudentID(String lastName, String firstName) {
        connection = DBConnection.getConnection();
        String studentID = "";
        try
        {
            getStudentList = connection.prepareStatement("select STUDENTID from APP.STUDENT where FIRSTNAME = '" + firstName + "' and LASTNAME = '" + lastName + "'");
            resultSet = getStudentList.executeQuery();
            
            while(resultSet.next())
            {
                studentID = resultSet.getString("STUDENTID");
            }
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        return studentID;
    }
    
    public static StudentEntry getStudent(String studentID) {
        connection = DBConnection.getConnection();
        String firstName = "";
        String lastName = "";
        try
        {
            getStudent = connection.prepareStatement("select * from APP.STUDENT where STUDENTID = '" + studentID + "'");
            resultSet = getStudent.executeQuery();
            
            while(resultSet.next())
            {
                firstName = resultSet.getString("FIRSTNAME");
                lastName = resultSet.getString("LASTNAME");
            }
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        return new StudentEntry(studentID,firstName,lastName);
    }
    
    public static void dropStudent(String studentID) {
        connection = DBConnection.getConnection();
        try
        {
            dropStudent = connection.prepareStatement("delete from APP.STUDENT where STUDENTID = '" + studentID + "'");
            dropStudent.executeUpdate();           
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
    }
}
