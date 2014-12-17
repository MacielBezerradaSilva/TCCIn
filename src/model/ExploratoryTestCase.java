/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author rparaujo
 */
public class ExploratoryTestCase {
    
    private String id;
    private String abstractString;
    private String objectiveString;
    private String timeConstraints;
    private int minimum;
    private int maximum;
    private String createdDate;
    private String requirements;
    private String setup;
    private String notes;
    private String concentrationAreas;
    private String issues;
    private String executionNotes;

    public String getId() {
        return id != null ? id : "(None)";
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAbstractString() {
        return abstractString != null ? abstractString : "(None)";
    }

    public void setAbstractString(String abstractString) {
        this.abstractString = abstractString;
    }

    public String getObjectiveString() {
        return objectiveString != null ? objectiveString : "(None)";
    }

    public void setObjectiveString(String objectiveString) {
        this.objectiveString = objectiveString;
    }

    public String getTimeConstraints() {
        return timeConstraints;
    }

    public void setTimeConstraints(String timeConstraints) {
        this.timeConstraints = timeConstraints;
    }

    public int getMinimum() {
        return minimum;
    }

    public void setMinimum(int minimum) {
        this.minimum = minimum;
    }

    public int getMaximum() {
        return maximum;
    }

    public void setMaximum(int maximum) {
        this.maximum = maximum;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public String getRequirements() {
        return requirements != null ? requirements : "(None)";
    }

    public void setRequirements(String requirements) {
        this.requirements = requirements;
    }

    public String getSetup() {
        return setup != null ? setup : "(None)";
    }

    public void setSetup(String setup) {
        this.setup = setup;
    }

    public String getNotes() {
        return notes != null ? notes : "(None)";
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getConcentrationAreas() {
        return concentrationAreas != null ? concentrationAreas : "(None)";
    }

    public void setConcentrationAreas(String concentrationAreas) {
        this.concentrationAreas = concentrationAreas;
    }

    public String getIssues() {
        return issues != null ? issues : "(None)";
    }

    public void setIssues(String issues) {
        this.issues = issues;
    }

    public String getExecutionNotes() {
        return executionNotes != null ? executionNotes : "(None)";
    }

    public void setExecutionNotes(String executionNotes) {
        this.executionNotes = executionNotes;
    }
    
    public void printValues() {
        if (this.id == null) { this.id = "(None)"; }
        if (this.abstractString == null) { this.abstractString = "(None)"; }
        if (this.objectiveString == null) { this.objectiveString = "(None)"; }
        if (this.minimum < 0 || this.minimum > 1000) { this.minimum = -1; }
        if (this.maximum < 0 || this.maximum > 1000) { this.maximum = -1; }
        if (this.createdDate == null) { this.createdDate = "(None)"; }
        if (this.requirements == null) { this.requirements = "(None)"; }
        if (this.setup == null) { this.setup = "(None)"; }
        if (this.notes == null) { this.notes = "(None)"; }
        if (this.concentrationAreas == null) { this.concentrationAreas = "(None)"; }
        if (this.issues == null) { this.issues= "(None)"; }
        if (this.executionNotes == null) { this.executionNotes = "(None)"; }
        
        System.out.println("ID: " + this.id);
        System.out.println("Abstract: " + this.abstractString);
        System.out.println("Objective: " + this.objectiveString);
        System.out.println("Time Constraints: \nMinimum: " + this.minimum +
                            "\nMaximum: " + this.maximum);
        System.out.println("Date: " + this.createdDate);
        System.out.println("Requirements: " + this.requirements);
        System.out.println("Setup: " + this.setup);
        System.out.println("Notes: " + this.notes);
        System.out.println("Concentration: " + this.concentrationAreas);
        System.out.println("Issues: " + this.issues);
        System.out.println("Execution Notes: " + this.executionNotes);
    }
    
}

//private String id;
//    private String abstractString;
//    private String objectiveString;
//    private String timeConstraints;
//    private int minimum;
//    private int maximum;
//    private String createdDate;
//    private String requirements;
//    private String setup;
//    private String notes;
//    private String concentrationAreas;
//    private String issues;
//    private String executionNotes;
