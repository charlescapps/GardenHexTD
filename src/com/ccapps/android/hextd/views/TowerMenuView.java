package com.ccapps.android.hextd.views;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import com.ccapps.android.hextd.R;
import com.ccapps.android.hextd.draw.HexGrid;
import com.ccapps.android.hextd.draw.Hexagon;
import com.ccapps.android.hextd.gamedata.StaticData;
import com.ccapps.android.hextd.gamedata.Tower;
import com.ccapps.android.hextd.gamedata.TowerUtils;

import java.util.ArrayList;
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
    private Point lastClickedHex;
    private List<Runnable> currentDelayEvents;
    private float yOffset;
    private float permanentHeight;

    public TowerMenuView(Context context) {
        super(context);
        this.context = context;
        this.currentDelayEvents = new ArrayList<Runnable>();
    }

    public TowerMenuView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        this.currentDelayEvents = new ArrayList<Runnable>();
        this.yOffset = 0.f;
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
        this.permanentHeight = 2*(imageSize+2*padding) + (float)imageSize*0.2f;

        for (int i = 0; i < drawableIds.size(); i++) {
            Integer thumb = drawableIds.get(i);
            TableRow currentRow = i / numPerRow == 0 ? row1 : row2;
            TextView textView = new TextView(context);

            int height = (int)((float)(imageSize + 2*padding)*1.25f);
            int textSize = (int)((float)height*0.2f);
            TableRow.LayoutParams lp = new TableRow.LayoutParams(imageSize + 2*padding, height + 2*padding);
            lp.setMargins(0, 0, padding, 0);
            textView.setLayoutParams(lp);
            textView.setPadding(padding, padding, padding, padding);

            Drawable drawable = this.getResources().getDrawable(thumb);
            drawable.setAlpha(180);
            drawable.setBounds(new Rect(0, 0, imageSize, imageSize));

            textView.setCompoundDrawables(null, drawable, null, null);
            textView.setBackgroundColor(Color.YELLOW);
            textView.setBackgroundResource(R.drawable.grid_background);

            textView.setOnClickListener(new OnClickTower(towerClasses.get(i)));
            int towerCost = StaticData.TOWER_COSTS.get(towerClasses.get(i));
            textView.setText("$" + towerCost);
            textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
            textView.setTextColor(Color.WHITE);
            textView.setGravity(Gravity.CENTER_HORIZONTAL);
            textView.scrollTo(0,0);

            currentRow.addView(textView);
        }
        invalidate();
    }

    public float getPermanentHeight() {
        return permanentHeight;
    }

    public int getYOffset() {
        return (int)yOffset;
    }

    public void shiftYOffset(float pix) {
        this.yOffset += pix;
    }

    public void setLastClickedHex(Point p) {
        this.lastClickedHex = p;
    }

    @Override
    public void onVisibilityChanged(View v, int visibility) {
        super.onVisibilityChanged(v, visibility);

        this.currentDelayEvents.clear();
        this.yOffset = 0.f;
        HexGrid.getInstance().clearSelectedHexagon();
    }

    public void clearDelayedEvents() {
        this.currentDelayEvents.clear();
    }

    public Runnable addDelayedEvent(Runnable r) {
        this.currentDelayEvents.add(r);
        return r;
    }

    public boolean containsEvent(Runnable runnable) {
        for (Runnable r: currentDelayEvents) {
            if (r == runnable) {
                return true;
            }
        }
        return false;
    }

    private class OnClickTower implements OnClickListener {
        private Class<? extends Tower> towerClass;
        public OnClickTower(Class<? extends Tower> towerClass) {
            this.towerClass = towerClass;
        }

        @Override
        public void onClick(View view) {
            final TextView textView = (TextView) view;
            final TowerMenuView towerMenu = TowerMenuView.this;
            TowerUtils.addTower(towerClass, lastClickedHex.x, lastClickedHex.y);

            textView.setBackgroundResource(R.drawable.grid_background_touched);
            towerMenu.invalidate();

            postDelayed(new Runnable() {
                @Override
                public void run() {
                    textView.setBackgroundResource(R.drawable.grid_background);
                    towerMenu.invalidate();
                }
            }, 150);

            postDelayed(new Runnable() {
                @Override
                public void run() {
                    towerMenu.setVisibility(View.GONE);
                    towerMenu.invalidate();
                }
            }, 300);

        }

    }
}
