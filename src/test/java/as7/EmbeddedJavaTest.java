package as7;

import org.jboss.arquillian.container.spi.Container;
import org.jboss.arquillian.container.spi.ContainerRegistry;
import org.jboss.arquillian.container.spi.client.container.DeployableContainer;
import org.jboss.arquillian.container.spi.client.deployment.TargetDescription;
import org.jboss.arquillian.container.spi.event.SetupContainer;
import org.jboss.arquillian.container.spi.event.StartContainer;
import org.jboss.arquillian.core.impl.loadable.LoadableExtensionLoader;
import org.jboss.arquillian.core.spi.Manager;
import org.jboss.arquillian.core.spi.ManagerBuilder;
import org.jboss.as.arquillian.container.CommonContainerExtension;
import org.jboss.as.arquillian.container.embedded.EmbeddedContainerConfiguration;
import org.jboss.as.arquillian.container.embedded.EmbeddedDeployableContainer;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 * // TODO: Document this
 *
 * @author Galder Zamarreño
 * @since // TODO
 */
public class EmbeddedJavaTest {

   @Test
   public void test000() throws Exception {
      // 1. With the AS7 zip dependency in place, unzip it
      String tmpDir = System.getProperty("java.io.tmpdir");
      String home = System.getProperty("user.home");
      String jbossHome = tmpDir + "/jboss-as-7.x.incremental.546";
      File jbossHomeDir = new File(jbossHome);
      File jbossCfg = new File(jbossHome + "/standalone/configuration/standalone.xml");

      if (!jbossCfg.exists())
         unzip(home + "/.m2/repository/" +
               "org/jboss/as/jboss-as-dist/7.x.incremental.546/" +
               "jboss-as-dist-7.x.incremental.546.zip",
               tmpDir);

      // TODO: ALR, why not use 'jboss.home.dir' ? ServerEnvironment.HOME_DIR
      System.setProperty("jboss.home", jbossHomeDir.getAbsolutePath());

      // 1. Start arquillian manager
      Manager manager = ManagerBuilder.from()
            .extension(LoadableExtensionLoader.class).create();
      manager.start();
      // 2. Resolve container registry
      ContainerRegistry registry = manager.resolve(ContainerRegistry.class);
      if (registry == null) {
         throw new IllegalStateException("No ContainerRegistry found in Context. Something is wrong with the classpath.....");
      }

      if (registry.getContainers().size() == 0) {
         throw new IllegalStateException(
               "No Containers in registry. You need to add the Container Adaptor dependencies to the plugin dependency section");
      }
      // 2. Get container
      Container container = registry.getContainer(TargetDescription.DEFAULT);
      System.out.println("to container: " + container.getName());
      // 3. Fire events to setup and start
      manager.fire(new SetupContainer(container));
      manager.fire(new StartContainer(container));


   }

   public static void unzip(String zipFile, String outputFolder) throws Exception {
      byte[] buffer = new byte[1024];

      File folder = new File(outputFolder);
      if (!folder.exists())
         folder.mkdir();

      ZipInputStream stream = new ZipInputStream(new FileInputStream(zipFile));
      try {
         ZipEntry entry;
         while ((entry = stream.getNextEntry()) != null) {
            if (entry.isDirectory())
               continue;

            String fileName = entry.getName();
            File newFile = new File(outputFolder + File.separator + fileName);
            System.out.println("file unzip : " + newFile.getAbsoluteFile());

            new File(newFile.getParent()).mkdirs();
            FileOutputStream fos = new FileOutputStream(newFile);

            int len;
            while ((len = stream.read(buffer)) > 0) {
               fos.write(buffer, 0, len);
            }

            fos.close();
         }
      } finally {
         stream.closeEntry();
         stream.close();
      }

      System.out.println("Done");
   }

//   public static class EmbeddedContainerExtension
//         extends CommonContainerExtension {
//
//      @Override
//      public void register(final ExtensionBuilder builder) {
//         super.register(builder);
//         builder.service(DeployableContainer.class,
//               EmbeddedDeployableContainer.class);
//      }
//
//   }

}
