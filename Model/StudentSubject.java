package Model;
import Service.GradeCalculate;


public class StudentSubject
{
    private Student student;
    private Subject subject;
    
    private double assignment_mark;
    private double test_mark;
    private double final_exam_mark;
    
    public StudentSubject(
        Student student,
        Subject subject,
        double assignment_mark,
        double test_mark,
        double final_exam_mark)
    {
        this.student = student;
        this.subject = subject;
        this.assignment_mark = assignment_mark;
        this.test_mark = test_mark;
        this.final_exam_mark = final_exam_mark;
    }
    
    public Student getStudent()
    {
        return student;
    }
    
    
    public Subject getSubject()
    {
        return subject;
    }
    
    public double getAssignmentMark()
    {
        return assignment_mark;
    }
    
    public double getTestMark()
    {
        return test_mark;
    }
    
    
    public double getFinalMarks()
    {
        return final_exam_mark;
    }
    
    
    public void setAssignmentMark(double assignment_mark)
    {
        this.assignment_mark = assignment_mark;
    }
    
    
    public void setTestMark(double test_mark)
    {
        this.test_mark = test_mark;
    }
    
    public void setFinalExamMark(double final_exam_mark)
    {
        this.final_exam_mark = final_exam_mark;
    }
    
    public double getOverallMark()
    {
        return GradeCalculate.calculateOverallMark(
            assignment_mark,
            test_mark,
            final_exam_mark);
    }
    
    
    public String getGrade()
    {
        return GradeCalculate.calculateGrade(
            getOverallMark());
    }
    
    public double getGradePoint()
    {
        return GradeCalculate.calculateGradePoint(getGrade());
    }
    
    public String getStatus()
    {
        return GradeCalculate.calculateStatus(
            getOverallMark());
    }
    

}
