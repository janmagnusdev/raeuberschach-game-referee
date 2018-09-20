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
}
