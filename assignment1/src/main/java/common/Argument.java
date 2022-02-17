package common;


import lombok.Data;


@Data
public class Argument {
    private int numThreads;
    private int numSkiers;
    private int numLifts;
    private int numRuns;
    private String ip;
    private int port;
}
