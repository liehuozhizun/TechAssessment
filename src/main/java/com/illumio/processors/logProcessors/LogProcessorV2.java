package com.illumio.processors.logProcessors;

import com.illumio.model.FlowLogV2Model;
import com.illumio.model.Pair;
import com.illumio.model.Protocol;
import com.illumio.processors.fileProcessors.report.PortProtocolCombinationCountCSVFileProcessor;
import com.illumio.processors.fileProcessors.report.TagCountCSVFileProcessor;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

@SuppressWarnings("unchecked")
public class LogProcessorV2 implements LogProcessor {

    private final Map<String, Integer> tagCountMap = new HashMap<>();
    private final Map<Pair<Integer, Integer>, Integer> portProtocolCombinationCountMap = new HashMap<>();
    private final Map<Pair<Integer, Integer>, String> lookupMap = new HashMap<>();

    @Override
    public void process(File logFile, File lookupTableFile) throws IOException, ExecutionException, InterruptedException {
        preProcessTagMapping(lookupTableFile);

        if (Objects.isNull(logFile)) {
            throw new NullPointerException("Input logFile file cannot be null");
        }

        try (LineIterator lineIterator = FileUtils.lineIterator(logFile, "UTF-8")) {
            lineIterator.forEachRemaining(line -> processFlowLog(FlowLogV2Model.parseFromString(line)));
        }

        generateReport();
    }

    /*
     * Pre-processing the lookup table tag mapping
     */
    private void preProcessTagMapping(File lookupTable) throws IOException {
        if (Objects.isNull(lookupTable)) {
            throw new NullPointerException("Input lookupTable file cannot be null");
        }

        try (LineIterator lineIterator = FileUtils.lineIterator(lookupTable, "UTF-8")) {
            lineIterator.nextLine();
            lineIterator.forEachRemaining(line -> {
                String[] lookup = line.split(",");
                lookupMap.put(Pair.of(Integer.parseInt(lookup[0]), Protocol.getIdByName(lookup[1])), lookup[2]);
            });
        }
    }

    /*
     * Count the result by given log record
     */
    private void processFlowLog(FlowLogV2Model log) {
        // Increase count number for matching tag
        String tag = lookupMap.getOrDefault(Pair.of(log.getDstPort(), log.getProtocol()), null);
        tagCountMap.put(Objects.isNull(tag) ? "Untagged" : tag, tagCountMap.getOrDefault(tag, 0) + 1);

        // Increase count number for matching port/protocol combination
        Pair<Integer, Integer> combination = Pair.of(log.getDstPort(), log.getProtocol());
        int count = portProtocolCombinationCountMap.getOrDefault(combination, 0) + 1;
        portProtocolCombinationCountMap.put(combination, count);
    }

    /*
     * Asynchronously generate count files based on the processed value
     */
    private void generateReport() throws ExecutionException, InterruptedException {
        String currentDirPath = System.getProperty("user.dir");

        // Define generation job for each desired count files
        Future<Boolean> tagCountFuture = CompletableFuture
                .supplyAsync(() -> new TagCountCSVFileProcessor().writeFile(currentDirPath, tagCountMap));
        Future<Boolean> portProtocolCombinationCountFuture = CompletableFuture
                .supplyAsync(() -> new PortProtocolCombinationCountCSVFileProcessor()
                        .writeFile(currentDirPath, portProtocolCombinationCountMap));

        // Print out count file generation status
        System.out.println("> Tag Count Report Completed:                       " +
                (tagCountFuture.get() ? "success" : "fail"));
        System.out.println("> Port Protocol Combination Count Report Completed: " +
                (portProtocolCombinationCountFuture.get() ? "success" : "fail"));
    }
}
