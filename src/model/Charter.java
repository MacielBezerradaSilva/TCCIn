
package model;

import java.util.Date;

public class Charter {
    private String name, etId, objective, requirements, setup, notes, keyAreas,issuesToBeAware, importantNotes;
    private int maxTime, minTime;
    private Date creationDate;

    public Charter(){
        maxTime = 0;
        minTime = 0;
    }
    
    public Charter(String name){
        this.name = name;
    }
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEtId() {
        return etId;
    }

    public void setEtId(String etId) {
        this.etId = etId;
    }

    public String getObjective() {
        return objective;
    }

    public void setObjective(String objective) {
        this.objective = objective;
    }

    public String getRequirements() {
        return requirements;
    }

    public void setRequirements(String requirements) {
        this.requirements = requirements;
    }

    public String getSetup() {
        return setup;
    }

    public void setSetup(String setup) {
        this.setup = setup;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getKeyAreas() {
        return keyAreas;
    }

    public void setKeyAreas(String keyAreas) {
        this.keyAreas = keyAreas;
    }

    public String getIssuesToBeAware() {
        return issuesToBeAware;
    }

    public void setIssuesToBeAware(String issuesToBeAware) {
        this.issuesToBeAware = issuesToBeAware;
    }

    public String getImportantNotes() {
        return importantNotes;
    }

    public void setImportantNotes(String importantNotes) {
        this.importantNotes = importantNotes;
    }

    public int getMaxTime() {
        return maxTime;
    }

    public void setMaxTime(int maxTime) {
        this.maxTime = maxTime;
    }

    public int getMinTime() {
        return minTime;
    }

    public void setMinTime(int minTime) {
        this.minTime = minTime;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    @Override
    public String toString() {
//        str += "<charter>" + 
//                "<name>" + (name == null ? "" : name) + "</name>"+
//                "<etId>" + (etId == null ? "" : etId) + "</etId>"+
//                "<objective>" + (objective == null ? "" : objective) + "</objective>" +
//                "<requirements>" + (requirements == null ? "" : requirements) + "</requirements>"+
//                "<setup>" + (setup == null ? "" : setup) + "</setup>"+
//                "<notes>" + (notes == null ? "" : notes) + "</notes>"+
//                "<keyAreas>" + (keyAreas == null ? "" : keyAreas) + "</keyAreas>"+
//                "<issuesToBeAware>" + (issuesToBeAware == null ? "" : issuesToBeAware) + "</issuesToBeAware>"+
//                "<importantNotes>" + (importantNotes == null ? "" : importantNotes) + "</importantNotes>"+
//                "<maxTime>" + maxTime + "</maxTime>"+
//                "<minTime>" + minTime + "</minTime>"+
//                "<creationDate>" + (creationDate == null ? "" : creationDate) + "</creationDate>";
//        str += "</charter>";
        
        String str = "ET " + getEtId() + ": " + getName();
        return str;
    }
   
}
