/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Ahan Malli
 */
public class CourseEntry {
    private String Semester;
    private String Coursecode;
    private String Description;
    private int Seats;

    public CourseEntry(String Semester, String Coursecode, String Description, int Seats) {
        this.Semester = Semester;
        this.Coursecode = Coursecode;
        this.Description = Description;
        this.Seats = Seats;
    }

    public String getSemester() {
        return Semester;
    }

    public String getCoursecode() {
        return Coursecode;
    }

    public String getDescription() {
        return Description;
    }

    public int getSeats() {
        return Seats;
    }
}
