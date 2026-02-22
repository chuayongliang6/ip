# SWE User Guide

## Introduction

**SWE** is a simple task management application that allows you to manage your to-do list via a command-line interface (
CLI).

---

## Getting Started

1. Ensure you have Java 11 or above installed in your Computer.
2. Launch the application by running the main class `SWE.java`.
3. The application will display a welcome message.
4. Enter commands in the CLI to manage your tasks.
5. Type `bye` to exit the application.

---

## Features

### Notes about the command format:

* Words in `UPPER_CASE` are the parameters to be supplied by the user.<br>
  e.g. in `todo DESCRIPTION`, `DESCRIPTION` is a parameter which can be used as `todo read book`.
* Items in square brackets are optional.<br>
  e.g `find KEYWORD [MORE_KEYWORDS]` can be used as `find book` or as `find book journal`.
* Parameters for task creation must follow the specified order of prefixes (e.g., `/by`, `/from`, `/to`).
* Extraneous parameters for commands that do not take in parameters (such as `list` and `bye`) will be ignored.<br>
  e.g. if the command specifies `list 123`, it will be interpreted as `list`.

---

### Adding a To-Do task: `todo`

Adds a to-do task to the list. To-do tasks are simple tasks without any date or time constraints.

Format: `todo DESCRIPTION`

Example:

* `todo read book`

### Adding a Deadline task: `deadline`

Adds a task with a specific deadline.

Format: `deadline DESCRIPTION /by DATE`

Example:

* `deadline submit assignment /by 2023-10-01`

### Adding an Event task: `event`

Adds a task that occurs within a specific time range.

Format: `event DESCRIPTION /from START_TIME /to END_TIME`

Example:

* `event team meeting /from 2pm /to 4pm`

### Listing all tasks: `list`

Shows a list of all tasks currently in your task list, including their status (Done/Not Done) and type.

Format: `list`

### Marking a task as done: `mark`

Marks an existing task in the list as completed.

Format: `mark INDEX`

* Marks the task at the specified `INDEX`.
* The index refers to the index number shown in the displayed task list.
* The index **must be a positive integer** 1, 2, 3, …​

Example:

* `mark 1` Marks the 1st task in the list as done.

### Deleting a task: `delete`

Deletes the specified task from the list.

Format: `delete INDEX`

* Deletes the task at the specified `INDEX`.
* The index refers to the index number shown in the displayed task list.
* The index **must be a positive integer** 1, 2, 3, …​

Examples:

* `list` followed by `delete 2` deletes the 2nd task in the list.

### Locating tasks by keyword: `find`

Finds tasks whose descriptions contain any of the given keywords.

Format: `find KEYWORD [MORE_KEYWORDS]`

* The search is **case-sensitive**. e.g. `book` will not match `Book`.
* Only the description is searched.
* Tasks matching at least one keyword will be returned (i.e. OR search).

Examples:

* `find book` returns `read book` and `buy book`
* `find project meeting` returns `finish project`, `team meeting`

### Exiting the program: `bye`

Exits the program.

Format: `bye`

---

## Data Storage

### Saving the data

SWE data are saved in the hard disk automatically after any command that changes the data. There is no need to save
manually.

### Editing the data file

SWE data are saved automatically as a text file at `./data/duke.txt`. Advanced users are welcome to update data directly
by editing that data file.

> [!CAUTION]
> If your changes to the data file make its format invalid, SWE may fail to load the data correctly. It is recommended
> to take a backup of the file before editing it.

---

## FAQ

**Q: What happens if I enter an invalid command?**
**A:** The app will display "I'm sorry, but I don't know what that command means. Please input correct command."

**Q: Are tasks case-sensitive?**
**A:** Task descriptions retain their casing, but the `find` command requires an exact case match to locate them.

---

## Command Summary

| Action       | Format                                  | Examples                          |
|:-------------|:----------------------------------------|:----------------------------------|
| **To-Do**    | `todo DESCRIPTION`                      | `todo read book`                  |
| **Deadline** | `deadline DESCRIPTION /by DATE`         | `deadline submit /by 2023-10-01`  |
| **Event**    | `event DESCRIPTION /from START /to END` | `event meeting /from 2pm /to 4pm` |
| **List**     | `list`                                  | `list`                            |
| **Mark**     | `mark INDEX`                            | `mark 1`                          |
| **Delete**   | `delete INDEX`                          | `delete 2`                        |
| **Find**     | `find KEYWORD [MORE_KEYWORDS]`          | `find book`                       |
| **Bye**      | `bye`                                   | `bye`                             |