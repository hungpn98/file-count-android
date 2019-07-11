package com.hungpn.file.file;

import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.ImageView;

import com.hungpn.file.R;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ListFileInFolderFragment extends Fragment implements FileAdapter.IFile {
    private List<ItemFile> itemFiles;
    private RecyclerView rc;
    private ImageView ivShowImage;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list_file, container, false);
        Bundle bundle = getArguments();
        String key = bundle.getString("PATHPLUS");
        if(key == ""){
            String path = Environment.getExternalStorageDirectory().getPath();
            itemFiles = readFile(path);
            ivShowImage = view.findViewById(R.id.iv_show_image);
        }
        if (key == "Download") {
            String path = Environment.getExternalStorageDirectory().getPath() + "/" + key;
            itemFiles = readFile(path);
            ivShowImage = view.findViewById(R.id.iv_show_image);

        } else if (key == "Image") {
            itemFiles = getAllImage();
        } else if (key == "Video") {
            itemFiles = getAllVideo();
        } else if (key == "Music") {
            itemFiles = getAllMusic();
        } else if (key == "App") {
            itemFiles = getAllApp();
        } else if (key == "Document") {
            itemFiles = getAllDocument();
        }else if (key == "Zip") {
            itemFiles = getAllZip();
        }
        rc = view.findViewById(R.id.rv_list);
        rc.setLayoutManager(new LinearLayoutManager(getActivity()));
        rc.setAdapter(new FileAdapter(this));
        return view;
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
    private List<ItemFile> getAllImage() {
        List<ItemFile> files = new ArrayList<>();
        Cursor c = getContext().getContentResolver().query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                null, null, null, null
        );
        c.getColumnNames();
        String getDataPath = "_data";
        String getDataName = "_display_name";
        int indexPath = c.getColumnIndex(getDataPath);
        int indexName = c.getColumnIndex(getDataName);

        c.moveToFirst();
        while (!c.isAfterLast()) {
            String path = c.getString(indexPath);
            String name = c.getString(indexName);
            files.add(new ItemFile(path, name));
            c.moveToNext();
        }
        c.close();
        return files;
    }private List<ItemFile> getAllVideo() {
        List<ItemFile> files = new ArrayList<>();
        Cursor c = getContext().getContentResolver().query(
                MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
                null, null, null, null
        );
        c.getColumnNames();
        String getDataPath = "_data";
        String getDataName = "_display_name";
        int indexPath = c.getColumnIndex(getDataPath);
        int indexName = c.getColumnIndex(getDataName);

        c.moveToFirst();
        while (!c.isAfterLast()) {
            String path = c.getString(indexPath);
            String name = c.getString(indexName);
            files.add(new ItemFile(path, name));
            c.moveToNext();
        }
        c.close();
        return files;
    }private List<ItemFile> getAllMusic() {
        List<ItemFile> files = new ArrayList<>();
        Cursor c = getContext().getContentResolver().query(
                MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                null, null, null, null
        );
        c.getColumnNames();
        String getDataPath = "_data";
        String getDataName = "_display_name";
        int indexPath = c.getColumnIndex(getDataPath);
        int indexName = c.getColumnIndex(getDataName);

        c.moveToFirst();
        while (!c.isAfterLast()) {
            String path = c.getString(indexPath);
            String name = c.getString(indexName);
            files.add(new ItemFile(path, name));
            c.moveToNext();
        }
        c.close();
        return files;
    }

    private List<ItemFile> getAllApp() {
        List<ItemFile> files = new ArrayList<>();
        Cursor c = getContext().getContentResolver().query(
                MediaStore.Files.getContentUri("external"),
                null,
                "_data like ?",
                new String[]{"%.apk"},
                null
        );
        String getDataPath = "_data";
        String getDataName = "_display_name";
        int indexPath = c.getColumnIndex(getDataPath);
        int indexName = c.getColumnIndex(getDataName);
        c.moveToFirst();
        while (!c.isAfterLast()) {
            String path = c.getString(indexPath);
            String name = c.getString(indexName);
            files.add(new ItemFile(path, name));
            c.moveToNext();
        }
        c.close();
        return files;
    }

    private List<ItemFile> getAllDocument() {
        List<ItemFile> files = new ArrayList<>();
        Cursor c = getContext().getContentResolver().query(
                MediaStore.Files.getContentUri("external"),
                null,
                "_data like ? or _data like ? or _data like ? or _data like ?" ,
                new String[]{"%.docx", "%.xlsx", "%.pdf", "%.txt"},
                null
        );
        String getDataPath = "_data";
        String getDataName = "_display_name";
        int indexPath = c.getColumnIndex(getDataPath);
        int indexName = c.getColumnIndex(getDataName);
        c.moveToFirst();
        while (!c.isAfterLast()) {
            String path = c.getString(indexPath);
            String name = c.getString(indexName);
            files.add(new ItemFile(path, name));
            c.moveToNext();
        }
        c.close();
        return files;
    }
    private List<ItemFile> getAllZip() {
        List<ItemFile> files = new ArrayList<>();
        Cursor c = getContext().getContentResolver().query(
                MediaStore.Files.getContentUri("external"),
                null,
                "_data like ?",
                new String[]{"%.zip"},
                null
        );
        String getDataPath = "_data";
        String getDataName = "_display_name";
        int indexPath = c.getColumnIndex(getDataPath);
        int indexName = c.getColumnIndex(getDataName);
        c.moveToFirst();
        while (!c.isAfterLast()) {
            String path = c.getString(indexPath);
            String name = c.getString(indexName);
            files.add(new ItemFile(path, name));
            c.moveToNext();
        }
        c.close();
        return files;
    }

    @Override
    public int getSize() {
        return itemFiles.size();
    }

    @Override
    public ItemFile getData(int position) {
        return itemFiles.get(position);
    }

    @Override
    public void onClickItem(int position) {
        String path = itemFiles.get(position).getPath();
        itemFiles = readFile(itemFiles.get(position).getPath());
        String type = getMimeType(path);
        if (type == "image/jpeg") {
            ivShowImage.setImageBitmap(BitmapFactory.decodeFile(path));
        }
        if (type == "audio/mpeg") {
            playerMusic(path);
        }
        rc.getAdapter().notifyDataSetChanged();

    }



    public String getMimeType(String url) {
        String type = null;
        String extension = MimeTypeMap.getFileExtensionFromUrl(url);
        if (extension != null) {
            type = MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension);
        }
        return type;
    }

    private void playerMusic(String path) {
        try {
            MediaPlayer mediaPlayer = new MediaPlayer();
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);

            mediaPlayer.setDataSource(path);
            mediaPlayer.prepare();
            mediaPlayer.start();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
