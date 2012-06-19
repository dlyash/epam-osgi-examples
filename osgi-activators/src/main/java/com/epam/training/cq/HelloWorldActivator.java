package com.epam.training.cq;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

public class HelloWorldActivator implements BundleActivator {
  public void start(BundleContext ctx) throws Exception {
    System.out.println("Hello World!");
  }
  
  public void stop(BundleContext ctx) throws Exception {
    System.out.println("Goodbye World!");
  }
}
