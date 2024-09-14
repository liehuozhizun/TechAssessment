package com.illumio.processors.logProcessors;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.ExecutionException;

public interface LogProcessor {
    /**
     * Process the log file with take lookupTable file, generating count files in current folder
     * @param logFile the File containing Flow Log
     * @param lookupTableFile The File containing the tag mapping info
     * @throws IOException read from input files or write to count file may throw exception when failed to do so
     */
    void process(File logFile, File lookupTableFile) throws IOException, ExecutionException, InterruptedException;
}
