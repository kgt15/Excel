package mec.mec.excel16;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by gopikm on 18/6/16.
 */
public class event {

    private String eventid;
    private String websitelink;
    private String eventformat;
    private String name;
    private String details;
    private String image;
    private String youtubelink;
    private String description;
    private ArrayList<String> rules;
    private HashMap<String,String> contacts;


    public HashMap<String, String> getContacts() {
        return contacts;
    }
    public void setContacts(HashMap<String, String> contacts) {
        this.contacts = contacts;
    }
    public String getEventid() {
        return eventid;
    }

    public void setEventid(String eventid) {
        this.eventid = eventid;
    }

    public String getWebsitelink() {
        return websitelink;
    }

    public void setWebsitelink(String websitelink) {
        this.websitelink = websitelink;
    }

    public String getEventformat() {
        return eventformat;
    }

    public void setEventformat(String eventformat) {
        this.eventformat = eventformat;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getYoutubelink() {
        return youtubelink;
    }

    public void setYoutubelink(String youtubelink) {
        this.youtubelink = youtubelink;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public ArrayList<String> getRules() {
        return rules;
    }

    public void setRules(ArrayList<String> rules) {
        this.rules = rules;
    }





}
