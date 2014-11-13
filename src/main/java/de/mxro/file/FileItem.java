package de.mxro.file;

import java.util.Date;
import java.util.List;

public interface FileItem {

    public boolean isDirectory();

    public boolean exists();

    public FileItem getChild(String childName);

    public FileItem assertFolder(String folderName);

    /**
     * Returns the file name inclusive the extension.
     * 
     * @return
     */
    public String getName();

    public void setVisible(boolean isVisible);

    public void create();

    public List<FileItem> getChildren();

    public Date lastModified();

}
