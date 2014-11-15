package de.mxro.file.Jre;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;

import de.mxro.file.FileItem;
import de.mxro.file.internal.NotExistentFileItem;

public class Java5FileItem implements FileItem {

    File file;

    @Override
    public boolean isDirectory() {
        return file.isDirectory();
    }

    @Override
    public boolean exists() {
        return file.exists();
    }

    @Override
    public FileItem getChild(final String childName) {
        for (final File child : file.listFiles()) {
            if (child.getName().equals(childName)) {
                return new Java5FileItem(child);
            }
        }
        return new NotExistentFileItem();
    }

    @Override
    public FileItem assertFolder(final String folderName) {
        if (!isDirectory()) {
            throw new RuntimeException("Cannot create folder for file [" + file + "].");
        }

        final File newFolder = getChildUnsafe(folderName);
        if (!newFolder.mkdir()) {
            throw new RuntimeException("Cannot create folder [" + newFolder + "].");
        }

        return new Java5FileItem(newFolder);
    }

    @Override
    public FileItem createFile(final String fileName) {
        if (!isDirectory()) {
            throw new RuntimeException("Cannot create file for file [" + file + "].");
        }

        final File newFile = getChildUnsafe(fileName);
        try {
            if (!newFile.createNewFile()) {
                throw new RuntimeException("Cannot create file [" + newFile + "].");
            }
        } catch (final IOException e) {
            throw new RuntimeException(e);
        }

        return new Java5FileItem(newFile);
    }

    @Override
    public FileItem deleteFile(final String fileName) {
        final File child = getChildUnsafe(fileName);

        if (!child.delete()) {
            throw new RuntimeException("File [" + child + "] could not be deleted.");
        }

        return this;
    }

    @Override
    public FileItem deleteFolder(final String folderName) {
        final File child = getChildUnsafe(folderName);

        if (!child.delete()) {
            throw new RuntimeException("Folder [" + child + "] could not be deleted.");
        }

        return this;
    }

    @Override
    public String getName() {

        return file.getName();
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

    public Java5FileItem(final File file) {
        super();
        this.file = file;
    }

    private final File getChildUnsafe(final String name) {
        return new File(file.getAbsolutePath() + "/" + name);
    }

}
