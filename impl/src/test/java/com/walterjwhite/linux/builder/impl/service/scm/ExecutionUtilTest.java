package com.walterjwhite.linux.builder.impl.service.scm;

import com.walterjwhite.shell.api.enumeration.VFSType;
import com.walterjwhite.shell.api.model.MountPoint;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ExecutionUtilTest {
  /**
   * doMount -o bind /projects rootDirectory/projects doMount -t tmpfs rootDirectory/tmp Test method
   * to see that this works ...
   */
  public static void main(final String[] arguments) throws IOException, InterruptedException {
    try {
      final List<MountPoint> mountPoints = new ArrayList<>();
      //            mountPoints.add(new MountPoint("/proc", "none", "proc"));//VFSType.PROC));
      //            mountPoints.add(new MountPoint("/dev", "/dev", ""));//VFSType.RBIND));
      //            mountPoints.add(new MountPoint("/sys", "/sys", ""));//VFSType.RBIND));
      //            mountPoints.add(new MountPoint("/projects", "/projects", ""));//VFSType.BIND));
      //            mountPoints.add(new MountPoint("/tmp", "none", "tmpfs"));//VFSType.TMPFS));

      mountPoints.add(new MountPoint("/proc", "none", VFSType.PROC));
      mountPoints.add(new MountPoint("/dev", "/dev", VFSType.RBIND));
      mountPoints.add(new MountPoint("/sys", "/sys", VFSType.RBIND));
      mountPoints.add(new MountPoint("/projects", "/projects", VFSType.BIND));
      mountPoints.add(new MountPoint("/tmp", "none", VFSType.TMPFS));

      final String rootPath = "/builds/linux/router/rootDirectory";

      // runInChroot(mountPoints, rootPath, new String[]{"cat", "/etc/system"});
    } catch (Exception e) {
      // LOGGER.error("Error running test commands", e);
    }
  }
}
