package Loaders;

import java.io.IOException;
import java.net.JarURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.jar.Attributes;

/**
 * Classloader zum Laden von Klassen aus jar-Dateien
 *
 * @author Dietrich Boles (Uni Oldenburg)
 *
 */
final class JARClassLoader extends URLClassLoader {

    private final String PROGRAMS_PATH = "";

    /**
     * Initialisiert einen Classloader zum Laden von Klassen aus jar-Dateien*
     * @param jarPathAndFilename
     *
    Pfad (Ordner + Name) der jar-Datei
     * @throws MalformedURLException
     *
    wird bei fehlerhaften Angaben geworfen
     */
    public JARClassLoader(String jarPathAndFilename)
            throws MalformedURLException {
        super(new URL[] {});
        this.addURL(new URL("file:" + jarPathAndFilename));
    }

    /**
     * Gibt von einer JarDatei den Classname wieder, also den Spieler-Programm-Namen.
     * @param jarPathAndFilename Der komplette Pfad zur jar-Datei.
     * @return
     */
    static String getProgramClassname(String jarPathAndFilename) {
        try {
            URL fileURL = new URL("file:" + jarPathAndFilename);
            URL u = new URL("jar", "", fileURL + "!/");
            JarURLConnection uc = (JarURLConnection) u.openConnection();
            Attributes attr = uc.getMainAttributes();
            return attr != null ? attr.getValue("Player") : null;
        } catch (IOException exc) {
            return null;
        }
    }
}
