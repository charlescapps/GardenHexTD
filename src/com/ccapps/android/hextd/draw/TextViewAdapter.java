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
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import com.ccapps.android.hextd.R;

public class TextViewAdapter extends BaseAdapter {
    private Context mContext;

    public TextViewAdapter(Context c) {
        mContext = c;
    }

    public int getCount() {
        return mThumbIds.length;
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

            Drawable drawable = mContext.getResources().getDrawable(mThumbIds[position]);
            drawable.setBounds(new Rect(0,0,70,70));
            textView.setCompoundDrawables(null, null, null, drawable);
            textView.setBackgroundColor(Color.YELLOW);
            textView.setBackgroundResource(R.drawable.grid_background);


        } else {
            textView = (TextView) convertView;
        }

        textView.setText("Test");
        return textView;
    }

    // references to our images
    private Integer[] mThumbIds = {
            R.drawable.sunflowericon_menu,
            R.drawable.carnivorousplant_menu,
            R.drawable.eggplant_menu,
            R.drawable.flower_menu
    };
}
