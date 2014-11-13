package de.mxro.file;

import java.util.List;

public interface FileItem {

    public boolean isDirectory();

    public boolean exists();

    public FileItem getChild(String childName);

    public FileItem assertFolder(String folderName);

    public void setVisible(boolean isVisible);

    public void create();

    public List<FileItem> getChildren();

}
