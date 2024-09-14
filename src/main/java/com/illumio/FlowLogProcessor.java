package com.illumio;

import com.illumio.processors.fileProcessors.FileProcessor;
import com.illumio.processors.fileProcessors.DefaultFileProcessor;
import com.illumio.processors.logProcessors.LogProcessor;
import com.illumio.processors.logProcessors.LogProcessorV2;

public class FlowLogProcessor
{
    public static void main( String[] args )
    {
        if (args.length < 2) {
            System.err.println("Usage: java FlowLogProcessor <flow_log_file>.* <lookup_table_file>.csv");
            System.exit(1);
        }

        // Read two input file path
        String logFilePath =  args[0];
        String lookupTableFilePath = args[1];

        // Process the logs
        FileProcessor fileProcessor = new DefaultFileProcessor();
        LogProcessor logProcessor = new LogProcessorV2();
        try {
            logProcessor.process(fileProcessor.readFile(logFilePath), fileProcessor.readFile(lookupTableFilePath));
            System.out.println("Processing completed successfully.");
        } catch (Exception e) {
            System.err.println("Failed to process file due to: " + e.getMessage());
            System.exit(1);
        }
    }
}
