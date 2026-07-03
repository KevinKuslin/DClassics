package com.example.dclassicsbooks.transformer;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.viewpager2.widget.ViewPager2;

public class CarouselTransformer implements ViewPager2.PageTransformer {

    private static final float MIN_SCALE = 0.82f;
    private static final float MIN_ALPHA = 0.55f;
    private static final float TRANSLATION_X = 55f;

    @Override
    public void transformPage(@NonNull View page, float position) {

        float absPosition = Math.abs(position);

        // Scale

        float scale = MIN_SCALE + (1 - absPosition) * (1 - MIN_SCALE);

        page.setScaleX(scale);
        page.setScaleY(scale);

        float alpha = MIN_ALPHA + (1 - absPosition) * (1 - MIN_ALPHA);

        page.setAlpha(alpha);
        page.setTranslationX(-position * TRANSLATION_X);
        page.setElevation((1 - absPosition) * 20);

    }
}