# FlowLogProcessor

## Requirements
- The input files are plain text files
- The flow log file size can be up to 10 MB
- The lookup file can be up to 10,000 mappings
- The tags can map to more than one port, protocol combinations
- The matches are sensitive

### Input

#### flow logs (.log)
```text
<version: INT_32> <account-id: STRING> <interface-id: STRING> <srcaddr: STRING> <dstaddr: STRING> <srcport: INT_32> <dstport: INT_32> <protocol: INT_32> <packets: INT_64> <bytes: INT_64> <start: INT_64> <end: INT_64> <action: STRING> <log-status: STRING>
...
```

#### lookup table file (.csv)
```text
dstport,protocol,tag
<dstport> <protocol> <tag>
...
```

### Output

#### Tag Counts
```text
Tag,Count
<tag> <number>
...
```

#### Port/Protocol Combination Counts
```text
Port,Protocol,Count
<port> <protocol> <count>
...
```

## Assumptions
1. Assume input log file is `.txt` in `UTF-8` encoding
2. Assume input lookupTable file is in `UTF-8` encoding
3. Assume log only has 3 protocols: `ICMP(1)`, `TCP(6)`, and `UDP(17)`

## Build and Run
1. Make sure the local machine has java21 installed
2. (Optional) run `mvn clean install`
3. run `java -jar Assessment-1.0-SNAPSHOT-jar-with-dependencies.jar <flow_log_file>.txt <lookup_table_file>.csv`  
   example: `java -jar Assessment-1.0-SNAPSHOT-jar-with-dependencies.jar logs.txt lookup.csv`