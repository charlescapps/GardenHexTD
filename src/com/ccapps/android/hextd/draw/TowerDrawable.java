package com.ccapps.android.hextd.draw;

import android.graphics.*;
import android.graphics.drawable.Drawable;
import com.ccapps.android.hextd.gamedata.Tower;

/**
 * Created with IntelliJ IDEA.
 * User: charles
 * Date: 9/30/12
 * Time: 12:50 PM
 * To change this template use File | Settings | File Templates.
 */
public class TowerDrawable extends Drawable {

    private Tower tower;
    private Bitmap towerImg;
    private PointF imgTopLeft;

    public TowerDrawable(Tower tower, Bitmap towerImg) {
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
        canvas.drawBitmap(towerImg, imgTopLeft.x + Hexagon.globalOffset.x, imgTopLeft.y + Hexagon.globalOffset.y, null);
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
}
