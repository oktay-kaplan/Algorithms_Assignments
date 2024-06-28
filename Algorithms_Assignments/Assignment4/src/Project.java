import java.io.Serializable;
import java.util.*;

public class Project implements Serializable {
    static final long serialVersionUID = 33L;
    private final String name;
    private final List<Task> tasks;

    public Project(String name, List<Task> tasks) {
        this.name = name;
        this.tasks = tasks;
    }

    /**
     * @return the total duration of the project in days
     */
    public int getProjectDuration() {
        int projectDuration = 0;
        int[] schedule = getEarliestSchedule();
        projectDuration = schedule[schedule.length - 1] + tasks.get(schedule.length - 1).getDuration();
        return projectDuration;
    }

    /**
     * Schedule all tasks within this project such that they will be completed as early as possible.
     *
     * @return An integer array consisting of the earliest start days for each task.
     */
    public int[] getEarliestSchedule() {
        int[] earliestStart = new int[tasks.size()];
        Map<Integer, Integer> taskIndexMap = new HashMap<>();
        for (int i = 0; i < tasks.size(); i++) {
            taskIndexMap.put(tasks.get(i).getTaskID(), i);
        }

        for (Task task : tasks) {
            int index = taskIndexMap.get(task.getTaskID());
            int maxDependentTime = 0;
            for (Integer dependency : task.getDependencies()) {
                maxDependentTime = Math.max(maxDependentTime, earliestStart[taskIndexMap.get(dependency)] + tasks.get(taskIndexMap.get(dependency)).getDuration());
            }
            earliestStart[index] = maxDependentTime;
        }

        for (int i = 0; i < tasks.size(); i++) {
            Task task = tasks.get(i);
            int index = taskIndexMap.get(task.getTaskID());
            int startTime = earliestStart[index];
            for (Integer dependency : task.getDependencies()) {
                int dependencyIndex = taskIndexMap.get(dependency);
                int dependencyEndTime = earliestStart[dependencyIndex] + tasks.get(dependencyIndex).getDuration();
                startTime = Math.max(startTime, dependencyEndTime);
            }
            earliestStart[index] = startTime;
        }

        return earliestStart;
    }



    public static void printlnDash(int limit, char symbol) {
        for (int i = 0; i < limit; i++) System.out.print(symbol);
        System.out.println();
    }

    /**
     * Some free code here. YAAAY! 
     */
    public void printSchedule(int[] schedule) {
        int limit = 65;
        char symbol = '-';
        printlnDash(limit, symbol);
        System.out.println(String.format("Project name: %s", name));
        printlnDash(limit, symbol);

        // Print header
        System.out.println(String.format("%-10s%-45s%-7s%-5s","Task ID","Description","Start","End"));
        printlnDash(limit, symbol);
        for (int i = 0; i < schedule.length; i++) {
            Task t = tasks.get(i);
            System.out.println(String.format("%-10d%-45s%-7d%-5d", i, t.getDescription(), schedule[i], schedule[i]+t.getDuration()));
        }
        printlnDash(limit, symbol);
        System.out.println(String.format("Project will be completed in %d days.", tasks.get(schedule.length-1).getDuration() + schedule[schedule.length-1]));
        printlnDash(limit, symbol);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Project project = (Project) o;

        int equal = 0;

        for (Task otherTask : ((Project) o).tasks) {
            if (tasks.stream().anyMatch(t -> t.equals(otherTask))) {
                equal++;
            }
        }

        return name.equals(project.name) && equal == tasks.size();
    }

}