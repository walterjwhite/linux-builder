package com.walterjwhite.linux.builder.impl.service.scm;

import com.walterjwhite.linux.builder.api.model.configuration.BuildConfiguration;
import com.walterjwhite.ssh.api.model.SCMTag;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.time.format.DateTimeFormatter;
import org.apache.commons.io.FileUtils;

public class DefaultSCMTagPatchWriterService implements SCMTagPatchWriterService {

  //  private static final String DATE_FORMAT_PATTERN = "yyyy-MM-dd hh:mm:ss";
  //  private static final DateFormat DATE_FORMAT = new SimpleDateFormat((DATE_FORMAT_PATTERN));

  private static final DateTimeFormatter DATE_TIME_FORMATTER =
      DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss");

  private static final String TAG_TEMPLATE =
      "# auto-generated via linux-builder @ %s\n\n"
          + "builddate: %s\n"
          + "variant: %s\n"
          + "scm tag: %s\n"
          + "scm date: %s\n"
          + "scm version ID: %s\n"
          + "scm commit message: %s\n";

  protected final String localRepositoryPath;

  public DefaultSCMTagPatchWriterService(String localRepositoryPath) {
    super();

    this.localRepositoryPath = localRepositoryPath;
  }

  @Override
  public void write(BuildConfiguration buildConfiguration, SCMTag scmTag) throws IOException {
    final File patchFile =
        new File(
            localRepositoryPath
                + File.separator
                + "patches"
                + File.separator
                + "system"
                + File.separator
                + "scm-timestamp.patch");

    if (patchFile.exists()) {
      patchFile.delete();
    }

    patchFile.mkdirs();

    final File tagFile =
        new File(
            patchFile.getAbsolutePath()
                + File.separator
                + "build"
                + File.separator
                + "files"
                + File.separator
                + "etc"
                + File.separator
                + "system");
    final String contents =
        String.format(
            TAG_TEMPLATE,
            getSystemVersion(),
            buildConfiguration.getBuildDate().format(DATE_TIME_FORMATTER),
            buildConfiguration.getVariant(),
            scmTag.getTag(),
            scmTag.getCommitDate().format(DATE_TIME_FORMATTER),
            scmTag.getScmVersionId(),
            scmTag.getCommitMessage());

    tagFile.getParentFile().mkdirs();
    FileUtils.write(tagFile, contents, Charset.defaultCharset() /*"UTF-8"*/);
  }

  // TODO: implement a standard means to get the system version, build date, etc. (wrap the Java
  // API)
  private static final String getSystemVersion() {
    return (DefaultSCMTagPatchWriterService.class.getPackage().getImplementationVersion());
  }
}
