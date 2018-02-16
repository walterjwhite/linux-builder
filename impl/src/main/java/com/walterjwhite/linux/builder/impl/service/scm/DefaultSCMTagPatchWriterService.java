package com.walterjwhite.linux.builder.impl.service.scm;

import com.walterjwhite.linux.builder.api.model.SCMTag;
import com.walterjwhite.linux.builder.api.service.SCMTagPatchWriterService;
import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DefaultSCMTagPatchWriterService implements SCMTagPatchWriterService {
  private static final Logger LOGGER =
      LoggerFactory.getLogger(DefaultSCMTagPatchWriterService.class);

  private static final String DATE_FORMAT_PATTERN = "yyyy-MM-dd hh:mm:ss";
  private static final DateFormat DATE_FORMAT = new SimpleDateFormat((DATE_FORMAT_PATTERN));

  private static final String TAG_TEMPLATE =
      "# auto-generated via linux-builder @ %s\n\n"
          + "build date: %s\n"
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
  public void write(SCMTag scmTag) throws IOException {
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
            DATE_FORMAT.format(scmTag.getBuildDate()),
            scmTag.getVariant(),
            scmTag.getTag(),
            DATE_FORMAT.format(scmTag.getCommitDate()),
            scmTag.getScmVersionId(),
            scmTag.getCommitMessage());

    tagFile.getParentFile().mkdirs();
    FileUtils.write(tagFile, contents, "UTF-8");
  }

  private static final String getSystemVersion() {
    return (DefaultSCMTagPatchWriterService.class.getPackage().getImplementationVersion());
  }
}
