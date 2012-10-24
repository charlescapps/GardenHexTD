package com.ccapps.android.hextd.draw;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.PointF;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import com.ccapps.android.hextd.gamedata.Tower;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: charles
 * Date: 9/30/12
 * Time: 12:50 PM
 * To change this template use File | Settings | File Templates.
 */
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
            final PointF center = h.getCenter();
            final int x = (int)(center.x - xOffset + HexGrid.GLOBAL_OFFSET.x);
            final int y = (int)(center.y - yOffset + HexGrid.GLOBAL_OFFSET.y);
            canvas.drawBitmap(attackAnimation.get( animationBeat % attackAnimationSize), x, y, null);
        }
    }

    @Override
    public void incrementAttackAnimation() {
        ++animationBeat;
    }
}
