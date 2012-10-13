package com.ccapps.android.hextd.views;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import com.ccapps.android.hextd.R;
import com.ccapps.android.hextd.gamedata.Tower;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: charles
 * Date: 10/13/12
 * Time: 12:10 PM
 * To change this template use File | Settings | File Templates.
 */
public class TowerMenuView extends TableLayout {
    private TableRow row1;
    private TableRow row2;
    private int numPerRow;
    private List<Integer> drawableIds;
    private Context context;
    private int imageSize;
    private int padding;
    List<Class<? extends Tower>> towerClasses;

    public TowerMenuView(Context context) {
        super(context);
        this.context = context;

    }

    public TowerMenuView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
    }

    public void init(List<Integer> drawableIds, List<Class<? extends Tower>> towerClasses, int numPerRow,
                     int imageSize, int padding) {
        this.drawableIds = drawableIds;
        this.towerClasses = towerClasses;
        this.numPerRow = numPerRow;
        this.imageSize = imageSize;
        this.padding = padding;
        this.row1 = (TableRow)getChildAt(0);
        this.row2 = (TableRow)getChildAt(1);

        for (int i = 0; i < drawableIds.size(); i++) {
            Integer thumb = drawableIds.get(i);
            TableRow currentRow = i / numPerRow == 0 ? row1 : row2;
            TextView textView = new TextView(context);

            TableRow.LayoutParams lp = new TableRow.LayoutParams(imageSize + 2*padding, imageSize + 2*padding);
            lp.setMargins(0, 0, padding, 0);
            textView.setLayoutParams(lp);
            textView.setPadding(padding, padding, padding, padding);


            Drawable drawable = this.getResources().getDrawable(thumb);
            drawable.setBounds(new Rect(0, 0, imageSize, imageSize));
            textView.setCompoundDrawables(null, drawable, null, null);
            textView.setBackgroundColor(Color.YELLOW);
            textView.setBackgroundResource(R.drawable.grid_background);

            textView.setOnClickListener(new OnClickTower());

            currentRow.addView(textView);
        }
        invalidate();
    }

    private class OnClickTower implements OnClickListener {
        public OnClickTower() {

        }

        @Override
        public void onClick(View view) {
            final TextView textView = (TextView) view;
            textView.setBackgroundResource(R.drawable.grid_background_touched);
            TowerMenuView.this.invalidate();

            postDelayed(new Runnable() {
                @Override
                public void run() {
                    textView.setBackgroundResource(R.drawable.grid_background);
                    TowerMenuView.this.invalidate();
                }
            }, 500);

        }
    }
}
