package model;

import java.util.List;

/**
 *
 * @author Heitor
 */
public class Product {

    private String id, name, project;
    private List<String> labels;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProject() {
        return project;
    }

    public void setProject(String project) {
        this.project = project;
    }

    public List<String> getLabels() {
        return labels;
    }
    
    public String[] getLabelsAsArray() {
        
        int size = labels.size();
        String[] result = new String[size];
        
        for (int i = 0; i < size; i++) {
            result[i] = labels.get(i);    
        }
        
        return result;
    }

    public void setLabels(List<String> labels) {
        this.labels = labels;
    }

    @Override
    public String toString() {
        String str = "";
        str = "<product>"
                + "<name>" + name + "</name>"
                + "<project>" + project + "</project>";
        
        if (labels != null) {
            for (String i : labels) {
                if (i != null) {
                    str += "<label>" + i + "</label>";
                }
            }
        }
        str+="</product>";
        return str;
    }
}
