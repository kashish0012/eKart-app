package com.example.gauryns.ekart.Interface;

import android.view.View;

public interface ItemClickListner
{
    void onClick(View view, int position, boolean isLongClick);

    void onClick(View view, int adapterPosition);
}
