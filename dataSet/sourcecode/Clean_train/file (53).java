package com.wmods.wppenhacer.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.wmods.wppenhacer.R;
import com.wmods.wppenhacer.databinding.ActivityAboutBinding;

public class AboutActivity extends AppCompatActivity {


    private ActivityAboutBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getTheme().applyStyle(R.style.ThemeOverlay_MaterialGreen, true);
        binding = ActivityAboutBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.btnTelegram.setOnClickListener(v -> {
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_VIEW);
            intent.setData(Uri.parse("https://t.me/waenhancer"));
            startActivity(intent);
        });
        binding.btnGithub.setOnClickListener(view -> {
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_VIEW);
            intent.setData(Uri.parse("https://github.com/Dev4Mod/waenhancer"));
            startActivity(intent);
        });
        binding.btnDonate.setOnClickListener(view -> {
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_VIEW);
            intent.setData(Uri.parse("https://coindrop.to/dev4mod"));
            startActivity(intent);
        });

    }
}
