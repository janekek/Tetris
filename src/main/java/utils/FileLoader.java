package utils;

import main.Main;

import java.io.File;

public class FileLoader {

    public static File getFile (String path) {
        String mainPath = (Main.class.getProtectionDomain().getCodeSource().getLocation().getPath().replaceFirst("/", ""));
        File f = (new File(mainPath)).getParentFile();
        return new File(f.getAbsolutePath() + "/resources/" + path);
    }

//    public static File getResource (String path) {
//        try {
//
//            URL url = FileLoader.class.getResource("/" + path);
//            System.out.println(url.toURI());
//            File file = new File(new URI(url.toString().replace(" ","%20")).getSchemeSpecificPart());
//            return file;
//
//            return new File(Objects.requireNonNull(Main.class.getResource("/" + path)).toURI());
//        } catch (URISyntaxException e) {
//            e.printStackTrace();
//            return null;
//        }
//    }

}
