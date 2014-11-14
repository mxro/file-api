package de.mxro.file;

import java.util.Date;
import java.util.List;

public interface FileItem {

    public boolean isDirectory();

    public boolean exists();

    public FileItem getChild(String childName);

    public FileItem assertFolder(String folderName);

    public FileItem createFile(String fileName);

    public FileItem deleteFile(String fileName);

    /**
     * Returns the file name inclusive the extension.
     * 
     * @return
     */
    public String getName();

    public FileItem setVisible(boolean isVisible);

    public FileItem create();

    public List<FileItem> getChildren();

    public Date lastModified();

    public String hash();

    /**
     * Returns the UTF-8 text content of this file.
     * 
     * @return
     */
    public String getText();

    public FileItem setText(String text);

}
