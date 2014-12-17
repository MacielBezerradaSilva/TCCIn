/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author heitor
 */
public class Issue {
    String name="", description="", type="";

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
    
    @Override
    public String toString(){
        String str = 
          "=========== ISSUE ===========\n"
        + "Name: " + getName() + "\n"
        + "Type: " + getType()+ "\n"
        + "Description: " + getDescription() + "\n"
        + "============================\n\n";
//        str = 
//                "<issue>\n" +
//                    "\t<name>" + getName() +"</name>\n" +
//                    "\t<type>" + getType() + "</type>\n" +
//                    "\t<description>" + getDescription() + "</description>\n" +
//                "</issue>\n\n";
        return str;
    }
    
}
