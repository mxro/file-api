package de.mxro.file.Jre;

import java.io.InputStream;

import de.mxro.file.FileItem;

public class JreFileItem implements FileItem {

    public InputStream getInputStream() {
        return null;
    }

    @Override
    public boolean isDirectory() {
        // TODO Auto-generated method stub
        return false;
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
    public void setVisible(final boolean isVisible) {
        // TODO Auto-generated method stub

    }

    @Override
    public void create() {
        // TODO Auto-generated method stub

    }

}
