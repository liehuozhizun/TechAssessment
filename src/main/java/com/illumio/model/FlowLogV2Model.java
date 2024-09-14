package com.illumio.model;

import lombok.Builder;
import lombok.Data;

import java.util.Objects;

@Data
@Builder
public class FlowLogV2Model {
    private int version;
    private String accountId;
    private String interfaceId;
    private String srcAddr;
    private String dstAddr;
    private int srcPort;
    private int dstPort;
    private int protocol;
    private long packets;
    private long bytes;
    private long start;
    private long end;
    private String action;
    private String logStatus;

    public static FlowLogV2Model parseFromString(String logLine, String delimiter) {
        if (Objects.isNull(logLine) || Objects.isNull(delimiter)) {
            throw new NullPointerException("input string logLine or delimiter cannot be null");
        }

        String[] fields = logLine.split(delimiter);
        if (fields.length != FlowLogV2Model.class.getDeclaredFields().length) {
            throw new IllegalArgumentException("input logLine is not valid to be parsed");
        }

        return FlowLogV2Model.builder()
                .version(Integer.parseInt(fields[0]))
                .accountId(fields[1])
                .interfaceId(fields[2])
                .srcAddr(fields[3])
                .dstAddr(fields[4])
                .srcPort(Integer.parseInt(fields[5]))
                .dstPort(Integer.parseInt(fields[6]))
                .protocol(Integer.parseInt(fields[7]))
                .packets(Long.parseLong(fields[8]))
                .bytes(Long.parseLong(fields[9]))
                .start(Long.parseLong(fields[10]))
                .end(Long.parseLong(fields[11]))
                .action(fields[12])
                .logStatus(fields[13])
                .build();
    }

    public static FlowLogV2Model parseFromString(String logLine) {
        return parseFromString(logLine, " ");
    }
}
