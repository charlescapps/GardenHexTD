package com.ccapps.android.hextd;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.graphics.PointF;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Display;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import com.ccapps.android.hextd.activities.GameActivity;
import com.ccapps.android.hextd.draw.HexGrid;
import com.ccapps.android.hextd.draw.Hexagon;
import com.ccapps.android.hextd.gamedata.StaticData;

public class MainActivity extends Activity {

    public static final int NUM_VERTICAL_HEXES = 16;                  //test values for now...
    public static final int NUM_HORIZONTAL_HEXES = 10;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setupHexGrid();

        setupStartGameButton();


    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
    
    public void setupStartGameButton() {

        View button = findViewById(R.id.startGameButton);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, GameActivity.class);
                MainActivity.this.startActivity(intent);

            }
        });

    }

    @Override
    public void onPause() {
        super.onPause();


    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onWindowFocusChanged(boolean changed) {
        super.onWindowFocusChanged(changed);


    }

    private void setupHexGrid() {

        HexGrid.reset();
        Display defaultDisplay = getWindowManager().getDefaultDisplay();
        StaticData.DEFAULT_SCREEN_SIZE = defaultDisplay;
        Point screenSize = new Point(defaultDisplay.getWidth(), defaultDisplay.getHeight());

        float gridWidth =  (float)(screenSize.x - 2*HexGrid.MARGIN.x);

        /**
         * Calculate the side of a hexagon, a and the height from the center to top, h
         */
        float a = gridWidth * 1.f / ( (3.f/2.f) * (float)NUM_HORIZONTAL_HEXES + 1.f/2.f);
        float h = a*Hexagon.sqrt3/2.f;

        setupStaticData((int)(a*2.f));


        PointF margin = new PointF(5.f, 5.f);
        HexGrid.initHexGrid(
                new PointF(a + HexGrid.MARGIN.x, 2.f*h + HexGrid.MARGIN.y),
                NUM_HORIZONTAL_HEXES,
                NUM_VERTICAL_HEXES,
                a,
                screenSize,
                margin);

    }

    private void setupStaticData(int a) {
        StaticData.BASIC_TOWER_IMAGE =
                Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.tower), a, a, false) ;

        StaticData.SUNFLOWER =
                Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.sunflower_tower), a, a, false) ;


        StaticData.EGGPLANT =
                Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.eggplant_tower), a, a, false) ;


        StaticData.CARNIVOROUS =
                Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.carnivorous_tower), a, a, false) ;

        StaticData.ROSE =
                Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.rose_tower), a, a, false) ;

        StaticData.ANT =
                Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.ant), a, a, false) ;





    }
    
}
