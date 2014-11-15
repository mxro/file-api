package de.mxro.file.Jre;

import java.io.File;

import de.mxro.file.FileItem;
import de.mxro.file.internal.jre.Java5FileItem;

public final class FilesJre {

    public static final FileItem wrap(final File file) {
        return new Java5FileItem(file);
    }

}
