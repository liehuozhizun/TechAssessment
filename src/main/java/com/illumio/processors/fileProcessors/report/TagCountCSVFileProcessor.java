package com.illumio.processors.fileProcessors.report;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.Map;

@SuppressWarnings("unchecked")
public class TagCountCSVFileProcessor extends AbstractCSVFileProcessor {
    @Override
    String getFileName() {
        return "tag_counts";
    }

    @Override
    void writeOperation(BufferedWriter writer, Object content) throws IOException {
        Map<String, Integer> tagCountMap = (Map<String, Integer>) content;
        writer.write("Tag,Count");
        writer.newLine();
        for (Map.Entry<String, Integer> entry : tagCountMap.entrySet()) {
            writer.write(entry.getKey() + "," + entry.getValue());
            writer.newLine();
        }
    }
}
