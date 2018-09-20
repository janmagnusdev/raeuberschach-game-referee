package Loaders;

import GameModel.Board;
import GameModel.Players.DummyPlayer;
import assets.IO;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.net.JarURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.jar.Attributes;

//Singleton
public class ProgramManager {
    private static ProgramManager instance;
    private final String PROGRAMS_PATH = "../raeuberschach-game-referee/src/programs"; //relative path

    private ProgramManager() {

    }

    /*
    public static void main(String[] args) {
        IO.print(ProgramManager.getInstance().loadCheck("programs/ DummyPlayer.jar", DummyPlayer.class.getName(), true));
        IO.print(ProgramManager.getInstance().loadCheck("programs/ DummyPlayer.jar", DummyPlayer.class.getName(),
                                                        false));
    }
    */

    public static ProgramManager getInstance() {
        return instance == null ? instance = new ProgramManager() : instance;
    }

    /**
     * @author dibo
     *
     * Ermittelt den Wert des Attributs "Player" in der Manifest-Datei der
     * angegebenen jar-Datei
     *
     * @param jarPathAndFilename
     *
    Pfad (Ordner + Name) der jar-Datei
     * @return der Wert des Attributs Player
     */
    public String getProgramClassname(String jarPathAndFilename) {
        try {
            URL fileURL = new URL("file:" + jarPathAndFilename);
            URL u = new URL("jar", "", fileURL + "!/");
            JarURLConnection uc = (JarURLConnection) u.openConnection();
            Attributes attr = uc.getMainAttributes();
            return attr != null ? attr.getValue("Player") //Attributes is a map
                    : null;
        } catch (IOException exc) {
            return null;
        }
    }

    /**
     * Checks a given .jar-File whether it is has a Class contained
     * @param jarPathAndFileName
     * @param classname
     * @param isWhite
     * @return
     */
    private boolean loadCheck(String jarPathAndFileName, String classname, boolean isWhite) {
         try (JARClassLoader jcl = new JARClassLoader(jarPathAndFileName)) {
             Class<?> cla = jcl.loadClass(classname);
             Constructor<?> cs = cla.getDeclaredConstructor(boolean.class, Board.class);
             cs.newInstance(isWhite, new Board());
             return true;
         } catch (Throwable e) {
             e.printStackTrace();
             return false;
         }
    }

    public Class<?> loadClassFromProgramsFolder(String classname) {
        try (JARClassLoader jcl = new JARClassLoader(PROGRAMS_PATH)) {
            return jcl.loadClass(classname);
        } catch (Throwable e) {
            e.printStackTrace();
            return null;
        }
    }

    public ArrayList<String> getPlayerNamesList() {
        ArrayList<String> playerNames = new ArrayList<>();
            File programDirectory = new File(PROGRAMS_PATH);
            File[] files = programDirectory.listFiles();
            if (files == null || files.length == 0) {
                return playerNames;
            }

            for (File file : files) {
                String playerClassName = getProgramClassname(file.getAbsolutePath());

                boolean canInstanciateWhite = loadCheck(file.getAbsolutePath(),
                                                        playerClassName, true);
                boolean canInstanciateBlack = loadCheck(file.getAbsolutePath(),
                                                     playerClassName, false);
                if (canInstanciateWhite && canInstanciateBlack) {
                    playerNames.add(playerClassName);
                }
            }
            return playerNames;
    }
}
