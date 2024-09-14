package com.illumio.processors.fileProcessors.report;

import com.illumio.processors.fileProcessors.FileProcessor;

import java.io.File;
import java.io.IOException;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.HashMap;

public abstract class AbstractCSVFileProcessor implements FileProcessor {

    @Override
    public File readFile(String path) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean writeFile(String path, Object content) {
        File file = getFile(content);

        try {
            if (!file.createNewFile()) {
                throw new IOException("Could not create file " + file.getAbsolutePath());
            }

            // Use BufferedWriter to write result into the file
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
                writeOperation(writer, content);
            }
            return true;
        } catch (IOException e) {
            System.err.println("Could not write file " + file.getAbsolutePath());
        }
        return false;
    }

    private File getFile(Object content) {
        if (!(content instanceof HashMap)) {
            throw new IllegalArgumentException("Content must be a HashMap");
        }

        // Create a new file for storing count result
        // If a file already exists, append an increasing number to the name
        String FILE_EXTENSION = ".csv";
        File file = new File(getFileName() + FILE_EXTENSION);
        int num = 1;
        while (file.exists()) {
            file = new File(getFileName() + "_" + num + FILE_EXTENSION);
            num++;
        }
        return file;
    }

    abstract String getFileName();

    abstract void writeOperation(BufferedWriter writer, Object content) throws IOException;
}
