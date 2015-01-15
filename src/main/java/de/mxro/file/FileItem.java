package de.mxro.file;

import java.util.Date;
import java.util.List;

public interface FileItem {

    /**
     * 
     * @return <code>true</code> if this item is a directory.
     */
    public boolean isDirectory();

    /**
     * 
     * @return <code>true</code> if this item exists.
     */
    public boolean exists();

    /**
     * <p>
     * To retrieve a child file or folder with a specific name.
     * <p>
     * <b>Usage</b>
     * 
     * <pre>
     * root.get(&quot;folder&quot;).get(&quot;file.txt&quot;);
     * </pre>
     * 
     * @param childName
     * @return
     */
    public FileItem get(String childName);

    /**
     * <p>
     * Creates a folder with the specified name as child of this file item if
     * the folder doesn't exist.
     * 
     * @param folderName
     *            The name of the folder to be asserted.
     * @return The created or retrieved folder.
     */
    public FileItem assertFolder(String folderName);

    /**
     * <p>
     * Creates a file with the specified name as child of this file item if the
     * file doesn't exist.
     * 
     * @param fileName
     *            The name of the file to be asserted.
     * @return The created or retrieved file.
     */
    public FileItem assertFile(String fileName);

    /**
     * Creates a new file with the specified name as child of this item.
     * 
     * @param fileName
     *            The name of the file to be created.
     * @return The newly created file item.
     */
    public FileItem createFile(String fileName);

    /**
     * <p>
     * Deletes the file with the specified name.
     * <p>
     * Throws a runtime exception if the specified file doesn't exist.
     * 
     * @param fileName
     *            The name of the child of this item to be deleted.
     * @return This FileItem.
     */
    public FileItem deleteFile(String fileName);

    /**
     * 
     * @param folderName
     * @return
     */
    public FileItem deleteFolder(String folderName);

    /**
     * Removes all files and folders within this FileItem.
     * 
     * @return
     */
    public FileItem empty();

    /**
     * Returns the file name inclusive the extension.
     * 
     * @return
     */
    public String getName();

    public String getExtension();

    public FileItem setVisible(boolean isVisible);

    public boolean getVisible();

    public boolean getIsLink();

    public List<FileItem> getChildren();

    public Date lastModified();

    public String hash();

    /**
     * Returns the UTF-8 text content of this file.
     * 
     * @return
     */
    public String getText();

    public String getPath();

    public FileItem setText(String text);

    /**
     * Returns true if this is a folder which contains a file with the specified
     * name.
     * 
     * @param fileName
     * @return
     */
    public boolean contains(String fileName);

}
