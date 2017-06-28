package com.mightyandrospacebattle;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;


import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;



/**
 * Created by Michal on 26.06.2017.
 */

public class Save {

    public static GameData gd = new GameData();

    public static void save() {

        try {

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);

            oos.writeObject(gd);
            FileHandle file = Gdx.files.local("highscores.sav");

            file.writeBytes(baos.toByteArray(),false);

        } catch (Exception e) {
            e.printStackTrace();

            Gdx.app.exit();

        }


    }

    public static void load() {
        try {
            if (!saveFileExists()) {
                init();
                return;
            }
            FileHandle file = Gdx.files.local("highscores.sav");
            ByteArrayInputStream bais = new ByteArrayInputStream(file.readBytes());

            ObjectInputStream ois = new ObjectInputStream(bais);



            gd = (GameData) ois.readObject();
            ois.close();

        } catch (Exception e) {
            e.printStackTrace();
            Gdx.app.exit();
        }
    }

    public static boolean saveFileExists() {
        FileHandle file = Gdx.files.local("highscores.sav");
        return file.exists();
    }

    public static void init() {
        gd = new GameData();
        gd.init();
        save();
    }
}
