package Service;
import Model.Student;
import Model.Subject;
import Model.StudentSubject;
import java.util.ArrayList;

public class GradeManager
{
    private ArrayList<Student> students;
    private ArrayList<Subject> subjects;
    private ArrayList<StudentSubject> student_subjects;
    
    public GradeManager()
    {
        students = new ArrayList<Student>();
        subjects = new ArrayList<Subject>();
        student_subjects = new ArrayList<StudentSubject>();
    }
    
    public ArrayList<Student> getStudents()
    {
        return students;
    }

    public ArrayList<Subject> getSubjects()
    {
        return subjects;
    }

    public ArrayList<StudentSubject> getStudentSubjects()
    {
        return student_subjects;
    }
    
    public boolean addStudent(Student student)
    {
        if (student == null || findStudentById(student.getStudentId()) != null)
        {
            return false;
        }
        
        students.add(student);
        return true;
    }
    
    public Student findStudentById(String studentId)
    {
        for(Student student : students)
        {
            if(student.getStudentId().equalsIgnoreCase(studentId))
            {
                return student;
            }
        }
        
        return null;
    }
    
    
    public boolean updateStudent(String studentId, String newName)
    {
        Student student = findStudentById(studentId);
        
        if(student == null)
        {
            return false;
        }
        
        student.setName(newName);
        return true;
    }
    
    public boolean deleteStudent(String studentId)
    {
        Student student = findStudentById(studentId);
        
        if(student == null)
        {
            return false;
        }
        
        student_subjects.removeIf(
            item -> item.getStudent().getStudentId().equalsIgnoreCase(studentId)
        );
        
        students.remove(student);
        return true;
    }
    
    public boolean addSubject(Subject subject)
    {
        if(subject == null || findSubjectByCode(subject.getSubjectCode()) != null)
        {
            return false;
        }
        
        subjects.add(subject);
        return true;
    }
    
    public Subject findSubjectByCode(String subjectCode)
    {
        for(Subject subject : subjects)
        {
            if(subject.getSubjectCode().equalsIgnoreCase(subjectCode))
            {
                return subject;
            }
        }
        
        return null;
    }
    
    
    public boolean updateSubject(
        String subject_code,
        String new_name,
        int new_hour)
    {
        Subject subject = findSubjectByCode(subject_code);
        
        if(subject == null)
        {
            return false;
        }
        
        subject.setSubjectName(new_name);
        subject.setCreditHour(new_hour);
        return true;
    }
    
    public boolean deleteSubject(String subjectCode)
    {
        Subject subject = findSubjectByCode(subjectCode);

        if (subject == null)
        {
            return false;
        }

        student_subjects.removeIf(
            item -> item.getSubject().getSubjectCode().equalsIgnoreCase(subjectCode)
        );

        subjects.remove(subject);
        return true;
    }
    
     public boolean addStudentSubject(StudentSubject result)
    {
        if (result == null)
        {
            return false;
        }

        if (findStudentSubject(
            result.getStudent().getStudentId(),
            result.getSubject().getSubjectCode()) != null)
        {
            return false;
        }

        student_subjects.add(result);
        return true;
    }

    public StudentSubject findStudentSubject(
        String studentId,
        String subjectCode)
    {
        for (StudentSubject result : student_subjects)
        {
            boolean sameStudent = result.getStudent().getStudentId().equalsIgnoreCase(studentId);

            boolean sameSubject = result.getSubject().getSubjectCode().equalsIgnoreCase(subjectCode);

            if (sameStudent && sameSubject)
            {
                return result;
            }
        }

        return null;
    }

    public boolean updateStudentSubject(
        String studentId,
        String subjectCode,
        double assignmentMark,
        double testMark,
        double finalExamMark
    )
    {
        StudentSubject result = findStudentSubject(studentId, subjectCode);

        if (result == null)
        {
            return false;
        }

        result.setAssignmentMark(assignmentMark);
        result.setTestMark(testMark);
        result.setFinalExamMark(finalExamMark);
        return true;
    }

    public boolean deleteStudentSubject(String studentId, String subjectCode)
    {
        StudentSubject result = findStudentSubject(studentId, subjectCode);

        if (result == null)
        {
            return false;
        }

        student_subjects.remove(result);
        return true;
    }
    
    
    public ArrayList<StudentSubject> findResultByStudent(String studentId)
    {
        ArrayList<StudentSubject> results = new ArrayList<StudentSubject>();
        
        for(StudentSubject result : student_subjects)
        {
            if(result.getStudent().getStudentId().equalsIgnoreCase(studentId))
            {
                results.add(result);
            }
        }
        
        return results;
    }
    
    public double calculateStudentGPA(String studentId)
    {
        ArrayList<StudentSubject> results = findResultByStudent(studentId);
        
        if(results.isEmpty())
        {
            return 0;
        }
        
        double total_points = 0;
        int total_credit_hours = 0;
        
        for(StudentSubject result : results)
        {
            int credit_hour = result.getSubject().getCreditHour();
            total_points += result.getGradePoint() * credit_hour;
            total_credit_hours += credit_hour;
        }
        
        if(total_credit_hours == 0)
        {
            return 0;
        }
        
        return total_points / total_credit_hours;
    }
    
    public void clearAll()
    {
        students.clear();
        subjects.clear();
        student_subjects.clear();
    }
}
