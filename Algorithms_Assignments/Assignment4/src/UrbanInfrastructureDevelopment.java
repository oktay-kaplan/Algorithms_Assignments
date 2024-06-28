import java.io.Serializable;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class UrbanInfrastructureDevelopment implements Serializable {
    static final long serialVersionUID = 88L;

    /**
     * Given a list of Project objects, prints the schedule of each of them.
     * Uses getEarliestSchedule() and printSchedule() methods of the current project to print its schedule.
     * @param projectList a list of Project objects
     */
    public void printSchedule(List<Project> projectList) {
        for (Project project : projectList) {
            int[] schedule = project.getEarliestSchedule();
            project.printSchedule(schedule);
        }
    }

    /**
     * Parse the input XML file and return a list of Project objects.
     *
     * @param filename the input XML file
     * @return a list of Project objects
     */
    public List<Project> readXML(String filename) {
        List<Project> projectList = new ArrayList<>();
        try {
            File file = new File(filename);
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(file);
            doc.getDocumentElement().normalize();

            NodeList projectNodes = doc.getElementsByTagName("Project");
            for (int i = 0; i < projectNodes.getLength(); i++) {
                Node projectNode = projectNodes.item(i);
                if (projectNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element projectElement = (Element) projectNode;
                    String projectName = projectElement.getElementsByTagName("Name").item(0).getTextContent();
                    List<Task> taskList = new ArrayList<>();
                    NodeList taskNodes = projectElement.getElementsByTagName("Task");
                    for (int j = 0; j < taskNodes.getLength(); j++) {
                        Node taskNode = taskNodes.item(j);
                        if (taskNode.getNodeType() == Node.ELEMENT_NODE) {
                            Element taskElement = (Element) taskNode;
                            int taskID = Integer.parseInt(taskElement.getElementsByTagName("TaskID").item(0).getTextContent());
                            String description = taskElement.getElementsByTagName("Description").item(0).getTextContent();
                            int duration = Integer.parseInt(taskElement.getElementsByTagName("Duration").item(0).getTextContent());
                            List<Integer> dependencies = new ArrayList<>();
                            NodeList dependsOnNodes = taskElement.getElementsByTagName("DependsOnTaskID");
                            for (int k = 0; k < dependsOnNodes.getLength(); k++) {
                                Node dependsOnNode = dependsOnNodes.item(k);
                                if (dependsOnNode.getNodeType() == Node.ELEMENT_NODE) {
                                    dependencies.add(Integer.parseInt(dependsOnNode.getTextContent()));
                                }
                            }
                            taskList.add(new Task(taskID, description, duration, dependencies));
                        }
                    }
                    projectList.add(new Project(projectName, taskList));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return projectList;
    }
}
