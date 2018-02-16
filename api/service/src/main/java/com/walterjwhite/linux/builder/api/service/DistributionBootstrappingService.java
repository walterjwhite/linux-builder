package com.walterjwhite.linux.builder.api.service;

import com.walterjwhite.linux.builder.api.model.BuildPhase;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;

public interface DistributionBootstrappingService {
  /**
   * Setup the base system (ie. gentoo/funtoo, download stage 3, unpack it, fedora, download base
   * ISO image and unpack it).
   *
   * @throws IOException
   * @throws InterruptedException
   */
  void doBootstrap() throws IOException, InterruptedException, NoSuchAlgorithmException;

  /**
   * Tasks to run before the given build phase (ie. gentoo/funtoo emerge --sync, emerge --newuse -uD
   * world -v).
   */
  void doPreBuild(BuildPhase buildPhase) throws Exception;

  /** Tasks to run after the given build phase (currently empty). */
  void doPostBuild(BuildPhase buildPhase) throws Exception;
}
