package model;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Heitor
 */
public class Session {

    private String id = "", testerId = "", testerName = "", duration = "", comments="";
    private Charter charter;
    private Product product;
    private List<Issue> issues;
    private int currentTime;

    public Session(Charter c) {
        this.charter = c;
        issues = new ArrayList<Issue>();
    }
    
    //public Session() {}

    public String getTesterId() {
        return testerId;
    }

    public void setTesterId(String testerId) {
        this.testerId = testerId;
    }

    public String getTesterName() {
        return testerName;
    }

    public void setTesterName(String testerName) {
        this.testerName = testerName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public Charter getCharter() {
        return charter;
    }

    public void setCharter(Charter charter) {
        this.charter = charter;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
    
    public synchronized void addIssue(Issue issue) {
        issues.add(issue);
    }

    public synchronized void removeIssue(Issue issue) {
        issues.remove(issue);
    }

    public List<Issue> getIssues() {
        return issues;
    }

    public void addIssues(Issue issue) {
        issues.add(issue);
    }
    
    public void setCurrentTime(int value) {
        this.currentTime = value;
    }
    
    public int getCurrentTime(){
        return this.currentTime;
    }
    
    @Override
    public String toString() {
        String str = "";
        str += "<session>";
//        str += "<testerField>" + (tester == null ? "" : tester) + "</testerField>";
        str += "<currTime>" + currentTime + "</currTime>";
        str += "<idField>" + (id == null ? "" : id) + "</idField>";
        str += "<durationField>" + (duration == null ? "" : duration) + "</durationField>";
        str += "<comments>" + (comments == null ? "" : comments) + "</comments>";
        str += (charter == null ? "<charter></charter>" : charter.toString());
        str += (product == null ? "<product></product>" : product.toString());
        for (Issue i : issues) {
            str += i.toString();
        }
        str += "</session>";
        return str;
    }

}