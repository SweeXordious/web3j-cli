/*
 * Copyright 2019 Web3 Labs Ltd.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 */
package org.web3j.console.project;

import java.util.ArrayList;
import java.util.List;

import picocli.CommandLine;

import static org.web3j.console.project.InteractiveOptions.*;
import static org.web3j.utils.Collection.tail;

public class ProjectImporter extends ProjectCreator {
    public static final String COMMAND_IMPORT = "import";

    public ProjectImporter(final String root, final String packageName, final String projectName) {
        super(root, packageName, projectName);
    }

    public static void main(String[] args) {
        final String projectName;
        final List<String> stringOptions = new ArrayList<>();
        if (args.length > 0 && args[0].equals(COMMAND_IMPORT)) {
            args = tail(args);
            if (args.length == 0) {
                stringOptions.add("-n");
                projectName = getProjectName();
                stringOptions.add(projectName);
                stringOptions.add("-p");
                stringOptions.add(getPackageName());
                stringOptions.add("-s");
                stringOptions.add(getSolidityProjectPath());
                getProjectDestination(projectName)
                        .ifPresent(
                                projectDest -> {
                                    stringOptions.add("-o");
                                    stringOptions.add(projectDest);
                                });
                if (userWantsTests()) {
                    stringOptions.add("-t");
                }

                args = stringOptions.toArray(new String[0]);
            }
        }
        CommandLine.run(new ProjectImporterCLIRunner(), args);
    }
}
