package storage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;

import java.util.Scanner;

import tasklist.TaskList;

import duke.DukeException;

public class Storage {
    private final String filePath;
    private final String OUTPUT_FORMAT = "  %s\n";
    private final TaskList taskList;

    public Storage(String filePath, TaskList taskList) {
        this.filePath = filePath;
        this.taskList = taskList;
    }

    public void getTodoList() throws DukeException {
        try {
            File f = new File(filePath);
            Scanner s = new Scanner(f);
            while (s.hasNext()) {
                String task = s.nextLine();
                formatStringToTask(task);
            }
        } catch (FileNotFoundException e) {
            throw new DukeException("File not found");
        }
    }

    public void formatStringToTask(String task) {
        String[] split = task.split(" ");
        String body = task.substring(6);
        try {
            switch (split[0]) {
            case "[T][O]":
                taskList.addTodoItem(body, true);
                break;
            case "[T][X]":
                taskList.addTodoItem(body, false);
                break;
            case "[D][O]":
                taskList.addDeadline(body, true);
                break;
            case "[D][X]":
                taskList.addDeadline(body, false);
                break;
            case "[E][O]":
                taskList.addEvent(body, true);
                break;
            default:
                taskList.addEvent(body, false);
            }
        } catch (DukeException e) {
            System.out.printf(OUTPUT_FORMAT, e.getMessage());
        }
    }

    public void overwriteTodoList() {
        try {
            FileWriter fw = new FileWriter(filePath);
            fw.write(taskList.formatTodoListToString());
            fw.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
