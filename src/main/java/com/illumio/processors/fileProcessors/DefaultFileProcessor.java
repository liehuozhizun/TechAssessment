package com.illumio.processors.fileProcessors;

import java.io.File;
import java.io.FileNotFoundException;

public class DefaultFileProcessor implements FileProcessor {

    @Override
    public File readFile(String path) throws FileNotFoundException {
        File file = new File(path);
        if (!file.exists()) {
            throw new FileNotFoundException();
        }
        if (!file.isFile()) {
            throw new RuntimeException("Selected file is not a file");
        }
        return file;
    }

    @Override
    public boolean writeFile(String path, Object content) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
