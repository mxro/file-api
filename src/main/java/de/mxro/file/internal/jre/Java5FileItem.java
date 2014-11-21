package de.mxro.file.internal.jre;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import de.mxro.file.FileItem;
import de.mxro.file.internal.NotExistentFileItem;
import de.mxro.process.Spawn;

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
    public FileItem get(final String childName) {
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

        if (newFolder.exists()) {
            return new Java5FileItem(newFolder);
        }

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

        if (!child.exists()) {
            throw new IllegalArgumentException("Folder [" + child + "] cannot be deleted since it doesn't exist.");
        }

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

        final int idx = file.getName().lastIndexOf(".");
        if (idx < 0) {
            return "";
        }

        return file.getName().substring(idx + 1);
    }

    @Override
    public FileItem setVisible(final boolean isVisible) {

        if (getVisible() == isVisible) {
            return this;
        }

        if (!getVisible() && isVisible) {
            throw new RuntimeException("Making files viisble not supported yet.");
        }

        if (!System.getProperty("os.name").startsWith("Windows")) {
            if (!getName().startsWith(".")) {
                throw new RuntimeException(
                        "Cannot make file invisible on UNIX with a name that doesn't start with '.' for file [" + file
                                + "]");
            } else {
                return this;
            }
        }

        Spawn.runCommand("attrib -s -h -r " + getName(), file.getParentFile());

        return this;
    }

    @Override
    public boolean getVisible() {
        return !file.isHidden();
    }

    @Override
    public List<FileItem> getChildren() {
        final File[] listFiles = file.listFiles();
        final List<FileItem> res = new ArrayList<FileItem>(listFiles.length);
        for (final File f : listFiles) {
            res.add(new Java5FileItem(f));
        }
        return res;
    }

    @Override
    public Date lastModified() {
        return new Date(file.lastModified());
    }

    @Override
    public String hash() {
        try {
            return Md5.getMD5Checksum(file);
        } catch (final Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String getText() {
        try {
            InputStream in = null;
            try {
                in = new FileInputStream(file);

                final byte[] b = new byte[(int) file.length()];
                final int len = b.length;
                int total = 0;

                while (total < len) {
                    final int result = in.read(b, total, len - total);
                    if (result == -1) {
                        break;
                    }
                    total += result;
                }

                return new String(b, "UTF-8");
            } finally {
                if (in != null) {
                    in.close();
                }
            }

        } catch (final Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public FileItem setText(final String text) {
        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(new FileWriter(file));
            writer.write(text);
        } catch (final IOException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                if (writer != null) {
                    writer.close();
                }
            } catch (final IOException e) {
                throw new RuntimeException(e);
            }
        }
        return this;
    }

    @Override
    public boolean contains(final String fileName) {
        if (!isDirectory()) {
            throw new RuntimeException(".contains can only be used on folders and not [" + file + "].");
        }

        return getChildUnsafe(fileName).exists();
    }

    public Java5FileItem(final File file) {
        super();
        this.file = file;
    }

    /**
     * Might return a child that does not exist.
     * 
     * @param name
     * @return
     */
    private final File getChildUnsafe(final String name) {
        return new File(file.getAbsolutePath() + "/" + name);
    }

    @Override
    public String toString() {
        return file.toString();
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((file == null) ? 0 : file.hashCode());
        return result;
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Java5FileItem other = (Java5FileItem) obj;
        if (file == null) {
            if (other.file != null) {
                return false;
            }
        } else if (!file.equals(other.file)) {
            return false;
        }
        return true;
    }

    @Override
    public String getPath() {
        return file.getAbsolutePath();
    }

    @Override
    public FileItem assertFile(final String fileName) {
        final FileItem item = get(fileName);
        if (item.exists()) {
            return item;
        }

        return createFile(fileName);
    }

}
