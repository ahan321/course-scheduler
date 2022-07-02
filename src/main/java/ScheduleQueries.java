import java.sql.*;
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
public class ScheduleQueries {
    private static Connection connection;
    private static ArrayList<String> faculty = new ArrayList<String>();
    private static PreparedStatement addSchedule;
    private static PreparedStatement getScheduleList;
    private static PreparedStatement getScheduleList2;
    private static PreparedStatement getScheduledStudentByCourse;
    private static PreparedStatement dropStudentScheduleByCourse;
    private static ResultSet resultSet;
    
    public static void addScheduleEntry(ScheduleEntry entry) {
        connection = DBConnection.getConnection();
        try
        {
            addSchedule = connection.prepareStatement("insert into APP.SCHEDULE (SEMESTER,STUDENTID,COURSECODE,STATUS,TIMESTAMP) values (?,?,?,?,?)");
            addSchedule.setString(1, entry.getSemester());
            addSchedule.setString(2, entry.getStudentID());
            addSchedule.setString(3, entry.getCourseCode());
            addSchedule.setString(4, entry.getStatus());
            addSchedule.setTimestamp(5, entry.getTimestamp());
            addSchedule.executeUpdate();
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
    }
    
    public static ArrayList<ScheduleEntry> getScheduleByStudent(String semester, String studentID) {
        connection = DBConnection.getConnection();
        ArrayList<ScheduleEntry> studentSchedule = new ArrayList<ScheduleEntry>();
        try
        {
            getScheduleList = connection.prepareStatement("select COURSECODE,STATUS,TIMESTAMP from APP.SCHEDULE where SEMESTER = '"+ semester + "' and STUDENTID = '" + studentID + "'");
            resultSet = getScheduleList.executeQuery();
            
            while(resultSet.next())
            {
                String courseCode = resultSet.getString(1);
                String status = resultSet.getString(2);
                Timestamp timestamp = resultSet.getTimestamp(3);
              
                studentSchedule.add(new ScheduleEntry(semester,courseCode,studentID,status,timestamp));
            }
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        return studentSchedule;
    }
    
    public static int getScheduledStudentCount(String currentSemester, String courseCode) {
        connection = DBConnection.getConnection();
        int count = 0;
        try
        {
            getScheduleList = connection.prepareStatement("select count(STUDENTID) from APP.SCHEDULE where SEMESTER = '" + currentSemester + "' and COURSECODE = '" + courseCode + "'");
            resultSet = getScheduleList.executeQuery();
            
            while(resultSet.next())
            {
                count = resultSet.getInt(1);
            }
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        return count;
    }
    
        public static ArrayList<ScheduleEntry> getScheduledStudentsByCourse(String semester, String courseCode) {
        connection = DBConnection.getConnection();
        ArrayList<ScheduleEntry> students = new ArrayList<>();
        try
        {
            getScheduledStudentByCourse = connection.prepareStatement("select * from APP.SCHEDULE where SEMESTER = '" + semester + "' and COURSECODE = '" + courseCode + "' and STATUS = 's'");
            resultSet = getScheduledStudentByCourse.executeQuery();
            
            while(resultSet.next())
            {
                String studentID = resultSet.getString("STUDENTID");
                Timestamp timestamp = resultSet.getTimestamp("TIMESTAMP");
                
                students.add(new ScheduleEntry(semester,courseCode,studentID,"s",timestamp));
            }
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        return students;
    }
    
    public static ArrayList<ScheduleEntry> getWaitlistedStudentsByCourse(String semester, String courseCode) {
        connection = DBConnection.getConnection();
        ArrayList<ScheduleEntry> students = new ArrayList<>();
        try
        {
            getScheduledStudentByCourse = connection.prepareStatement("select * from APP.SCHEDULE where SEMESTER = '" + semester + "' and COURSECODE = '" + courseCode + "' and STATUS = 'w'");
            resultSet = getScheduledStudentByCourse.executeQuery();
            
            while(resultSet.next())
            {
                String studentID = resultSet.getString("STUDENTID");
                Timestamp timestamp = resultSet.getTimestamp("TIMESTAMP");
                
                students.add(new ScheduleEntry(semester,courseCode,studentID,"w",timestamp));
            }
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        return students;
    }
    
    public static void dropStudentScheduleByCourse(String semester, String studentID, String courseCode) {
        connection = DBConnection.getConnection();
        try
        {
            dropStudentScheduleByCourse = connection.prepareStatement("delete from APP.SCHEDULE where STUDENTID = '" + studentID + "' and SEMESTER = '" + semester + "' and COURSECODE = '" + courseCode + "'");
            dropStudentScheduleByCourse.executeUpdate();
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
    }
    
    public static ArrayList<ScheduleEntry> dropScheduleByCourse(String semester, String courseCode) {
        connection = DBConnection.getConnection();
        ArrayList<ScheduleEntry> students = new ArrayList<ScheduleEntry>();
        try
        {   
            getScheduleList = connection.prepareStatement("select studentid, status, timestamp from app.schedule where coursecode = ? and semester = ?");
            getScheduleList2 = connection.prepareStatement("delete from app.schedule where coursecode = ? and semester = ?");
            getScheduleList.setString(1, courseCode);
            getScheduleList.setString(2, semester);
            getScheduleList2.setString(1, courseCode);
            getScheduleList2.setString(2, courseCode);
            resultSet = getScheduleList.executeQuery();
            getScheduleList2.executeUpdate();
            
            while(resultSet.next())
            {
               ScheduleEntry entry = new ScheduleEntry(semester, courseCode, resultSet.getString(1), resultSet.getString(2), resultSet.getTimestamp(3));
               students.add(entry);
            }
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        
        return students;
    }
    
    public static String getFirstName(String studentID) {
        connection = DBConnection.getConnection();
        String firstName = "";
        try
        {
            getScheduledStudentByCourse = connection.prepareStatement("select FIRSTNAME from APP.STUDENT where STUDENTID = '" + studentID + "'");
            resultSet = getScheduledStudentByCourse.executeQuery();
            
            while(resultSet.next())
            {
                firstName = resultSet.getString("FIRSTNAME");
            }
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        return firstName;
    }
    
    public static String getLastName(String studentID) {
        connection = DBConnection.getConnection();
        String lastName = "";
        try
        {
            getScheduledStudentByCourse = connection.prepareStatement("select LASTNAME from APP.STUDENT where STUDENTID = '" + studentID + "'");
            resultSet = getScheduledStudentByCourse.executeQuery();
            
            while(resultSet.next())
            {
                lastName = resultSet.getString("LASTNAME");
            }
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        return lastName;
    }
    
    public static void updateScheduleEntry(String semester, ScheduleEntry entry) {
        connection = DBConnection.getConnection();
        try
        {   
            getScheduleList = connection.prepareStatement("update app.schedule set status = 's' where studentid = ? and semester = ? and timestamp = ?");
            getScheduleList.setString(1, entry.getStudentID());
            getScheduleList.setString(2, semester);
            getScheduleList.setTimestamp(3, entry.getTimestamp());
            getScheduleList.executeUpdate();
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
    }
    
    public static ArrayList<ScheduleEntry> deleteStudentFromSchedule(String currentSemester, String studentID)
    {
        connection = DBConnection.getConnection();
        ArrayList<ScheduleEntry> schedules = new ArrayList<ScheduleEntry>();
        System.out.println("REached meewjl");
        try
        {   
            getScheduleList = connection.prepareStatement("select COURSECODE, STATUS, TIMESTAMP from APP.SCHEDULE where STUDENTID = ? and SEMESTER = ?");
            getScheduleList2 = connection.prepareStatement("delete from APP.SCHEDULE where STUDENTID = ? and SEMESTER = ?");
            getScheduleList.setString(1,studentID);
            getScheduleList.setString(2,currentSemester);
            getScheduleList2.setString(1,studentID);
            getScheduleList2.setString(2,currentSemester);
            resultSet = getScheduleList.executeQuery();
            getScheduleList2.executeUpdate();
            
            while(resultSet.next())
            {
               ScheduleEntry entry = new ScheduleEntry(currentSemester, resultSet.getString(1), studentID, resultSet.getString(2), resultSet.getTimestamp(3));
               schedules.add(entry);
            }
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        return schedules;
    }
    
    public static String deleteClassFromStudent(String currentSemester, String courseCode, String studentID)
    {
        connection = DBConnection.getConnection();
        String returnString = "";
        try
        {   
            getScheduleList = connection.prepareStatement("select status from app.schedule where studentid = ? and semester = ? and coursecode = ?");
            getScheduleList2 = connection.prepareStatement("delete from app.schedule where studentid = ? and semester = ? and coursecode = ?");
            getScheduleList.setString(1, studentID);
            getScheduleList.setString(2, currentSemester);
            getScheduleList.setString(3, courseCode);
            getScheduleList2.setString(1, studentID);
            getScheduleList2.setString(2, currentSemester);
            getScheduleList2.setString(3, courseCode);
            resultSet = getScheduleList.executeQuery();
            getScheduleList2.executeUpdate();
            
            while(resultSet.next())
            {
               returnString = resultSet.getString(1);
            }
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        return returnString;
    }
    
}
