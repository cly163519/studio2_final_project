package model;

public class Task {
    private String title;
    private boolean completed;

    public Task(String title) {
        this.title = title;
        this.completed = false;
    }

    public String getTitle() {
        return title;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    @Override
    public String toString() {
        return (completed ? "[✔] " : "[ ] ") + title;
    }
}


// LAURA'S TO-DO LIST:


/*

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

/* DELETE THIS LINE TO RESTORE CODE [START]

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

/* DELETE THIS LINE TO RESTORE CODE [START] */

  // ========== Getters ==========
 

    /**
     * Get task ID
     * @return int task ID
     */

	/* DELETE THIS LINE TO RESTORE CODE [START]

    public int getId() {
        return id;
    }
    
	DELETE THIS LINE TO RESTORE CODE [END] */    
    
    /**
     * Get task title
     * @return String task title
     */

	/* DELETE THIS LINE TO RESTORE CODE [START]

    public String getTitle() {
        return title;
    }
    
    DELETE THIS LINE TO RESTORE CODE [END] */  
    
    /**
     * Get task description
     * @return String task description
     */

	/* DELETE THIS LINE TO RESTORE CODE [START]

    public String getDescription() {
        return description;
    }
    
    DELETE THIS LINE TO RESTORE CODE [END] */  

    /**
     * Check if task is completed
     * @return boolean true if completed, false otherwise
     */

	/* DELETE THIS LINE TO RESTORE CODE [START]

    public boolean isCompleted() {
        return isCompleted;
    }
    
    DELETE THIS LINE TO RESTORE CODE [END] */
    
    /**
     * Get task category
     * @return String category (work/life/study)
     */

	/* DELETE THIS LINE TO RESTORE CODE [START]

    public String getCategory() {
        return category;
    }
    
    /**
     * Get creation date timestamp
     * @return long timestamp in milliseconds
     */

	/* DELETE THIS LINE TO RESTORE CODE [START]

    public long getCreatedDate() {
        return createdDate;
    }
  
  	 DELETE THIS LINE TO RESTORE CODE [END] */
    
    // ========== Setters ==========
    
    /**
     * Set task ID
     * @param id New task ID
     */

	/* DELETE THIS LINE TO RESTORE CODE [START]

    public void setId(int id) {
        this.id = id;
    }
    
    DELETE THIS LINE TO RESTORE CODE [END] */
    
    /**
     * Set task title
     * @param title New task title
     */

/* DELETE THIS LINE TO RESTORE CODE [START]

    public void setTitle(String title) {
        this.title = title;
    }
    
     DELETE THIS LINE TO RESTORE CODE [END] */
    
    /**
     * Set task description
     * @param description New task description
     */

	/* DELETE THIS LINE TO RESTORE CODE [START]

    public void setDescription(String description) {
        this.description = description;
    }
    
    DELETE THIS LINE TO RESTORE CODE [END] */
    
    /**
     * Set completion status
     * @param completed New completion status
     */

	/* DELETE THIS LINE TO RESTORE CODE [START]

    public void setCompleted(boolean completed) {
        this.isCompleted = completed;
    }
    
     DELETE THIS LINE TO RESTORE CODE [END] */
    
    /**
     * Set task category
     * @param category New category (work/life/study)
     */

/* DELETE THIS LINE TO RESTORE CODE [START]

    public void setCategory(String category) {
        this.category = category;
    }
    
     DELETE THIS LINE TO RESTORE CODE [END] */
    
    /**
     * Set creation date
     * @param createdDate Creation timestamp
     */

	/* DELETE THIS LINE TO RESTORE CODE [START]

    public void setCreatedDate(long createdDate) {
        this.createdDate = createdDate;
    }
    
    DELETE THIS LINE TO RESTORE CODE [END] */
    
    // ========== Utility Methods ==========
    
 
    
    /**
     * Get formatted creation date
     * @return String formatted date (yyyy-MM-dd)
     */

	/* DELETE THIS LINE TO RESTORE CODE [START]

    public String getFormattedCreatedDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        return sdf.format(new Date(createdDate));
    }
    
 	DELETE THIS LINE TO RESTORE CODE [END] */
    
    /**
     * Get category display name
     * @return String category display name in English
     */

	/* DELETE THIS LINE TO RESTORE CODE [START]
	
    public String getCategoryDisplayName() {
        switch (category) {
            case Constants.CATEGORY_EASY_WINS:
                return "Easy Wins";
            case Constants.CATEGORY_NUTRITION:
                return "Nutrition";
            case Constants.CATEGORY_MOVEMENT:
                return "Movement";
            case Constants.CATEGORY_CONNECTION:
                return "Connection";
            case Constants.CATEGORY_PRODUCTIVITY:
                return "Productivity";
            default:
                return "Unknown";
        }
    }
    
    DELETE THIS LINE TO RESTORE CODE [END] */
    
    /**
     * Returns a string representation of this task
     * @return String representation for debugging
     */
	
	/* DELETE THIS LINE TO RESTORE CODE [START]
	
    @Override
    public String toString() {
        return "Task [id=" + id + 
               ", title=" + title + 
               ", category=" + category + 
               ", completed=" + isCompleted + "]";
    }
	
	
}

	DELETE THIS LINE TO RESTORE CODE [END] */

