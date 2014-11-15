package de.mxro.file.internal;

import java.util.Date;
import java.util.List;

import de.mxro.file.FileItem;

public class NotExistentFileItem implements FileItem {

    final private static String ILLEGAL_ACCESS = "Cannot access property of non-existent file.";
    final private static String ILLEGAL_OPERATION = "Cannot perform operation on non-existent file";

    @Override
    public boolean isDirectory() {
        throw new RuntimeException(ILLEGAL_ACCESS);
    }

    @Override
    public boolean exists() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public FileItem getChild(final String childName) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public FileItem assertFolder(final String folderName) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public FileItem createFile(final String fileName) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public FileItem deleteFile(final String fileName) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public FileItem deleteFolder(final String folderName) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String getName() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String getExtension() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public FileItem setVisible(final boolean isVisible) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean getVisible() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public List<FileItem> getChildren() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Date lastModified() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String hash() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String getText() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public FileItem setText(final String text) {
        // TODO Auto-generated method stub
        return null;
    }

}
