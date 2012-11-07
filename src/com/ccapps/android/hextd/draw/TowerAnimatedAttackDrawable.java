package com.ccapps.android.hextd.draw;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.PointF;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import com.ccapps.android.hextd.gamedata.Tower;

import java.util.List;

/*****************************************************
 Garden Hex Tower Defense
 Charles Capps & Joseph Lee
 ID:  920474106
 CS 313 AI and Game Design
 Fall 2012
 *****************************************************/
public class TowerAnimatedAttackDrawable extends TowerDrawable {

    private final List<Bitmap> attackAnimation;
    private int animationBeat;
    private final int xOffset;
    private final int yOffset;
    private final int attackAnimationSize;

    public TowerAnimatedAttackDrawable(Tower tower, Bitmap towerImg, List<Bitmap> attackAnimation) {
        super(tower, towerImg);
        this.attackAnimation = attackAnimation;
        this.animationBeat = 0;
        this.xOffset = towerImg.getWidth()/2;
        this.yOffset = towerImg.getHeight()/2;
        this.attackAnimationSize = attackAnimation.size();
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        for (Hexagon h: tower.getAttackHexes()) {
            if (h.getTower() == null) {
                final PointF center = h.getCenter();
                final int x = (int)(center.x - xOffset + HexGrid.GLOBAL_OFFSET.x);
                final int y = (int)(center.y - yOffset + HexGrid.GLOBAL_OFFSET.y);
                canvas.drawBitmap(attackAnimation.get( animationBeat % attackAnimationSize), x, y, null);
            }
        }
    }

    @Override
    public void incrementAttackAnimation() {
        ++animationBeat;
    }
}
