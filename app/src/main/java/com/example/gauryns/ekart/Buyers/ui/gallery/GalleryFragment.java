package com.example.gauryns.ekart.Buyers.ui.gallery;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.support.annotation.Nullable;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;

import com.example.gauryns.ekart.R;

public class GalleryFragment extends Fragment {


    private GalleryViewModel galleryViewModel;

    private ImageView tShirts, sportsTShrits, femaleDresses, sweaters;
    private ImageView glasses, hatsCaps, walletsBags, shoes;
    private ImageView headPhonesHandFree, Laptops, watches, mobilePhones;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        galleryViewModel =
                ViewModelProviders.of(this).get(GalleryViewModel.class);
        View root = inflater.inflate(R.layout.fragment_gallery, container, false);


        tShirts = (ImageView) root.findViewById(R.id.categories_t_shirts);
        sportsTShrits = (ImageView) root.findViewById(R.id.categories_sports_t_shirts);
        femaleDresses = (ImageView) root.findViewById(R.id.categories_female_dresses);
        sweaters = (ImageView) root.findViewById(R.id.categories_sweaters);
        glasses = (ImageView) root.findViewById(R.id.categories_glasses);
        hatsCaps = (ImageView) root.findViewById(R.id.categories_hats_caps);
        walletsBags = (ImageView) root.findViewById(R.id.categories_purses_bags_wallets);
        shoes = (ImageView) root.findViewById(R.id.categories_shoes);
        headPhonesHandFree = (ImageView) root.findViewById(R.id.categories_headphones_handfree);
        Laptops = (ImageView) root.findViewById(R.id.categories_laptop_pc);
        watches = (ImageView) root.findViewById(R.id.categories_watches);
        mobilePhones = (ImageView) root.findViewById(R.id.categories_mobilePhones);

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        tShirts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



            }
        });

    }
}