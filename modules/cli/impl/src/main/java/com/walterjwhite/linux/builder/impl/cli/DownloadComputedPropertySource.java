package com.walterjwhite.linux.builder.impl.cli;

import com.walterjwhite.linux.builder.impl.cli.property.BuildDirectory;
import com.walterjwhite.linux.builder.impl.cli.property.DownloadPath;
import com.walterjwhite.property.api.PropertyManager;
import com.walterjwhite.property.api.annotation.PropertySourceIndex;
import com.walterjwhite.property.api.property.ComputedProperty;
import com.walterjwhite.property.impl.source.AbstractSingularPropertySource;
import java.io.File;

@PropertySourceIndex(9999)
public class DownloadComputedPropertySource
    extends AbstractSingularPropertySource<ComputedProperty> {
  public DownloadComputedPropertySource(PropertyManager propertyManager) {
    super(propertyManager, ComputedProperty.class);
  }

  @Override
  protected String get(Class<? extends ComputedProperty> property) {
    if (!property.equals(DownloadPath.class)) return null;

    return propertyManager.get(BuildDirectory.class) + File.separator + "downloads";
  }

  @Override
  protected void doSet(Class<? extends ComputedProperty> property, String value) {}
}
