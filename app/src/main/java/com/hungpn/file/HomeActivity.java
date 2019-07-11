package com.hungpn.file;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.hungpn.file.file.ListFileInFolderFragment;
import com.hungpn.file.home.InternalStorageFragment;
import com.hungpn.file.home.ManagerMediaFragment;
import com.hungpn.file.filemanager.MediaManager;
import com.hungpn.file.filemanager.MediaPrecenter;
import com.hungpn.file.home.StatusBarFragment;

public class HomeActivity extends AppCompatActivity {

    private MediaPrecenter precenter;
    private Bundle bundle;
    ActionBarDrawerToggle toggle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        precenter = new MediaManager(this);
        bundle = new Bundle();
        initHome();
    }

    public void initHome(){
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();

        StatusBarFragment statusBarFragment = new StatusBarFragment();
        ManagerMediaFragment managerMediaFragment = new ManagerMediaFragment();
        InternalStorageFragment internalStorage = new InternalStorageFragment();

        bundle.putString("COUNT_IMAGE",precenter.getCountImage() +"");
        bundle.putString("COUNT_VIDEO",precenter.getCountVideo() +"");
        bundle.putString("COUNT_MUSIC",precenter.getCountMusic() +"");
        bundle.putString("COUNT_APP",precenter.getCountApp() +"");
        bundle.putString("COUNT_ZIP", precenter.getCountZip()+ "");
        bundle.putString("COUNT_DOCUMENT", precenter.getCountDocument()+ "");
        bundle.putString("COUNT_DOWNLOAD", precenter.getCountDownload()+ "");
        managerMediaFragment.setArguments(bundle);


        transaction.add(R.id.status_bar, statusBarFragment, StatusBarFragment.class.getName());
        transaction.add(R.id.manager_media, managerMediaFragment, ManagerMediaFragment.class.getName());
        transaction.add(R.id.internal_storage, internalStorage, InternalStorageFragment.class.getName());


        transaction.commit();

    }
    public void openListManager(String pathPlus){
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        ListFileInFolderFragment listFile = new ListFileInFolderFragment();
        Fragment fragmentRemoveManagerMedia = manager.findFragmentByTag(ManagerMediaFragment.class.getName());
        Fragment fragmentRemoveIntenalStorage = manager.findFragmentByTag(InternalStorageFragment.class.getName());

        transaction.remove(fragmentRemoveIntenalStorage);
        transaction.remove(fragmentRemoveManagerMedia);
        bundle.putString("PATHPLUS", pathPlus);
        listFile.setArguments(bundle);
        transaction.add(R.id.list_file, listFile, ListFileInFolderFragment.class.getName());
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
