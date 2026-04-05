package io;

import game.GameMode;
import utils.FileLoader;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class DataHandler {

    public static int getHighScore(GameMode gm) {
        File file = null;
        if (gm == GameMode.LINEBREAKER)
            file = FileLoader.getFile("data/linebreaker highscore.txt");
        if (gm == GameMode.CLASSIC)
            file = FileLoader.getFile("data/save.txt");

        try {
            assert file != null;
            Scanner sc = new Scanner(file);
            return sc.nextInt();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static void saveHighscore(GameMode gm, int highscore) {
        File file = null;
        if (gm == GameMode.LINEBREAKER)
            file = FileLoader.getFile("data/linebreaker highscore.txt");
        if (gm == GameMode.CLASSIC)
            file = FileLoader.getFile("data/save.txt");

        try {
            assert file != null;
            OutputStream stream = new FileOutputStream(file);
            stream.write((Integer.toString(highscore)).getBytes());
            stream.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void saveScore(GameMode gm, int score) {
        File file = null;
        if (gm == GameMode.LINEBREAKER)
            file = FileLoader.getFile("data/linebreaker scores.txt");
        if (gm == GameMode.CLASSIC)
            file = FileLoader.getFile("data/scores.txt");

        try {
            StringBuilder stringScore;
            ArrayList<String> list = getScores(gm);
            list.add(Integer.toString(score));
            if (list.size() > 16) {
                list.subList(0, list.size() - 16).clear();
            }
            stringScore = new StringBuilder();
            for (String s : list) {
                stringScore.append(";").append(s);
            }
            stringScore = new StringBuilder(stringScore.toString().replaceFirst(";", ""));

            System.out.println(stringScore);

            assert file != null;
            FileWriter fileWriter = new FileWriter(file);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.write(stringScore.toString());
            bufferedWriter.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<String> getScores (GameMode gm) {
        File file = null;
        if (gm == GameMode.LINEBREAKER)
            file = FileLoader.getFile("data/linebreaker scores.txt");
        if (gm == GameMode.CLASSIC)
            file = FileLoader.getFile("data/scores.txt");

        Scanner sc = null;

        try {
            assert file != null;
            sc = new Scanner(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        assert sc != null;
        return new ArrayList<>(Arrays.asList(sc.next().split(";")));
    }

    public static void saveSomething (String path, int i) {
        File file = FileLoader.getFile(path);

        try {
            OutputStream stream = new FileOutputStream(file);
            stream.write((Integer.toString(i)).getBytes());
            stream.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static int getSomething (String path) {
        File file = FileLoader.getFile(path);
        Scanner sc;

        try {
            sc = new Scanner(file);
            return sc.nextInt();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return -1;
    }

}
