import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Scanner;

public class Duke {

    public enum SpecialTaskKeyword {
        TODO,
        DEADLINE,
        EVENT
    }
    public static void main(String[] args) {
        System.out.println("Hello! I'm HAPPY\nWhat can I do for you?\n");

        Scanner scanner = new Scanner(System.in);
        ArrayList<Task> storedTasks = new ArrayList<>();
        SpecialTaskKeyword[] specialTasksKeywords = SpecialTaskKeyword.values();
        while (true) {
            String userInput = scanner.nextLine();
            try {
                String[] userInputSegmented = userInput.split(" ");
                int numStoredTasks = storedTasks.size();
                String actionWord = userInputSegmented[0];
                if (actionWord.equals("bye")) {
                    System.out.println("Bye. Hope to see you again soon!");
                    break;
                }
                else if (actionWord.equals("list")) {
                    System.out.println("Here are the tasks in your list:");
                    for (int i = 0; i < numStoredTasks; i++){
                        Task task = storedTasks.get(i);
                        System.out.printf("%d.%s%n", i + 1, task);
                    }
                }
                else if (actionWord.equals("mark")) {
                    int taskNumber = Integer.parseInt(userInputSegmented[1]) - 1;
                    Task task = storedTasks.get(taskNumber);
                    task.markAsDone();
                    System.out.println("Nice! I've marked this task as done:");
                    System.out.printf("  %s%n", task);
                }
                else if (actionWord.equals("unmark")) {
                    int taskNumber = Integer.parseInt(userInputSegmented[1]) - 1;
                    Task task = storedTasks.get(taskNumber);
                    task.unmark();
                    System.out.println("OK, I've marked this task as not done yet:");
                    System.out.printf("  %s%n", task);
                }
                else if (actionWord.equals("delete")) {
                    int taskNumber = Integer.parseInt(userInputSegmented[1]) - 1;
                    Task task = storedTasks.get(taskNumber);
                    System.out.println("Noted. I've removed this task:");
                    System.out.println("  " + task);
                    System.out.printf("Now you have %d tasks in the list.%n", numStoredTasks - 1);
                    storedTasks.remove(taskNumber);
                }
                else if (Arrays.stream(specialTasksKeywords).anyMatch(
                        keyword -> keyword.toString().toLowerCase().equals(actionWord))) {
                    try {
                        if (userInputSegmented.length == 1) {
                            throw new InvalidTaskException("ERROR: The description of a " + userInputSegmented[0] + " cannot be empty.");
                        }
                        System.out.println("Got it. I've added this task:");
                        if (actionWord.equals("todo")) {
                            Todo todoTask = new Todo(String.join(" ", Arrays.copyOfRange(userInputSegmented, 1, userInputSegmented.length)));
                            storedTasks.add(todoTask);
                            System.out.println("  " + todoTask);
                        }
                        else if (actionWord.equals("deadline")) {
                            int startIndex = 0;
                            while (startIndex < userInputSegmented.length) {
                                startIndex++;
                                if (userInputSegmented[startIndex].equals("/by")) {
                                    startIndex++;
                                    break;
                                }
                            }
                            Deadline deadlineTask = new Deadline(
                                    String.join(" ", Arrays.copyOfRange(userInputSegmented, 1, startIndex - 1)),
                                    String.join(" ", Arrays.copyOfRange(userInputSegmented, startIndex, userInputSegmented.length)));
                            storedTasks.add(deadlineTask);
                            System.out.println("  " + deadlineTask);
                        }
                        else {
                            int fromIndex = 0;
                            int toIndex = 0;
                            while (fromIndex < userInputSegmented.length) {
                                fromIndex++;
                                toIndex++;
                                if (userInputSegmented[fromIndex].equals("/from")) {
                                    fromIndex++;
                                    toIndex++;
                                    break;
                                }
                            }
                            while (toIndex < userInputSegmented.length) {
                                toIndex++;
                                if (userInputSegmented[toIndex].equals("/to")) {
                                    toIndex++;
                                    break;
                                }
                            }

                            Event eventTask = new Event(
                                    String.join(" ", Arrays.copyOfRange(userInputSegmented, 1, fromIndex - 1)),
                                    String.join(" ", Arrays.copyOfRange(userInputSegmented, fromIndex, toIndex - 1)),
                                    String.join(" ", Arrays.copyOfRange(userInputSegmented, toIndex, userInputSegmented.length))
                            );
                            storedTasks.add(eventTask);
                            System.out.println("  " + eventTask);
                        }
                        System.out.printf("Now you have %d tasks in the list.%n", numStoredTasks + 1);
                    } catch (InvalidTaskException e) {
                        System.out.println(e.getMessage());
                    }
                }
                else {
                    throw new InvalidInputException("ERROR: Invalid input");
                }
            } catch (InvalidInputException e) {
                System.out.println(e.getMessage());
            }
        }
        scanner.close();
    }
}
