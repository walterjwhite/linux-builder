package com.walterjwhite.linux.builder.impl.service.scm;

import com.walterjwhite.linux.builder.api.model.SCMTag;
import com.walterjwhite.linux.builder.api.model.configuration.BuildConfiguration;
import com.walterjwhite.linux.builder.api.service.SCMService;
import com.walterjwhite.shell.api.service.ShellExecutionService;
import com.walterjwhite.shell.impl.service.ShellCommandBuilder;
import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import javax.inject.Inject;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;

// TODO: this API supports diffing, that would be useful for performing delta builds (only doing *
// what changed).
// https://github.com/centic9/jgit-cookbook/blob/master/src/main/java/org/dstadler/jgit/porcelain/CloneRemoteRepository.java
public class GitSCMService implements SCMService {
  protected final ShellCommandBuilder shellCommandBuilder;

  @Inject
  public GitSCMService(
      ShellCommandBuilder shellCommandBuilder, ShellExecutionService shellExecutionService) {
    super();
    this.shellCommandBuilder = shellCommandBuilder;
    this.shellExecutionService = shellExecutionService;
  }

  @Override
  public void checkout(BuildConfiguration buildConfiguration) throws Exception {
    checkoutExternally(buildConfiguration);
  }

  protected final ShellExecutionService shellExecutionService;

  protected void checkoutExternally(BuildConfiguration buildConfiguration) throws Exception {
    shellExecutionService.run(
        shellCommandBuilder
            .build()
            .withCommandLine(
                "git clone -q "
                    + buildConfiguration.getRepositoryUri()
                    + " -b "
                    + buildConfiguration.getTag()
                    + " "
                    + buildConfiguration.getLocalWorkspace()));
  }

  protected void checkoutInternally(BuildConfiguration buildConfiguration) {
    /*
            FileRepositoryBuilder fileRepositoryBuilder = new FileRepositoryBuilder();
            Repository repository = fileRepositoryBuilder.build();
            try (Git git = new Git(repository)) {
    git.checkout().
            }
            */

    // the permissions are incorrect
    /*
    try (Git result = Git.cloneRepository()
            .setURI(buildConfiguration.getRepositoryUri())
            .setDirectory(localRepositoryFile)
            .setBranch(buildConfiguration.getTag())
            //.setCredentialsProvider()
            .call()) {
        // Note: the call() returns an opened repository already which needs to be closed to avoid file handle leaks!
    }
    */
  }

  @Override
  public SCMTag getSCMTag(BuildConfiguration buildConfiguration) throws IOException {
    FileRepositoryBuilder fileRepositoryBuilder = new FileRepositoryBuilder();
    fileRepositoryBuilder.setWorkTree(new File(buildConfiguration.getLocalWorkspace()));
    Repository repository = fileRepositoryBuilder.build();

    final Ref ref = repository.exactRef(getRef(buildConfiguration));

    try (RevWalk walk = new RevWalk(repository)) {
      RevCommit commit = walk.parseCommit(ref.getObjectId());

      Calendar calendar = Calendar.getInstance();
      calendar.setTimeInMillis(((long) commit.getCommitTime() * 1000));
      final SCMTag scmTag =
          new SCMTag(
              buildConfiguration.getTag(),
              buildConfiguration.getVariant(),
              calendar.getTime(),
              new Date(),
              commit.toString(),
              commit.getFullMessage());
      walk.dispose();
      return (scmTag);
    }
    // create a new patch
    // write build date, tag, tag date, commit ref
  }

  protected String getRef(BuildConfiguration buildConfiguration) {
    return "refs/heads/" + buildConfiguration.getTag();
  }

  // public void setup(){}
}
