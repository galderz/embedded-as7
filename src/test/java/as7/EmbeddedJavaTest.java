package as7;

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

      if (!jbossHomeDir.exists())
         unzip(home + "/.m2/repository/" +
            "org/jboss/as/jboss-as-dist/7.x.incremental.546/" +
            "jboss-as-dist-7.x.incremental.546.zip",
            tmpDir);

      // TODO: ALR, why not use 'jboss.home.dir' ? ServerEnvironment.HOME_DIR
      System.setProperty("jboss.home", jbossHomeDir.getAbsolutePath());

      EmbeddedDeployableContainer container = new EmbeddedDeployableContainer();
      container.setup(new EmbeddedContainerConfiguration());
      container.start();
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

}
