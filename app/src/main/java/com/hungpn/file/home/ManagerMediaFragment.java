package com.hungpn.file.home;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.hungpn.file.HomeActivity;
import com.hungpn.file.R;

public class ManagerMediaFragment extends Fragment implements View.OnClickListener {
    private TextView tvImage, tvVideo, tvMusic, tvZip, tvApp, tvDocument, tvDownload;
    private ImageView ivDownload, ivImage, ivVideo, ivMp3, ivDocument, ivZip, ivApp;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_media_manager, container, false);
        tvImage = view.findViewById(R.id.tv_image);
        tvVideo = view.findViewById(R.id.tv_video);
        tvMusic = view.findViewById(R.id.tv_music);
        tvZip = view.findViewById(R.id.tv_zip);
        tvDocument = view.findViewById(R.id.tv_document);
        tvApp = view.findViewById(R.id.tv_app);
        tvDownload = view.findViewById(R.id.tv_download);

        ivDownload = view.findViewById(R.id.iv_download);
        ivDownload.setOnClickListener(this);
        ivImage = view.findViewById(R.id.iv_image);
        ivImage.setOnClickListener(this);
        ivVideo = view.findViewById(R.id.iv_video);
        ivVideo.setOnClickListener(this);
        ivMp3 = view.findViewById(R.id.iv_music);
        ivMp3.setOnClickListener(this);
        ivApp = view.findViewById(R.id.iv_app);
        ivApp.setOnClickListener(this);
        ivZip = view.findViewById(R.id.iv_zip);
        ivZip.setOnClickListener(this);
        ivDocument = view.findViewById(R.id.iv_document);
        ivDocument.setOnClickListener(this);


        Bundle bundle = getArguments();
        String countImage = bundle.getString("COUNT_IMAGE");
        String countVideo = bundle.getString("COUNT_VIDEO");
        String countMusic = bundle.getString("COUNT_MUSIC");
        String countApp = bundle.getString("COUNT_APP");
        String countZip = bundle.getString("COUNT_ZIP");
        String countDocument = bundle.getString("COUNT_DOCUMENT");
        String countDownload = bundle.getString("COUNT_DOWNLOAD");


        tvVideo.setText(countVideo);
        tvDownload.setText(countDownload);
        tvImage.setText(countImage);
        tvMusic.setText(countMusic);
        tvApp.setText(countApp);
        tvZip.setText(countZip);
        tvDocument.setText(countDocument);
        return view;
    }


    @Override
    public void onClick(View v) {
        FragmentActivity ac = getActivity();
        switch (v.getId()) {
            case R.id.iv_download:
                ((HomeActivity) ac).openListManager("Download");
                break;
            case R.id.iv_image:
                ((HomeActivity) ac).openListManager("Image");
                break;
            case R.id.iv_video:
                ((HomeActivity) ac).openListManager("Video");
                break;
            case R.id.iv_music:
                ((HomeActivity) ac).openListManager("Music");
                break;
            case R.id.iv_app:
                ((HomeActivity) ac).openListManager("App");
                break;
            case R.id.iv_document:
                ((HomeActivity) ac).openListManager("Document");
                break;
            case R.id.iv_zip:
                ((HomeActivity) ac).openListManager("Zip");
                break;

        }
    }
}
