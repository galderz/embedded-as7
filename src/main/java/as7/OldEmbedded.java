package as7;

import org.jboss.as.embedded.ServerEnvironment;
import org.jboss.as.embedded.StandaloneServer;
import org.jboss.modules.Module;
import org.jboss.modules.ModuleClassLoader;
import org.jboss.modules.ModuleIdentifier;
import org.jboss.modules.ModuleLoader;
import org.jboss.modules.log.JDKModuleLogger;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.Properties;
import java.util.logging.LogManager;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import static org.jboss.as.embedded.EmbeddedMessages.MESSAGES;

/**
 * Hello world!
 */
public class OldEmbedded {

//   public static void main(String[] args) throws Exception {
//      // 1. With the AS7 zip dependency in place, unzip it
//      String tmpDir = System.getProperty("java.io.tmpdir");
//      String home = System.getProperty("user.home");
//      String jbossHome = tmpDir + "/jboss-as-7.x.incremental.379";
//      File jbossHomeDir = new File(jbossHome);
//
//      if (!jbossHomeDir.exists())
//         unzip(home + "/.m2/repository/" +
//            "org/jboss/as/jboss-as-dist/7.x.incremental.379/" +
//            "jboss-as-dist-7.x.incremental.379.zip",
//            tmpDir);
//
//      // 2. Set log manager
//      System.setProperty("java.util.logging.manager",
//            "org.jboss.logmanager.LogManager");
//
//      // 3. Load VFS module
//      Module vfsModule = Module.getBootModuleLoader().loadModule(
//            ModuleIdentifier.create("org.jboss.vfs"));
//      Module.registerURLStreamHandlerFactoryModule(vfsModule);
//
//      // ?
//      System.setProperty(ServerEnvironment.HOME_DIR, jbossHomeDir.getAbsolutePath());
//      File modulesDir = new File(jbossHomeDir + "/modules");
//      ModuleLoader moduleLoader = getModuleLoader(modulesDir);
//      Module.registerURLStreamHandlerFactoryModule(moduleLoader.loadModule(
//            ModuleIdentifier.create("org.jboss.vfs")));
//
//      // Initialize the Logging system
//      ModuleIdentifier logModuleId = ModuleIdentifier.create("org.jboss.logmanager");
//      ModuleClassLoader logModuleClassLoader = moduleLoader.loadModule(logModuleId).getClassLoader();
//      ClassLoader ctxClassLoader = Thread.currentThread().getContextClassLoader();
//      try {
//         Thread.currentThread().setContextClassLoader(logModuleClassLoader);
//         System.setProperty("java.util.logging.manager", "org.jboss.logmanager.LogManager");
//         if (LogManager.getLogManager().getClass() == LogManager.class) {
//            System.err.println(MESSAGES.failedToLoadLogModule(logModuleId));
//         } else {
//            Module.setModuleLogger(new JDKModuleLogger());
//         }
//      } finally {
//         Thread.currentThread().setContextClassLoader(ctxClassLoader);
//      }
//
//      __redirected.__JAXPRedirected.changeAll(ModuleIdentifier
//            .fromString("javax.xml.jaxp-provider"), moduleLoader);
//
//      ModuleIdentifier serverModuleId = ModuleIdentifier.create("org.jboss.as.server");
//      Module serverModule = moduleLoader.loadModule(serverModuleId);
//      ModuleClassLoader serverModuleClassLoader = serverModule.getClassLoader();
//
//      Class<?> embeddedStandAloneServerFactoryClass = serverModuleClassLoader
//            .loadClass("org.jboss.as.server.EmbeddedStandAloneServerFactory");
//      Method createMethod = embeddedStandAloneServerFactoryClass
//            .getMethod("create", File.class, ModuleLoader.class, Properties.class, Map.class);
//      StandaloneServer server = (StandaloneServer) createMethod
//            .invoke(null, jbossHomeDir, moduleLoader, System.getProperties(), System.getenv());
//
//      server.start();
//      server.stop();
//   }
//
//   public static void unzip(String zipFile, String outputFolder) throws Exception {
//      byte[] buffer = new byte[1024];
//
//      File folder = new File(outputFolder);
//      if (!folder.exists())
//         folder.mkdir();
//
//      ZipInputStream stream = new ZipInputStream(new FileInputStream(zipFile));
//      try {
//         ZipEntry entry;
//         while ((entry = stream.getNextEntry()) != null) {
//            if (entry.isDirectory())
//               continue;
//
//            String fileName = entry.getName();
//            File newFile = new File(outputFolder + File.separator + fileName);
//            System.out.println("file unzip : " + newFile.getAbsoluteFile());
//
//            new File(newFile.getParent()).mkdirs();
//            FileOutputStream fos = new FileOutputStream(newFile);
//
//            int len;
//            while ((len = stream.read(buffer)) > 0) {
//               fos.write(buffer, 0, len);
//            }
//
//            fos.close();
//            entry = stream.getNextEntry();
//         }
//      } finally {
//         stream.closeEntry();
//         stream.close();
//      }
//
//      System.out.println("Done");
//   }
//
//   public static ModuleLoader getModuleLoader(File modulePath,
//         String... systemPackages) {
//      String oldClassPath = System.getProperty("java.class.path");
//      try {
//         System.clearProperty("java.class.path");
//         System.setProperty("module.path", modulePath.getAbsolutePath());
//
//         StringBuffer packages = new StringBuffer("org.jboss.modules," +
//               OldEmbedded.class.getPackage().getName());
//
//         // for model operations
//         packages.append(",org.jboss.as.controller.client,org.jboss.dmr");
//         if (systemPackages != null) {
//            for (String packageName : systemPackages)
//               packages.append("," + packageName);
//         }
//         System.setProperty("jboss.modules.system.pkgs", packages.toString());
//         ModuleLoader moduleLoader = Module.getBootModuleLoader();
//         return moduleLoader;
//      } finally {
//         System.setProperty("java.class.path", oldClassPath);
//      }
//   }

}
