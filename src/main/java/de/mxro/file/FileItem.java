package de.mxro.file;

import java.util.Date;
import java.util.List;

/**
 * <p>
 * An interface for interacting with files and folder.
 * <p>
 * For usage examples the
 * <a href='https://github.com/mxro/file-api'>project page</a>
 * 
 * @author <a href="http://www.mxro.de">Max Rohde</a>
 *
 */
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
     * Returns the child element which matches the specified regular expression.
     * 
     * @param regex
     * @return
     */
    public FileItem find(String regex);

    /**
     * Returns all child elements which matche the specified regular expression.
     * 
     * @param regex
     * @return
     */
    public List<FileItem> findAll(String regex);

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
     * <p>
     * Deletes the folder with the specified name.
     * <p>
     * <b>Note:</b> This operation will recursively delete the contents of the
     * folder (ignoring links).
     * 
     * @param folderName
     *            The name of the folder to be deleted.
     * @return This FileTiem.
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
     * @return The name of this item.
     */
    public String getName();

    /**
     * Determines the extension (after the .) for this item.
     * 
     * @return The extension for this item.
     */
    public String getExtension();

    /**
     * Sets the visibility of this item.
     * 
     * @param isVisible
     *            <code>true</code> if the item should be visible,
     *            <code>false</code> if the item should be hidden.
     * @return This FileItem.
     */
    public FileItem setVisible(boolean isVisible);

    /**
     * Determines the visibility of this item.
     * 
     * @return <code>true</code> if this item is visible
     */
    public boolean getVisible();

    /**
     * Determines if this item is a link.
     * 
     * @return <code>true</code> if the item is a link.
     */
    public boolean getIsLink();

    /**
     * <p>
     * If destination is a folder, create a copy of this FileItem in the
     * specified folder.
     * <p>
     * If destination is a file, override specified file with the contents of
     * this FileItem.
     * <p>
     * If file exists, it will be overwritten.
     * 
     * @param destination
     * @return
     */
    public FileItem copyTo(FileItem destination);

    /**
     * Determines the list of files and folders which are children of this item.
     * 
     * @return List of files and folders being direct children of this item.
     */
    public List<FileItem> getChildren();

    /**
     * <p>
     * Determines the parten directory of this item.
     * <p>
     * If this item is a file, it will be the directory it is contained in.
     * <p>
     * If this item is a directory, it will be its parent directory.
     * 
     * @return
     */
    public FileItem getParent();

    /**
     * Determines when this item has last been modified.
     * 
     * @return The date when the file was last modified.
     */
    public Date lastModified();

    /**
     * Determines a hash for this item.
     * 
     * @return
     */
    public String hash();

    /**
     * Reads the contents of the file and converts them into a String based on
     * UTF-8 encoding.
     * 
     * @return The UTF-8 text content of this file
     */
    public String getText();

    /**
     * The full, absolute path of this file.
     * 
     * @return The path of this file.
     */
    public String getPath();

    /**
     * <p>
     * Replaces the current contents of the file with the specified text.
     * <p>
     * Uses UTF-8 Encoding
     * 
     * @param text
     *            The text to be used as file contents.
     * @return This FileItem.
     */
    public FileItem setText(String text);

    /**
     * Returns true if this is a folder which contains a file with the specified
     * name.
     * 
     * @param fileName
     *            The name of the child.
     * @return <code>true</code> If a child with the specified name exists.
     */
    public boolean contains(String fileName);

}
