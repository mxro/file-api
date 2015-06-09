package de.mxro.file.internal.jre;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.nio.channels.FileChannel;
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
    public FileItem find(final String pattern) {
        for (final File child : file.listFiles()) {
            if (child.getName().matches(pattern)) {
                return new Java5FileItem(child);
            }
        }
        return new NotExistentFileItem();
    }

    @Override
    public List<FileItem> findAll(final String pattern) {
        final List<FileItem> list = new ArrayList<FileItem>(0);

        for (final File child : file.listFiles()) {
            if (child.getName().matches(pattern)) {
                list.add(new Java5FileItem(child));
            }
        }

        return list;
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

        if (!getIsLink()) {
            new Java5FileItem(child).empty();
        }

        if (!child.delete()) {

            throw new RuntimeException("Folder [" + child + "] could not be deleted.");
        }

        return this;
    }

    @Override
    public FileItem empty() {

        for (final FileItem child : this.getChildren()) {
            if (child.isDirectory()) {
                deleteFolder(child.getName());
            } else {
                deleteFile(child.getName());
            }

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

    @Override
    public boolean getIsLink() {

        try {
            return isSymlink(file);
        } catch (final IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * based on method from Apache Commons IO under Apache 2 license:
     * http://commons
     * .apache.org/proper/commons-io/xref/org/apache/commons/io/FileUtils.html
     * 
     * Determines whether the specified file is a Symbolic Link rather than an
     * actual file.
     * <p>
     * Will not return true if there is a Symbolic Link anywhere in the path,
     * only if the specific file is.
     * <p>
     * <b>Note:</b> the current implementation always returns {@code false} if
     * the system is detected as Windows using
     * {@link FilenameUtils#isSystemWindows()}
     * <p>
     * For code that runs on Java 1.7 or later, use the following method
     * instead: <br>
     * {@code boolean java.nio.file.Files.isSymbolicLink(Path path)}
     * 
     * @param file
     *            the file to check
     * @return true if the file is a Symbolic Link
     * @throws IOException
     *             if an IO error occurs while checking the file
     * @since 2.0
     */
    private final static boolean isSymlink(final File file) throws IOException {
        if (file == null) {
            throw new NullPointerException("File must not be null");
        }
        if (System.getProperty("os.name").startsWith("Windows")) {
            return false;
        }
        File fileInCanonicalDir = null;
        if (file.getParent() == null) {
            fileInCanonicalDir = file;
        } else {
            final File canonicalDir = file.getParentFile().getCanonicalFile();
            fileInCanonicalDir = new File(canonicalDir, file.getName());
        }

        if (fileInCanonicalDir.getCanonicalFile().equals(fileInCanonicalDir.getAbsoluteFile())) {
            return false;
        } else {
            return true;
        }
    }

    @Override
    public FileItem copyTo(final FileItem destination) {
        if (this.isDirectory()) {
            throw new IllegalStateException("Not supported for directories yet.");
        }
        FileItem destFile;
        if (destination.isDirectory()) {
            destFile = destination.assertFile(this.getName());
        } else {
            destFile = destination;
        }

        final File destFileRaw = ((Java5FileItem) destFile).file;

        try {

            if (!destFile.exists()) {
                destFileRaw.createNewFile();
            }

            doCopyFile(this.file, destFileRaw, true);

        } catch (final IOException e) {
            throw new RuntimeException(e);
        }
        return destFile;
    }

    private static final long FILE_COPY_BUFFER_SIZE = 1024 * 1000 * 30;

    /**
     * based on method from Apache Commons IO under Apache 2 license:
     * http://commons
     * .apache.org/proper/commons-io/xref/org/apache/commons/io/FileUtils.html
     * 
     * Internal copy file method. This caches the original file length, and
     * throws an IOException if the output file length is different from the
     * current input file length. So it may fail if the file changes size. It
     * may also fail with "IllegalArgumentException: Negative size" if the input
     * file is truncated part way through copying the data and the new file size
     * is less than the current position.
     *
     * @param srcFile
     *            the validated source file, must not be {@code null}
     * @param destFile
     *            the validated destination file, must not be {@code null}
     * @param preserveFileDate
     *            whether to preserve the file date
     * @throws IOException
     *             if an error occurs
     * @throws IOException
     *             if the output file length is not the same as the input file
     *             length after the copy completes
     * @throws IllegalArgumentException
     *             "Negative size" if the file is truncated so that the size is
     *             less than the position
     */
    private static void doCopyFile(final File srcFile, final File destFile, final boolean preserveFileDate)
            throws IOException {
        if (destFile.exists() && destFile.isDirectory()) {
            throw new IOException("Destination '" + destFile + "' exists but is a directory");
        }

        FileInputStream fis = null;
        FileOutputStream fos = null;
        FileChannel input = null;
        FileChannel output = null;
        try {
            fis = new FileInputStream(srcFile);
            fos = new FileOutputStream(destFile);
            input = fis.getChannel();
            output = fos.getChannel();
            final long size = input.size(); // TODO See IO-386
            long pos = 0;
            long count = 0;
            while (pos < size) {
                final long remain = size - pos;
                count = remain > FILE_COPY_BUFFER_SIZE ? FILE_COPY_BUFFER_SIZE : remain;
                final long bytesCopied = output.transferFrom(input, pos, count);
                if (bytesCopied == 0) { // IO-385 - can happen if file is
                    // truncated after caching the size
                    break; // ensure we don't loop forever
                }
                pos += bytesCopied;
            }
        } finally {
            output.close();
            fos.close();
            input.close();
            fis.close();

        }

        final long srcLen = srcFile.length(); // TODO See IO-386
        final long dstLen = destFile.length(); // TODO See IO-386
        if (srcLen != dstLen) {
            throw new IOException("Failed to copy full contents from '" + srcFile + "' to '" + destFile
                    + "' Expected length: " + srcLen + " Actual: " + dstLen);
        }
        if (preserveFileDate) {
            destFile.setLastModified(srcFile.lastModified());
        }
    }

}
