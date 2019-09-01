// package com.walterjwhite.linux.builder.modules.cli;
//
// import com.walterjwhite.google.guice.property.AbstractPropertyModule;
// import com.walterjwhite.linux.builder.impl.cli.LinuxBuilderCommandLineModule;
// import org.junit.Test;
//
// public class ServiceStopTimeoutTest {
//  @Test
//  public void doTest() throws NoSuchFieldException {
//    System.out.println(
//        "is assignable 1:"
//            + LinuxBuilderCommandLineModule.class.isAssignableFrom(
//                AbstractPropertyModule.class
//                    .getDeclaredField("serviceStopTimeout")
//                    .getDeclaringClass()));
//    System.out.println(
//        "is assignable 2:"
//            + AbstractPropertyModule.class
//                .getDeclaredField("serviceStopTimeout")
//                .getDeclaringClass()
//                .isAssignableFrom(LinuxBuilderCommandLineModule.class));
//    // System.out.println("is assignable 3:" +
//    //
// LinuxBuilderCommandLineModule.class.getDeclaredField("testField").getDeclaringClass().isAssignableFrom(LinuxBuilderCommandLineModule.class));
//  }
// }
