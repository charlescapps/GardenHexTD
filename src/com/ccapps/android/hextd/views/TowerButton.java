package com.ccapps.android.hextd.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.widget.ImageButton;
import com.ccapps.android.hextd.gamedata.Tower;

/**
 * Created with IntelliJ IDEA.
 * User: charles
 * Date: 10/7/12
 * Time: 3:48 PM
 * To change this template use File | Settings | File Templates.
 */
public class TowerButton extends ImageButton{

    private Class<? extends Tower> towerClass;

    public Class<? extends Tower> getTowerClass() {
        return towerClass;
    }

    public void setTowerClass(Class towerClass) {
        this.towerClass = towerClass;
    }

    public TowerButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TowerButton(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public TowerButton(Context context) {
        super(context);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent keyEvent) {

        if (keyEvent.getAction() == KeyEvent.ACTION_DOWN) {
            return true;
        }

        return false;
    }
}
