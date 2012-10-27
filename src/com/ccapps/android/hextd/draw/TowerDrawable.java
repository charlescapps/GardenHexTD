package com.ccapps.android.hextd.draw;

import android.content.Context;
import android.graphics.*;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import com.ccapps.android.hextd.gamedata.Tower;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created with IntelliJ IDEA.
 * User: charles
 * Date: 9/30/12
 * Time: 12:50 PM
 * To change this template use File | Settings | File Templates.
 */
public class TowerDrawable extends Drawable {

    protected final Tower tower;
    protected final Bitmap towerImg;
    protected final PointF imgTopLeft;

    public TowerDrawable(Tower tower, Bitmap towerImg) {
        super();
        this.tower = tower;
        this.towerImg = towerImg;
        PointF center = tower.getHex().getCenter();
        this.imgTopLeft = new PointF(
                center.x - towerImg.getWidth() / 2.f,
                center.y - towerImg.getHeight() / 2.f
        );

    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawBitmap(towerImg, imgTopLeft.x + HexGrid.GLOBAL_OFFSET.x, imgTopLeft.y + HexGrid.GLOBAL_OFFSET.y, null);
    }

    @Override
    public void setAlpha(int i) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void setColorFilter(ColorFilter colorFilter) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public int getOpacity() {
        return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public void incrementAttackAnimation() {

    }

    public Bitmap getTowerImg() {
        return towerImg;
    }
}
