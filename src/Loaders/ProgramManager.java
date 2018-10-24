package Loaders;

import assets.IO;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.net.JarURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.jar.Attributes;

// Singleton; Idee von Jannis Lehmann
// Zusammenarbeit mit Danny Irrsack und Henri Baumann
public class ProgramManager {
    private static ProgramManager instance;
    private final String PROGRAMS_PATH = System.getProperty("user.dir") + "/programs"; // path relative to users working directory
    private final File[] filesFromProgramDirectory = new File(PROGRAMS_PATH).listFiles();
    private HashMap<String, File> classToFileHashMap = createClassToFileHashMap();

    private ProgramManager() {

    }

    private HashMap<String, File> createClassToFileHashMap() {
        HashMap<String, File> classToFileHashMap = new HashMap<>();
        if (filesFromProgramDirectory != null && filesFromProgramDirectory.length != 0) {
            for (File file : filesFromProgramDirectory) {
                if (file.getName().endsWith(".jar")) {
                    String classname = getProgramClassnameFromManifest(file.getAbsolutePath());
                    if (loadCheck(file.getAbsolutePath(), classname, true) && loadCheck(file.getAbsolutePath(), classname, false)) {
                        classToFileHashMap.put(classname, file);
                    }
                }
            }
            return classToFileHashMap;
        } else {
            return null;
        }
    }

    public static ProgramManager getInstance() {
        return instance == null ? instance = new ProgramManager() : instance;
    }

    /**
     * @param jarPathAndFilename Pfad (Ordner + Name) der jar-Datei
     * @return Der Wert des Attributs Player
     * @author dibo
     * <p>
     * Ermittelt den Wert des Attributs "Player" in der Manifest-Datei der
     * angegebenen jar-Datei
     */
    private String getProgramClassnameFromManifest(String jarPathAndFilename) {
        try {
            URL fileURL = new URL("file:" + jarPathAndFilename);
            URL u = new URL("jar", "", fileURL + "!/");
            JarURLConnection uc = (JarURLConnection) u.openConnection();
            Attributes attr = uc.getMainAttributes();
            return attr != null ? attr.getValue("Player") // Attributes is a map
                    : null;
        } catch (IOException exc) {
            return null;
        }
    }

    /**
     * Checks a given .jar-file whether it has a class contained that can be instantiated with the needed constructor
     */
    private boolean loadCheck(String jarPathAndFileName, String classname, boolean isWhite) {
        try {
            JARClassLoader jcl = new JARClassLoader(jarPathAndFileName);
            Class<?> cla = jcl.loadClass(classname);
            Constructor<?> cs = cla.getDeclaredConstructor(boolean.class);
            cs.newInstance(isWhite);
            return true;
        } catch (Throwable e) {
            e.printStackTrace();
            return false;
        }
    }

    public ArrayList<String> getPlayerNamesList() {
        ArrayList<String> playerNames = new ArrayList<>();
        if (filesFromProgramDirectory == null) {
            return playerNames;
        }
        if (filesFromProgramDirectory.length == 0) {
            return playerNames;
        }

        for (File file : filesFromProgramDirectory) {
            String playerClassName = getProgramClassnameFromManifest(file.getAbsolutePath());

            boolean canInstanciateWhite = loadCheck(file.getAbsolutePath(),
                                                    playerClassName, true);
            boolean canInstanciateBlack = loadCheck(file.getAbsolutePath(),
                                                    playerClassName, false);
            if (canInstanciateWhite && canInstanciateBlack) {
                playerNames.add(playerClassName);
            } else {
                IO.println("Couldn't create Black and White from " + file.getAbsolutePath());
            }
        }
        return playerNames;
    }

    @SuppressWarnings("all")
    public Class<?> loadClassFromProgramsFolderJar(String classname) {
        try {
            JARClassLoader jcl = new JARClassLoader(classToFileHashMap.get(classname).getAbsolutePath());
            return jcl.loadClass(classname);
        } catch (Throwable throwable) {
            throwable.printStackTrace();
            return null;
        }
    }
}
