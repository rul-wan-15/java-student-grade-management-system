# java-student-grade-management-system
This project is organised into several Java packages to separate the responsibilities of each class

- Model contains the main data classes, including students, subjects, users, and student results.
- Service contains the business logic for managing records and calculating grades.
- GUI contains the Java Swing windows and panels.
- Session stores the currently logged-in user during the application session.
- Storage handles reading and writing data using CSV files.
- Data contains the saved student, subject, and result records.

Run Project
- Run the Main.java

Class Relationships
- The StudentSubject class connects one Student object with one Subject object and stores the marks obtained for that subject.
- A student taking five subjects will therefore have five StudentSubject records.
- The GradeManager stores the system data using three ArrayList collections:
  - ArrayList<Student> students;
  - ArrayList<Subject> subjects;
  - ArrayList<StudentSubject> studentSubjects;
- The same GradeManager object is passed to the GUI panels so that all panels access the same student, subject, and result records.
