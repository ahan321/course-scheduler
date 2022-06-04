
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
public class CourseQueries {
    private static Connection connection;
    private static ArrayList<String> faculty = new ArrayList<String>();
    private static PreparedStatement dropCourse;
    private static PreparedStatement addCourse;
    private static PreparedStatement getCourseList;
    private static ResultSet resultSet;
    
    public static ArrayList<CourseEntry> getAllCourses(String semester) {
        connection = DBConnection.getConnection();
        ArrayList<CourseEntry> courses = new ArrayList<CourseEntry>();
        try
        {
            getCourseList = connection.prepareStatement("select COURSECODE, DESCRIPTION, SEATS from APP.COURSE where SEMESTER = '" + semester + "'");
            resultSet = getCourseList.executeQuery();
            
            while(resultSet.next())
            {
                String courseCode = resultSet.getString("COURSECODE");
                String description = resultSet.getString("DESCRIPTION");
                int seats = resultSet.getInt("SEATS");
                
                courses.add(new CourseEntry(semester,courseCode,description,seats));
            }
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        return courses;
    }
    
    public static void addCourse(CourseEntry course)
    {
        connection = DBConnection.getConnection();
        try
        {
            addCourse = connection.prepareStatement("insert into APP.COURSE (SEMESTER,COURSECODE,DESCRIPTION,SEATS) values (?,?,?,?)");
            addCourse.setString(1, course.getSemester());
            addCourse.setString(2, course.getCoursecode());
            addCourse.setString(3, course.getDescription());
            addCourse.setInt(4, course.getSeats());
            addCourse.executeUpdate();
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        
    }
    
    public static ArrayList<String> getAllCourseCodes(String semester) {
        connection = DBConnection.getConnection();
        ArrayList<String> courses = new ArrayList<String>();
        try
        {
            getCourseList = connection.prepareStatement("select COURSECODE from APP.COURSE where SEMESTER = '" + semester + "'");
            resultSet = getCourseList.executeQuery();
            
            while(resultSet.next())
            {
                String courseCode = resultSet.getString("COURSECODE");
                
                courses.add(courseCode);
            }
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        return courses;
    }
    
        public static int getCourseSeats(String semester, String courseCode) {
        connection = DBConnection.getConnection();
        int seats = 0;
        try
        {
            getCourseList = connection.prepareStatement("select SEATS from APP.COURSE where SEMESTER = '" + semester + "' and courseCode = '" + courseCode + "'");
            resultSet = getCourseList.executeQuery();
            
            while(resultSet.next())
            {
                seats = resultSet.getInt("SEATS");
            }
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        return seats;
    }
        
        public static void dropCourse(String semester, String courseCode) {
        connection = DBConnection.getConnection();
        try
        {
            dropCourse = connection.prepareStatement("delete from APP.COURSE where SEMESTER = '" + semester + "' and COURSECODE = '" + courseCode + "'");
            dropCourse.executeUpdate();
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        }
}
