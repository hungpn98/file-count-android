package com.hungpn.file.filemanager;

import android.content.Context;
import android.database.Cursor;
import android.os.Environment;
import android.provider.MediaStore;

import com.hungpn.file.file.ItemFile;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MediaManager implements MediaPrecenter{
    private Context context;
    public MediaManager(Context context){
        this.context = context;
    }
    List<ItemFile> itemFiles;
    @Override
    public int getCountImage() {
        Cursor c = context.getContentResolver().query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                new String[]{"COUNT(*) as c"},
                null,null,null);
        if (c == null){
            return 0;
        }
        c.moveToFirst();
        int indexC = c.getColumnIndex("c");
        int count = c.getInt(indexC);

        c.close();

        return count;
    }

    @Override
    public int getCountMusic() {
        Cursor c = context.getContentResolver().query(
                MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                new String[]{"COUNT(*) as c"},
                null,null,null);
        if (c == null){
            return 0;
        }
        c.moveToFirst();
        int indexC = c.getColumnIndex("c");
        int count = c.getInt(indexC);

        c.close();

        return count;
    }


    @Override
    public int getCountVideo() {
        Cursor c = context.getContentResolver().query(
                MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
                new String[]{"COUNT(*) as c"},
                null,null,null);
        if (c == null){
            return 0;
        }
        c.moveToFirst();
        int indexC = c.getColumnIndex("c");
        int count = c.getInt(indexC);

        c.close();

        return count;
    }

    @Override
    public int getCountZip() {
        Cursor c = context.getContentResolver().query(
                MediaStore.Files.getContentUri("external"),
                new String[]{"COUNT(*) as c"},
                "_data like ?",
                new String[]{"%.zip"},
                null
        );
        if (c == null) {
            return 0;
        }
        c.moveToFirst();
        int indexC = c.getColumnIndex("c");
        int count = c.getInt(indexC);
        c.close();
        return count;
    }

    @Override
    public int getCountApp() {
        Cursor c = context.getContentResolver().query(
                MediaStore.Files.getContentUri("external"),
                new String[]{"COUNT(*) as c"},
                "_data like ?",
                new String[]{"%.apk"},
                null
        );
        if (c == null) {
            return 0;
        }
        c.moveToFirst();
        int indexC = c.getColumnIndex("c");
        int count = c.getInt(indexC);
        c.close();
        return count;
    }

    @Override
    public int getCountDocument() {
        Cursor c = context.getContentResolver().query(
                MediaStore.Files.getContentUri("external"),
                new String[]{"COUNT(*) as c"},
                "_data like ? or _data like ? or _data like ? or _data like ?" ,
                new String[]{"%.docx", "%.xlsx", "%.pdf", "%.txt"}
                ,null
        );
        if (c == null) {
            return 0;
        }
        c.moveToFirst();

        int indexC = c.getColumnIndex("c");
        int count = c.getInt(indexC);
        c.close();
        return count;    }

    @Override
    public int getCountDownload() {
        String path = Environment.getExternalStorageDirectory().getPath() + "/Download";
        itemFiles = new ArrayList<>();
        itemFiles = readFile(path);
        return itemFiles.size();
    }
    private List<ItemFile> readFile(String path) {
        List<ItemFile> files = new ArrayList<>();
        File file = new File(path);
        File[] arrFile = file.listFiles();
        if (arrFile == null || arrFile.length == 0) {
            return files;
        }
        for (File file1 : arrFile) {
            files.add(new ItemFile(file1.getPath(), file1.getName()));
        }
        return files;
    }

}
