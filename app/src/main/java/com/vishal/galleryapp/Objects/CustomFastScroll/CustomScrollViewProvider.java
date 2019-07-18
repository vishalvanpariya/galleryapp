package com.vishal.galleryapp.Objects.CustomFastScroll;

import android.annotation.SuppressLint;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.graphics.drawable.shapes.RectShape;
import android.support.v4.content.ContextCompat;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.futuremind.recyclerviewfastscroll.viewprovider.DefaultBubbleBehavior;
import com.futuremind.recyclerviewfastscroll.RecyclerViewScrollListener;
import com.futuremind.recyclerviewfastscroll.viewprovider.ScrollerViewProvider;
import com.futuremind.recyclerviewfastscroll.Utils;
import com.futuremind.recyclerviewfastscroll.viewprovider.ViewBehavior;
import com.futuremind.recyclerviewfastscroll.viewprovider.VisibilityAnimationManager;
import com.vishal.galleryapp.R;

/**
 * Created by Michal on 05/08/16.
 */
public class CustomScrollViewProvider extends ScrollerViewProvider {

    private TextView bubble;
    private View handle;

    @Override
    public View provideHandleView(ViewGroup container) {
        handle = new View(getContext());
        int dimen = getContext().getResources().getDimensionPixelSize(R.dimen.custom_handle_size);
        handle.setLayoutParams(new ViewGroup.LayoutParams(dimen, dimen));
        Utils.setBackground(handle, drawCircle(dimen, dimen, ContextCompat.getColor(getContext(), R.color.colorPrimary),"cir"));
        handle.setVisibility(View.INVISIBLE);
        return handle;
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public View provideBubbleView(ViewGroup container) {
        bubble = new TextView(getContext());
        int dimen = getContext().getResources().getDimensionPixelSize(R.dimen.custom_bubble_size);
        bubble.setLayoutParams(new ViewGroup.LayoutParams(250, 30));
        //Utils.setBackground(bubble, drawCircle(250, 30, ContextCompat.getColor(getContext(), R.color.colorPrimary),"rec"));
        bubble.setVisibility(View.INVISIBLE);
        bubble.setGravity(Gravity.CENTER);
        bubble.setTextColor(ContextCompat.getColor(getContext(), android.R.color.white));
        bubble.setTextSize(12);
        GradientDrawable gd = new GradientDrawable();
        gd.setCornerRadius(10);
        gd.setColor(ContextCompat.getColor(getContext(), R.color.colorPrimary));
        bubble.setBackground(gd);
        bubble.setTypeface(null, Typeface.BOLD);
        getScroller().addScrollerListener(new RecyclerViewScrollListener.ScrollerListener() {
            @Override
            public void onScroll(float relativePos) {
            }
        });
        return bubble;
    }

    @Override
    public TextView provideBubbleTextView() {
        return bubble;
    }

    @Override
    public int getBubbleOffset() {
        return (int) (getScroller().isVertical() ? (float)handle.getHeight()/2f-(float)bubble.getHeight()/2f : (float)handle.getWidth()/2f-(float)bubble.getWidth()/2);
    }

    @Override
    protected ViewBehavior provideHandleBehavior() {
        return new CustomHandleBehavior(
                new VisibilityAnimationManager.Builder(handle)
                        .withHideDelay(2000)
                        .build(),
                new CustomHandleBehavior.HandleAnimationManager.Builder(handle)
                        .withGrabAnimator(R.animator.custom_grab)
                        .withReleaseAnimator(R.animator.custom_release)
                        .build()
        );
    }

    @Override
    protected ViewBehavior provideBubbleBehavior() {
        return new DefaultBubbleBehavior(new VisibilityAnimationManager.Builder(bubble).withHideDelay(0).build());
    }

    private static ShapeDrawable drawCircle (int width, int height, int color,String shape) {
        ShapeDrawable oval;
        if (shape.equals("rec")) {
            oval = new ShapeDrawable(new RectShape());
        }
        else {
            oval = new ShapeDrawable(new OvalShape());
        }
        oval.setIntrinsicHeight(height);
        oval.setIntrinsicWidth(width);
        oval.getPaint().setColor(color);
        return oval;
    }

}