# Student Academic Task Manager

A small offline Java console application for managing academic tasks such as assignments, exams, lab work, and projects.

## Main Features

- Add a task
- View all tasks
- Search tasks by title, description, category, or ID
- Update a task
- Delete a task
- Mark a task as completed
- Filter by category
- Filter by completion status
- Sort by due date or priority
- Display a task summary/dashboard
- Save tasks to a local text file
- Automatically load saved tasks when the program starts
- Validate user input
- Handle invalid input without crashing
- Exit safely

## Technologies Used

- Java
- Maven
- JUnit 5 for automated tests
- Java standard library for application logic and file handling

No internet connection, database, paid API, or GUI is required.

## Requirements

- Java Development Kit (JDK) 17 or newer
- Apache Maven 3.8 or newer
- A normal terminal/command prompt

The project is designed to work on Windows, macOS, and Linux.

## Check Java Installation

Run:

```bash
java -version
javac -version
```

You should see Java 17 or a newer version.

## Check Maven Installation

Run:

```bash
mvn -version
```

Maven should report its version and the Java version it is using.

## Folder Structure

```text
StudentAcademicTaskManager/
├── README.md
├── .gitignore
├── pom.xml
├── LICENSE
├── data/
│   └── tasks.txt
├── docs/
│   └── PROJECT_REPORT.md
└── src/
    ├── main/
    │   └── java/
    │       └── com/
    │           └── studenttaskmanager/
    │               ├── Main.java
    │               ├── model/
    │               │   ├── Category.java
    │               │   ├── Priority.java
    │               │   └── Task.java
    │               ├── service/
    │               │   └── TaskManager.java
    │               └── util/
    │                   ├── FileManager.java
    │                   └── InputValidator.java
    └── test/
        └── java/
            └── com/
                └── studenttaskmanager/
                    └── service/
                        └── TaskManagerTest.java
```

## Environment Setup

1. Install JDK 17+.
2. Install Maven.
3. Open a terminal.
4. Clone this repository.
5. Change into the repository directory.
6. Run the Maven commands below.

No IDE setup is necessary.

## Dependency Installation

Maven downloads the test dependency automatically. There are no application dependencies that need manual installation.

Run:

```bash
mvn clean compile
```

## Configuration

No external configuration is required.

The application stores its data in:

```text
data/tasks.txt
```

The program creates the `data` directory and file automatically if they do not exist.

The data file is relative to the directory from which the application is started. Therefore, run the application from the repository root.

## Compile

From the repository root:

```bash
mvn clean compile
```

## Run

From the repository root:

```bash
mvn exec:java
```

The Maven Exec Plugin starts:

```text
com.studenttaskmanager.Main
```

## Test

Run the automated tests with:

```bash
mvn test
```

## Recommended First Verification

Run these three commands:

```bash
mvn clean compile
mvn test
mvn exec:java
```

## Example Program Usage

A typical session can look like this:

```text
===== Student Academic Task Manager =====
1. Add task
2. View all tasks
3. Search task
4. Update task
5. Delete task
6. Mark task as completed
7. Filter by category
8. Filter by completion status
9. Sort tasks
10. Show dashboard
0. Exit

Enter your choice: 1

Enter title: DBMS Assignment
Enter description: Complete normalization questions
Enter category: 1
1. ASSIGNMENT
2. EXAM
3. LAB
4. PROJECT
5. OTHER
Choose category: 1
Enter due date (yyyy-MM-dd): 2026-09-25
Enter priority:
1. LOW
2. MEDIUM
3. HIGH
Choose priority: 3

Task added successfully. Task ID: 1
```

The exact menu continues to depend on the selected operation.

## Task Data Format

Tasks are saved in `data/tasks.txt`. Each task occupies one line, with fields separated by a tab character.

The stored fields are:

```text
id    title    description    category    dueDate    priority    completed
```

The file is intended for this application and should not be manually edited while the program is running.

## Important Classes

### `Task`
Represents one academic task. It stores the task ID, title, description, category, due date, priority, and completion status.

### `Category`
Enum containing the supported task categories.

### `Priority`
Enum containing LOW, MEDIUM, and HIGH.

### `TaskManager`
Contains the main task-management logic. It uses an `ArrayList<Task>` and provides methods for adding, searching, updating, deleting, completing, filtering, sorting, and summarizing tasks.

### `FileManager`
Loads and saves tasks using a relative local text-file path.

### `InputValidator`
Contains reusable validation methods for task fields and menu input.

### `Main`
Handles the command-line menu and user interaction. It calls the manager and file manager rather than storing task-management logic itself.

## Error Handling

The application handles common problems such as:

- Non-numeric menu input
- Invalid task IDs
- Empty titles
- Invalid dates
- Invalid category/priority choices
- Missing task IDs
- Malformed saved task lines
- File read/write problems

Invalid user input causes an explanatory message and returns to the relevant prompt instead of crashing the program.

If an individual malformed line is found in the saved file, that line is skipped and the remaining valid tasks can still be loaded.

## Troubleshooting

### `mvn` is not recognized

Maven is either not installed or its `bin` directory is not available on your PATH. Install Maven and reopen the terminal.

### `java` is not recognized

Install a JDK and make sure the JDK `bin` directory is available on your PATH.

### Wrong Java version

Run:

```bash
java -version
mvn -version
```

Make sure both are using JDK 17 or newer.

### No saved tasks appear

Check that the program is being run from the repository root and that `data/tasks.txt` exists. The application will create the file if necessary.

### Tests are not found

Run:

```bash
mvn clean test
```

from the repository root.

### The data file looks different after running

This is expected. The application rewrites the file with the current task list after changes.

## Design Notes

The project deliberately uses a small number of classes and standard Java collections/file APIs. The goal is to make the code easy to study and explain while still demonstrating object-oriented programming, enums, collections, sorting, validation, exception handling, and file handling.

## Academic Use

Review, test, understand, and personalize the project before submitting it. In particular, make sure the final report and examples match the work you actually performed.
