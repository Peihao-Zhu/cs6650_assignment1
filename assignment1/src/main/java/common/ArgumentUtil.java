package common;

import org.apache.commons.cli.*;

public class ArgumentUtil {
    public static Argument parse(String[] args) throws ParseException {
        // create Options object
        Options options = new Options();

        // add t option
        options.addOption(Const.NumThread, true, "maximum number of threads to run");
        options.addOption(Const.NumSkiers, true, "number of skier to generate lift rides");
        options.addOption(Const.NumLifts, true, "number of ski lifts");
        options.addOption(Const.NumRuns, true, "mean numbers of ski lifts each skier rides each day");
        options.addOption(Const.IP, true, "ip of the remote server");
        options.addOption(Const.PORT, true, "port the remote server listens to ");

        CommandLineParser parser = new DefaultParser();
        CommandLine cmd = parser.parse(options, args);
        Argument argument = new Argument();
        if(!cmd.hasOption(Const.NumThread) || !cmd.hasOption(Const.NumSkiers) || !cmd.hasOption(Const.NumLifts) || !cmd.hasOption(Const.NumRuns)
            || !cmd.hasOption(Const.IP) || !cmd.hasOption(Const.PORT)) {
            HelpFormatter formatter = new HelpFormatter();
            formatter.printHelp("Command Usage", options);
            System.exit(0);

        }
        argument.setNumThreads(Integer.valueOf(cmd.getOptionValue(Const.NumThread)));
        argument.setNumSkiers(Integer.valueOf(cmd.getOptionValue(Const.NumSkiers)));
        argument.setNumLifts(Integer.valueOf(cmd.getOptionValue(Const.NumLifts)));
        argument.setNumRuns(Integer.valueOf(cmd.getOptionValue(Const.NumRuns)));
        argument.setIp(cmd.getOptionValue(Const.IP));
        argument.setPort(Integer.valueOf(cmd.getOptionValue(Const.PORT)));
        return argument;
    }
}
