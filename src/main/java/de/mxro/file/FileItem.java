package de.mxro.file;

public interface FileItem {

    public boolean isDirectory();

    public boolean exists();

    public FileItem getChild(String childName);

}
