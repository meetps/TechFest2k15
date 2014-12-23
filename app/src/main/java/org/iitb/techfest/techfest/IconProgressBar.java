package org.iitb.techfest.techfest;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

public class IconProgressBar extends View {
    Bitmap front, back;
    int progress=0;

    public IconProgressBar(Context context, AttributeSet attrs) {
        super(context);

        front = BitmapFactory.decodeResource(context.getResources(),R.drawable.tf_front);
        back = BitmapFactory.decodeResource(context.getResources(),R.drawable.tf_back);
    }

    @Override
    public void onDraw(Canvas canvas){
        try{
            canvas.drawBitmap(
                    Bitmap.createScaledBitmap(
                        Bitmap.createBitmap(back,0,(100-progress)*back.getHeight()/100,back.getWidth(),progress*back.getHeight()/100),
                        canvas.getWidth(),canvas.getHeight()*progress/100,true)
                    ,0,(100-progress)*canvas.getHeight()/100,null);

        } catch (IllegalArgumentException e){

        }

        canvas.drawBitmap(Bitmap.createScaledBitmap(front, canvas.getWidth(), canvas.getHeight(), true), 0, 0, null);
    }

    public void updateProgress(int progress){
        if(progress < this.progress) return;
        this.progress = progress;

        Log.d("ProgressBar", "updateProgress called "+progress);
        invalidate();
    }
}
