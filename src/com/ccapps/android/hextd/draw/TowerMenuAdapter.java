package com.ccapps.android.hextd.draw;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.ccapps.android.hextd.R;
import com.ccapps.android.hextd.gamedata.BasicTower;
import com.ccapps.android.hextd.gamedata.Tower;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class TowerMenuAdapter extends BaseAdapter {
    private Context mContext;
    private List<Integer> mThumbIds;
    private List<Class<? extends Tower>> mTowerClasses;

    public TowerMenuAdapter(Context c) {
        mContext = c;
        mThumbIds = Arrays.asList(new Integer[] {
                R.drawable.sunflowericon_menu,
                R.drawable.carnivorousplant_menu,
                R.drawable.eggplant_menu,
                R.drawable.flower_menu
        });

        mTowerClasses = new ArrayList<Class<? extends Tower>>();
        mTowerClasses.add(BasicTower.class);
        mTowerClasses.add(BasicTower.class);
        mTowerClasses.add(BasicTower.class);
        mTowerClasses.add(BasicTower.class);
    }

    public int getCount() {
        return mThumbIds.size();
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }

    // create a new ImageView for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView textView;
        if (convertView == null) {  // if it's not recycled, initialize some attributes
            textView = new TextView(mContext);

            textView.setLayoutParams(new GridView.LayoutParams(70, 70));
            textView.setPadding(0, 0, 0, 0);

            Drawable drawable = mContext.getResources().getDrawable(mThumbIds.get(position));
            drawable.setBounds(new Rect(0, 0, 70, 70));
            textView.setCompoundDrawables(null, null, null, drawable);
            textView.setBackgroundColor(Color.YELLOW);
            textView.setBackgroundResource(R.drawable.grid_background);


        } else {
            textView = (TextView) convertView;
        }

        textView.setText("Test");
        return textView;
    }


}
