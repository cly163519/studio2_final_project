package model;

import java.text.SimpleDateFormat;
import java.util.Date;

import util.Constants;

/**
 * Constructor with basic information
 * 
 * @param id Unique task ID
 * @param title Task title
 * @param description Task description
 * @param level Task difficult level
 * @param category Task category (work/life/study)
 */
public class Task {
	
	
	private int id;
	private String title;
	private String description;
	private String category;
	private int level;
	private boolean isCompleted;
	private long createdDate;
	
	
	
	public Task() {
	    // Empty constructor for JSON
	}

	public Task(int id,String title, String description, int level,String category) {
		
		this.id=id;
		this.title=title;
		this.description=description ;
		this.level = level;
		this.category=category;
		this.isCompleted = false;
		 this.createdDate = System.currentTimeMillis();
	}
	
  // ========== Getters ==========
    
    /**
     * Get task ID
     * @return int task ID
     */
    public int getId() {
        return id;
    }
    
    /**
     * Get task title
     * @return String task title
     */
    public String getTitle() {
        return title;
    }
    
    /**
     * Get task description
     * @return String task description
     */
    public String getDescription() {
        return description;
    }
    
    /**
     * Check if task is completed
     * @return boolean true if completed, false otherwise
     */
    public boolean isCompleted() {
        return isCompleted;
    }
    
    /**
     * Get task category
     * @return String category (work/life/study)
     */
    public String getCategory() {
        return category;
    }
    
    /**
     * Get creation date timestamp
     * @return long timestamp in milliseconds
     */
    public long getCreatedDate() {
        return createdDate;
    }
  
    
    // ========== Setters ==========
    
    /**
     * Set task ID
     * @param id New task ID
     */
    public void setId(int id) {
        this.id = id;
    }
    
    /**
     * Set task title
     * @param title New task title
     */
    public void setTitle(String title) {
        this.title = title;
    }
    
    /**
     * Set task description
     * @param description New task description
     */
    public void setDescription(String description) {
        this.description = description;
    }
    
    /**
     * Set completion status
     * @param completed New completion status
     */
    public void setCompleted(boolean completed) {
        this.isCompleted = completed;
    }
    
    /**
     * Set task category
     * @param category New category (work/life/study)
     */
    public void setCategory(String category) {
        this.category = category;
    }
    
    /**
     * Set creation date
     * @param createdDate Creation timestamp
     */
    public void setCreatedDate(long createdDate) {
        this.createdDate = createdDate;
    }
    
  
    
    // ========== Utility Methods ==========
    
 
    
    /**
     * Get formatted creation date
     * @return String formatted date (yyyy-MM-dd)
     */
    public String getFormattedCreatedDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        return sdf.format(new Date(createdDate));
    }
    
 
    
    /**
     * Get category display name
     * @return String category display name in English
     */
    public String getCategoryDisplayName() {
        switch (category) {
            case Constants.CATEGORY_WORK:
                return "Work";
            case Constants.CATEGORY_LIFE:
                return "Life";
            case Constants.CATEGORY_STUDY:
                return "Study";
            default:
                return "Unknown";
        }
    }
    
    /**
     * Returns a string representation of this task
     * @return String representation for debugging
     */
    @Override
    public String toString() {
        return "Task [id=" + id + 
               ", title=" + title + 
               ", category=" + category + 
               ", completed=" + isCompleted + "]";
    }
	
	
	
	
	
	

}
