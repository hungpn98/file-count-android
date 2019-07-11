package com.hungpn.file.file;

import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.ImageView;
import android.widget.TextView;

import com.hungpn.file.R;

public class FileAdapter extends RecyclerView.Adapter<FileAdapter.FileHolder>{
    private IFile inter;

    public FileAdapter(IFile inter) {
        this.inter = inter;
    }

    @NonNull
    @Override
    public FileHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_file, viewGroup, false);
        return new FileHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final FileHolder fileHolder, int i) {
        ItemFile itemFile = inter.getData(i);
        fileHolder.tvNameFolder.setText(itemFile.getName());
        if (getMimeType(itemFile.getPath())== null){
            fileHolder.ivIconFolder.setImageResource(R.drawable.folder);
        }else {
            fileHolder.ivIconFolder.setImageBitmap(BitmapFactory.decodeFile(itemFile.getPath()));
        }

        fileHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inter.onClickItem(fileHolder.getAdapterPosition());
            }
        });
    }

    @Override
    public int getItemCount() {
        return inter.getSize();
    }

    public interface IFile{
        int getSize();
        ItemFile getData(int position);
        void onClickItem(int position);
    }

    static class FileHolder extends RecyclerView.ViewHolder{
        private ImageView ivIconFolder;
        private TextView tvNameFolder;
        public FileHolder(@NonNull View itemView) {
            super(itemView);
            tvNameFolder = itemView.findViewById(R.id.tv_name_folder);
            ivIconFolder = itemView.findViewById(R.id.iv_icon_folder);
        }
    }
    public String getMimeType(String url) {
        String type = null;
        String extension = MimeTypeMap.getFileExtensionFromUrl(url);
        if (extension != null) {
            type = MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension);
        }
        return type;
    }
}
