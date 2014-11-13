package de.mxro.file.Jre;

import java.io.InputStream;

import de.mxro.file.FileItem;

public class JreFiles {

    public static InputStream getInputStream(final FileItem item) {
        return ((JreFileItem) item).getInputStream();
    }

}
