package com.illumio.processors.fileProcessors.report;

import com.illumio.model.Pair;
import com.illumio.model.Protocol;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.Map;

@SuppressWarnings("unchecked")
public class PortProtocolCombinationCountCSVFileProcessor extends AbstractCSVFileProcessor {
    @Override
    String getFileName() {
        return "port_protocol_combination";
    }

    @Override
    void writeOperation(BufferedWriter writer, Object content) throws IOException {
        Map<Pair<Integer, Integer>, Integer> portProtocolCombinationCountMap =
                (Map<Pair<Integer, Integer>, Integer>) content;

        writer.write("Port,Protocol,Count");
        writer.newLine();
        for (Map.Entry<Pair<Integer, Integer>, Integer> entry : portProtocolCombinationCountMap.entrySet()) {
            writer.write(entry.getKey().fst + "," + Protocol.getNameById(entry.getKey().snd) + "," + entry.getValue());
            writer.newLine();
        }
    }
}
