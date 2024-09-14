package com.illumio.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

/**
 * Protocol Enum
 * ATTN: For simplicity, only support ICMP, TCP, UDP
 */
@Getter
@AllArgsConstructor
public enum Protocol {;

    private static final Map<Integer, String> protocolIdNameMap = new HashMap<>();
    private static final Map<String, Integer> protocolNameIdMap = new HashMap<>();

    static {
        protocolIdNameMap.put(1, "icmp");
        protocolIdNameMap.put(6, "tcp");
        protocolIdNameMap.put(17, "udp");

        protocolNameIdMap.put("icmp", 1);
        protocolNameIdMap.put("tcp", 6);
        protocolNameIdMap.put("udp", 17);
    }

    public static String getNameById(int id) {
        return protocolIdNameMap.get(id);
    }

    public static int getIdByName(String name) {
        return protocolNameIdMap.get(name);
    }
}
