package Model;



public class Subject
{
    private String subject_code;
    private String subject_name;
    private int credit_hour;

    public Subject(String subject_code,
    String subject_name, int credit_hour)
    {
        this.subject_code = subject_code;
        this.subject_name = subject_name;
        this.credit_hour = credit_hour;
    }
    
    public String getSubjectCode()
    {
        return subject_code;
    }
    
    public String getSubjectName()
    {
        return subject_name;
    }
    
    public int getCreditHour()
    {
        return credit_hour;
    }
    
    public void setSubjectName(String name)
    {
        subject_name = name;
    }
    
    public void setCreditHour(int hour)
    {
        credit_hour = hour;
    }
    
    @Override
    public String toString()
    {
        return subject_code + "-" + subject_name;
    }
}
