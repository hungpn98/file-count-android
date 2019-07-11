package com.hungpn.file.home;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StatFs;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.hungpn.file.HomeActivity;
import com.hungpn.file.R;
import com.hungpn.file.filemanager.SDCardInfo;

import java.io.File;
import java.text.DecimalFormat;

public class InternalStorageFragment extends Fragment implements View.OnClickListener {
    private ProgressBar pbInternalStorage;
    private TextView tvFreeStorage, tvStorage;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_progress_bar, container,false);
        SDCardInfo sdCardInfo = getSDCardInfo();
        DecimalFormat df = new DecimalFormat("#.0");
        float storage = sdCardInfo.getA()/1024/1024/1024;
        float storageUsed = sdCardInfo.getB()/1024/1024/1024;
        float storageFree = storage - storageUsed;
        float percentUseStorage =(storageUsed / storage) * 100;
        pbInternalStorage = view.findViewById(R.id.pb_storage);
        tvFreeStorage = view.findViewById(R.id.tv_storage_free);
        tvStorage = view.findViewById(R.id.tv_storage);

        tvFreeStorage.setText(df.format(storageFree) + "GB");
        tvStorage.setText(df.format(storage) + "GB");
        pbInternalStorage.setProgress((int) percentUseStorage);
        pbInternalStorage.setOnClickListener(this);

        return view;
    }


    public SDCardInfo getSDCardInfo() {
        if(Environment.getExternalStorageState()
                .equals(Environment.MEDIA_MOUNTED)) {
            File fileRootExternal = Environment.getExternalStorageDirectory();
            StatFs statFs = new StatFs(fileRootExternal.getPath());
            long blockSizeCount;
            long sizeBlocks;
            long availableBlocks;
            if (Build.VERSION.SDK_INT >= 			Build.VERSION_CODES.JELLY_BEAN_MR2) {
                blockSizeCount = statFs.getBlockCountLong();
                sizeBlocks = statFs.getBlockSizeLong();
                availableBlocks = statFs.getAvailableBlocksLong();

            } else {
                blockSizeCount = statFs.getBlockCount();
                sizeBlocks = statFs.getBlockSize();
                availableBlocks = statFs.getAvailableBlocks();
            }

            SDCardInfo sdCardInfo = new SDCardInfo(blockSizeCount * sizeBlocks,
                    blockSizeCount * sizeBlocks - availableBlocks * sizeBlocks);

            return sdCardInfo;
        }
        return null;
    }

    @Override
    public void onClick(View v) {

        FragmentActivity ac = getActivity();
        ((HomeActivity)ac).openListManager("");
    }
}
