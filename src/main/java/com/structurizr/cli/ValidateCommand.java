package com.structurizr.cli;

import com.structurizr.Workspace;
import com.structurizr.util.WorkspaceUtils;
import org.apache.commons.cli.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

class ValidateCommand extends AbstractCommand {

    private static final Log log = LogFactory.getLog(ValidateCommand.class);

    ValidateCommand() {
    }

    void run(String... args) throws Exception {
        Options options = new Options();

        Option option = new Option("w", "workspace", true, "Path or URL to the workspace JSON file/DSL file(s)");
        option.setRequired(true);
        options.addOption(option);

        CommandLineParser commandLineParser = new DefaultParser();
        HelpFormatter formatter = new HelpFormatter();

        String workspacePathAsString = null;

        try {
            CommandLine cmd = commandLineParser.parse(options, args);

            workspacePathAsString = cmd.getOptionValue("workspace");
        } catch (ParseException e) {
            log.error(e.getMessage());
            formatter.printHelp("validate", options);

            System.exit(1);
        }

        log.info("Validating workspace at " + workspacePathAsString);

        Workspace workspace = loadWorkspace(workspacePathAsString);
        WorkspaceUtils.fromJson(WorkspaceUtils.toJson(workspace, false)); // this will trigger the deserialization validation

        log.info(" - validated");
        log.info(" - finished");
    }

}