package com.walterjwhite.linux.builder.impl.service.annotation;

import com.walterjwhite.linux.builder.api.model.configuration.Configurable;
import com.walterjwhite.linux.builder.impl.service.enumeration.DistributionConfiguration;
import com.walterjwhite.linux.builder.impl.service.util.configuration.Configurer;
import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ModuleSupports {
  DistributionConfiguration distribution();

  Class<? extends Configurer> configurer();

  Class<? extends Configurable> configurationClass();
}
