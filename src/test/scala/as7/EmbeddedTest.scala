package as7

import annotation.tailrec
import java.io.{FileOutputStream, FileInputStream, File}
import java.util.zip.{ZipEntry, ZipInputStream}
import org.jboss.as.arquillian.container.embedded.{EmbeddedContainerConfiguration, EmbeddedDeployableContainer}
import org.junit.{Ignore, Test}

/**
 * // TODO: Document this
 * @author Galder Zamarreño
 * @since // TODO
 */
class EmbeddedTest {

   @Ignore
   @Test
   def test000() {
      // 1. With the AS7 zip dependency in place, unzip it
      val tmpDir = System.getProperty("java.io.tmpdir")
      val home = System.getProperty("user.home")
      val jbossHome = tmpDir + "/jboss-as-7.x.incremental.546"
      val jbossHomeDir = new File(jbossHome)

      if (!jbossHomeDir.exists())
         unzip(home + "/.m2/repository/" +
                 "org/jboss/as/jboss-as-dist/7.x.incremental.546/" +
                 "jboss-as-dist-7.x.incremental.546.zip",
            tmpDir)

      // TODO: ALR, why not use 'jboss.home.dir' ? ServerEnvironment.HOME_DIR
      System.setProperty("jboss.home", jbossHomeDir.getAbsolutePath)

      val container = new EmbeddedDeployableContainer
      container.setup(new EmbeddedContainerConfiguration)
      container.start()
   }

   private def unzip(zipFile: String, outputFolder: String) {
      val buffer = new Array[Byte](1024)

      val folder = new File(outputFolder)
      if (!folder.exists())
         folder.mkdir()

      val stream = new ZipInputStream(new FileInputStream(zipFile))
      try {
         unzipEntry(stream, buffer, outputFolder, stream.getNextEntry)
      } finally {
         stream.closeEntry()
         stream.close()
      }

      println("Done")
   }

   @tailrec
   private def unzipEntry(stream: ZipInputStream, buffer: Array[Byte],
           outputFolder: String, entry: ZipEntry) {
      if (entry == null) return
      else {
         if (!entry.isDirectory) {
            val fileName = entry.getName
            val newFile = new File(outputFolder + File.separator + fileName)
            println("file unzip : " + newFile.getAbsoluteFile)

            new File(newFile.getParent).mkdirs()

            val fos = new FileOutputStream(newFile)
            readStream(stream, fos, buffer, stream.read(buffer))
            fos.close()
         }
         unzipEntry(stream, buffer, outputFolder, stream.getNextEntry)
      }
   }

   @tailrec
   private def readStream(stream: ZipInputStream, fos: FileOutputStream,
           buffer: Array[Byte], len: Int) {
      if (len <= 0) return
      else {
         fos.write(buffer, 0, len)
         readStream(stream, fos, buffer, stream.read(buffer))
      }
   }

}
