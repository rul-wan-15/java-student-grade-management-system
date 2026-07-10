package Service;



public class GradeCalculate
{
    private static final double assignment_weight = 0.3;
    private static final double test_weight = 0.2;
    private static final double final_exam_weight = 0.5;
    
    private GradeCalculate()
    {
    }
    
    public static double calculateOverallMark(
        double assignment_mark,
        double test_mark,
        double final_exam_mark)
    {
        double calculate_assignment = assignment_mark * assignment_weight;
        double calculate_test = test_mark * test_weight;
        double calculate_final_test = final_exam_mark * final_exam_weight;
        
        double total = calculate_assignment + calculate_test + calculate_final_test;
        
        return total;
    }
    
    public static String calculateGrade(double overallMark)
    {
        if (overallMark >= 80)
        {
            return "A";
        }
        else if (overallMark >= 70)
        {
            return "B";
        }
        else if (overallMark >= 60)
        {
            return "C";
        }
        else if (overallMark >= 50)
        {
            return "D";
        }

        return "F";
    }
    
    
    public static double calculateGradePoint(String grade)
    {
        if (grade.equals("A"))
        {
            return 4.00;
        }
        else if (grade.equals("B"))
        {
            return 3.00;
        }
        else if (grade.equals("C"))
        {
            return 2.00;
        }
        else if (grade.equals("D"))
        {
            return 1.00;
        }

        return 0.00;
    }
    
    public static String calculateStatus(double overallMark)
    {
        return overallMark >= 50 ? "Pass" : "Fail";
    }
    
    public static boolean isValidMark(double mark)
    {
        return mark >= 0 && mark <= 100;
    }
}
