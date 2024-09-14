package com.illumio.processors.fileProcessors;

import java.io.File;
import java.io.FileNotFoundException;

public interface FileProcessor {
    /**
     * Create a {@link File} object to hold the file to be read from
     * @param path the file path pointing to the file location
     * @return File representation of the given file path
     * @throws FileNotFoundException if no such file exists
     */
    File readFile(String path) throws FileNotFoundException;

    /**
     * Write the content into the file given the target folder
     * @param path the folder where the file will be created and written into
     * @param content the content ready to be written into target file
     * @return whether the write operation succeeds
     */
    boolean writeFile(String path, Object content);
}
