package com.walterjwhite.linux.builder.impl.service.packagemanagement;

import com.walterjwhite.linux.builder.api.model.configuration.BuildConfiguration;
import com.walterjwhite.shell.api.service.ShellExecutionService;
import com.walterjwhite.shell.impl.service.ShellCommandBuilder;
import javax.inject.Inject;

public class FuntooEmergePackageManagementService extends EmergePackageManagementService {
  @Inject
  public FuntooEmergePackageManagementService(
      ShellExecutionService shellExecutionService,
      ShellCommandBuilder shellCommandBuilder,
      BuildConfiguration buildConfiguration) {
    super(shellExecutionService, shellCommandBuilder, buildConfiguration);
  }

  @Override
  public void update() throws Exception {
    run(
        "ego sync",
        "emerge --quiet --quiet-build y --nospinner --color n --update --newuse --deep --with-bdeps=y @world",
        "emerge --newuse -uD --quiet --quiet-build y --nospinner --color n @world",
        "emerge --quiet --quiet-build y --nospinner --color n --depclean",
        "emerge --quiet --quiet-build y --nospinner --color n @preserved-rebuild"
        /*"eclean-dist --deep"*/ );
    // run eclean-dist outside and combine both workstation and router world files
  }

  public boolean isInstalled(String packageName) throws Exception {
    return run("equery l " + packageName).getReturnCode() == 0;
  }
}
