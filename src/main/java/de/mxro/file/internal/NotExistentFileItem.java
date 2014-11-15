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
        return false;
    }

    @Override
    public FileItem getChild(final String childName) {
        throw new RuntimeException(ILLEGAL_ACCESS);
    }

    @Override
    public FileItem assertFolder(final String folderName) {
        throw new RuntimeException(ILLEGAL_OPERATION);
    }

    @Override
    public FileItem createFile(final String fileName) {
        throw new RuntimeException(ILLEGAL_OPERATION);
    }

    @Override
    public FileItem deleteFile(final String fileName) {
        throw new RuntimeException(ILLEGAL_OPERATION);
    }

    @Override
    public FileItem deleteFolder(final String folderName) {
        throw new RuntimeException(ILLEGAL_OPERATION);
    }

    @Override
    public String getName() {
        throw new RuntimeException(ILLEGAL_ACCESS);
    }

    @Override
    public String getExtension() {
        throw new RuntimeException(ILLEGAL_ACCESS);
    }

    @Override
    public FileItem setVisible(final boolean isVisible) {
        throw new RuntimeException(ILLEGAL_OPERATION);
    }

    @Override
    public boolean getVisible() {
        throw new RuntimeException(ILLEGAL_ACCESS);
    }

    @Override
    public List<FileItem> getChildren() {
        throw new RuntimeException(ILLEGAL_ACCESS);
    }

    @Override
    public Date lastModified() {
        throw new RuntimeException(ILLEGAL_ACCESS);
    }

    @Override
    public String hash() {
        throw new RuntimeException(ILLEGAL_ACCESS);
    }

    @Override
    public String getText() {
        throw new RuntimeException(ILLEGAL_ACCESS);
    }

    @Override
    public FileItem setText(final String text) {
        throw new RuntimeException(ILLEGAL_OPERATION);
    }

    @Override
    public boolean contains(final String fileName) {
        throw new RuntimeException(ILLEGAL_ACCESS);
    }

}
