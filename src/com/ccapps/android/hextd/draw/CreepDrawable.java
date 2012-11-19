package com.ccapps.android.hextd.draw;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.PointF;
import android.graphics.drawable.Drawable;
import com.ccapps.android.hextd.gamedata.Creep;

/*****************************************************
 Garden Hex Tower Defense
 Charles Capps & Joseph Lee
 ID:  920474106
 CS 313 AI and Game Design
 Fall 2012
 *****************************************************/

//CLC: Original Code Begin
public class CreepDrawable extends Drawable {

    private Creep creep;
    private Bitmap creepImg;
    private Bitmap deadCreepImg;
    private PointF imgTopLeft;

    public CreepDrawable(Creep creep, Bitmap creepImg, Bitmap deadCreepImg) {
        super();
        this.creep = creep;
        this.creepImg = creepImg;
        this.deadCreepImg = deadCreepImg;
        PointF center = creep.getHex().getCenter();
        this.imgTopLeft = new PointF(
                center.x - creepImg.getWidth() / 2.f,
                center.y - creepImg.getHeight() / 2.f
        );

    }

    @Override
    public void draw(Canvas canvas) {
        if (creep.isDead()) {
            canvas.drawBitmap(deadCreepImg, imgTopLeft.x + HexGrid.GLOBAL_OFFSET.x, imgTopLeft.y + HexGrid.GLOBAL_OFFSET.y, null);
        } else {
            canvas.drawBitmap(creepImg, imgTopLeft.x + HexGrid.GLOBAL_OFFSET.x, imgTopLeft.y + HexGrid.GLOBAL_OFFSET.y, null);
        }
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

    /**
     * Call this after the underlying creep moved and needs to be drawn somewhere else
     */
    public void updateLocation() {
        PointF center = creep.getHex().getCenter();
        this.imgTopLeft = new PointF(
                center.x - creepImg.getWidth() / 2.f,
                center.y - creepImg.getHeight() / 2.f
        );
    }
}
//CLC: Original Code End
