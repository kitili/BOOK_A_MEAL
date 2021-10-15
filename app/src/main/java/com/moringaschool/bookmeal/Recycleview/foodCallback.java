package com.moringaschool.bookmeal.Recycleview;

import android.widget.ImageView;
import android.widget.TextView;

public interface foodCallback {
    void onFoodItemClick(int pos,
                         ImageView imgContainer,
                         ImageView imgFood,
                         TextView title,
                         TextView price,
                         TextView description);
}
