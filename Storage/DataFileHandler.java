package Storage;
import Service.GradeManager;
import Model.Student;
import Model.Subject;
import Model.StudentSubject;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class DataFileHandler
{
    
    private static final String STUDENT_FILE = "students.csv";
    private static final String SUBJECT_FILE = "subjects.csv";
    private static final String RESULT_FILE = "results.csv";

    private DataFileHandler()
    {
    }

    public static void saveAll(GradeManager manager)
    {
        saveStudents(manager);
        saveSubjects(manager);
        saveResults(manager);
    }

    public static void loadAll(GradeManager manager)
    {
        manager.clearAll();
        loadStudents(manager);
        loadSubjects(manager);
        loadResults(manager);
    }

    private static void saveStudents(GradeManager manager)
    {
        try (PrintWriter writer = new PrintWriter(new FileWriter(STUDENT_FILE)))
        {
            for (Student student : manager.getStudents())
            {
                writer.println(
                    escape(student.getStudentId()) + ","
                    + escape(student.getName())
                );
            }
        }
        catch (IOException exception)
        {
            showError("Unable to save students: " + exception.getMessage());
        }
    }

    private static void saveSubjects(GradeManager manager)
    {
        try (PrintWriter writer = new PrintWriter(new FileWriter(SUBJECT_FILE)))
        {
            for (Subject subject : manager.getSubjects())
            {
                writer.println(
                    escape(subject.getSubjectCode()) + ","
                    + escape(subject.getSubjectName()) + ","
                    + subject.getCreditHour()
                );
            }
        }
        catch (IOException exception)
        {
            showError("Unable to save subjects: " + exception.getMessage());
        }
    }

    private static void saveResults(GradeManager manager)
    {
        try (PrintWriter writer = new PrintWriter(new FileWriter(RESULT_FILE)))
        {
            for (StudentSubject result : manager.getStudentSubjects())
            {
                writer.println(
                    escape(result.getStudent().getStudentId()) + ","
                    + escape(result.getSubject().getSubjectCode()) + ","
                    + result.getAssignmentMark() + ","
                    + result.getTestMark() + ","
                    + result.getFinalMarks()
                );
            }
        }
        catch (IOException exception)
        {
            showError("Unable to save results: " + exception.getMessage());
        }
    }

    private static void loadStudents(GradeManager manager)
    {
        File file = new File(STUDENT_FILE);

        if (!file.exists())
        {
            return;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file)))
        {
            String line;

            while ((line = reader.readLine()) != null)
            {
                String[] values = line.split(",", -1);

                if (values.length == 2)
                {
                    manager.addStudent(
                        new Student(values[0].trim(), values[1].trim())
                    );
                }
            }
        }
        catch (IOException exception)
        {
            showError("Unable to load students: " + exception.getMessage());
        }
    }

    private static void loadSubjects(GradeManager manager)
    {
        File file = new File(SUBJECT_FILE);

        if (!file.exists())
        {
            return;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file)))
        {
            String line;

            while ((line = reader.readLine()) != null)
            {
                String[] values = line.split(",", -1);

                if (values.length == 3)
                {
                    int creditHour = Integer.parseInt(values[2].trim());

                    manager.addSubject(
                        new Subject(
                            values[0].trim(),
                            values[1].trim(),
                            creditHour
                        )
                    );
                }
            }
        }
        catch (IOException | NumberFormatException exception)
        {
            showError("Unable to load subjects: " + exception.getMessage());
        }
    }

    private static void loadResults(GradeManager manager)
    {
        File file = new File(RESULT_FILE);

        if (!file.exists())
        {
            return;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file)))
        {
            String line;

            while ((line = reader.readLine()) != null)
            {
                String[] values = line.split(",", -1);

                if (values.length == 5)
                {
                    Student student = manager.findStudentById(values[0].trim());
                    Subject subject = manager.findSubjectByCode(values[1].trim());

                    if (student != null && subject != null)
                    {
                        manager.addStudentSubject(
                            new StudentSubject(
                                student,
                                subject,
                                Double.parseDouble(values[2].trim()),
                                Double.parseDouble(values[3].trim()),
                                Double.parseDouble(values[4].trim())
                            )
                        );
                    }
                }
            }
        }
        catch (IOException | NumberFormatException exception)
        {
            showError("Unable to load results: " + exception.getMessage());
        }
    }

    private static String escape(String value)
    {
        return value.replace(",", " ");
    }

    private static void showError(String message)
    {
        System.out.println(message);
    }

    
}
