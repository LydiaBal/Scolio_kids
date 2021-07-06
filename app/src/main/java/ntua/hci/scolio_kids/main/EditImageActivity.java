package ntua.hci.scolio_kids.main;

import android.annotation.SuppressLint;
import android.content.AsyncQueryHandler;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.content.AsyncTaskLoader;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import ntua.hci.scolio_kids.MainActivity;
import ntua.hci.scolio_kids.R;

public class EditImageActivity extends AppCompatActivity {

    ImageView imageViewStroke;
    ImageView imagePreview;
    ImageView imageView;

    //Bitmap b;
    final Bitmap[] b = new Bitmap[1];

    List<Line> lines = new ArrayList<Line>();

    PointF startPoint;
    PointF endPoint;

    Bitmap bitmap;
    Canvas canvas;
    Paint paint;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_image);
        initializeElement();
        initializeEvent();

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String uid = user.getUid();

        loadImage();

//        Picasso.with(EditImageActivity.this).load(str_image).into(imageView);

    }

    @SuppressLint("ClickableViewAccessibility")
    private void initializeEvent() {
//        this.btnOpenGallery.setOnClickListener(this.btnOpenGalleryClick);

        this.imageView.setOnTouchListener(new ImageViewOnTouch());

    }

    private void initializeElement() {
        this.imageView = (ImageView) findViewById(R.id.imageView);
        this.imagePreview = (ImageView) findViewById(R.id.image);
        this.imageViewStroke = (ImageView) findViewById(R.id.imageViewStroke);
//        this.btnOpenGallery = (Button) findViewById(R.id.btnOpenGallery);
    }

    private class ImageViewOnTouch implements View.OnTouchListener {

        @Override
        public boolean onTouch(View v, MotionEvent event) {

            if (b[0] == null) return false;
            int action = event.getAction();

            PointF point = new PointF(event.getX(), event.getY());

            float maxWidth = (imageView.getWidth() / 2);
            float maxHeight = (imageView.getHeight() / 2);

            point.x = point.x - maxWidth;
            point.y = point.y - maxHeight;

            point.x = (point.x / maxWidth) * 100;
            point.y = (point.y / maxHeight) * 100;
            Log.i("final point x", String.valueOf(point.x));
            Log.i("final point y", String.valueOf(point.y));

            int width = 100;
            int height = 100;

            PointF imagePoint = new PointF(0, 0);

            float imageMaxWidth = (b[0].getWidth() / 2);
            float imageMaxHeight = (b[0].getHeight() / 2);

            imagePoint.x = imageMaxWidth + (point.x * imageMaxWidth / 100) - (width / 2);
            imagePoint.y = imageMaxHeight + (point.y * imageMaxHeight / 100) - (width / 2);

            if (imagePoint.x <= 0) imagePoint.x = 0;
            if (imagePoint.y <= 0) imagePoint.y = 0;
            if (imagePoint.x + (width) > b[0].getWidth())
                imagePoint.x = b[0].getWidth() - (width);
            if (imagePoint.y + (height) > b[0].getHeight())
                imagePoint.y = b[0].getHeight() - (height);
            Log.i("final image point x", String.valueOf(imagePoint.x));
            Log.i("final image point y", String.valueOf(imagePoint.y));

            PointF thisPoint = new PointF(0, 0);

            float thisMaxWidth = (b[0].getWidth() / 2);
            float thisMaxHeight = (b[0].getHeight() / 2);

            thisPoint.x = thisMaxWidth + (point.x * thisMaxWidth / 100);
            thisPoint.y = thisMaxHeight + (point.y * thisMaxHeight / 100);

            if (thisPoint.x <= 0) thisPoint.x = 0;
            if (thisPoint.y <= 0) thisPoint.y = 0;
            if (thisPoint.x > b[0].getWidth()) thisPoint.x = b[0].getWidth();


            //buat tampilin thumbnail
            Bitmap thumbnail = Bitmap.createBitmap(b[0], (int) imagePoint.x, (int) imagePoint.y, width, height);
            imagePreview.setImageBitmap(thumbnail);

            RadioButton one = findViewById(R.id.one_line);
            RadioButton two = findViewById(R.id.two_lines);

            if (action == MotionEvent.ACTION_DOWN) {
                imagePreview.setVisibility(View.VISIBLE);
                startPoint = new PointF(thisPoint.x, thisPoint.y);
            }

            if (action == MotionEvent.ACTION_MOVE) {
                imagePreview.setVisibility(View.VISIBLE);

                endPoint = new PointF(thisPoint.x, thisPoint.y);

                canvas.drawColor(0, PorterDuff.Mode.CLEAR);
                for (Line line : lines){
                    Log.i("lines", "in");
                    float xs = line.x1;
                    float ys = line.y1;
                    float xe = line.x2;
                    float ye = line.y2;
                    canvas.drawLine(xs, ys, xe, ye, paint);
                }

                canvas.drawLine(startPoint.x, startPoint.y, endPoint.x, endPoint.y, paint);



                imageView.invalidate();
            }

            if (action == MotionEvent.ACTION_UP) {
                Line new_line = new Line(startPoint.x, endPoint.x, startPoint.y, endPoint.y);
                lines.add(new_line);
                TextView angle = findViewById(R.id.angle);

                if (one.isChecked()) {
                    Line xx = new Line(0, 10, 0, 0);
                    double myangle = angleBetween2Lines(lines.get(lines.size() - 1), xx);
                    angle.setText(String.valueOf(myangle));
                }
                else if (two.isChecked() && lines.size()>=2) {
                    double myangle = angleBetween2Lines(lines.get(lines.size() - 1), lines.get(lines.size() - 2));
                    angle.setText(String.valueOf(myangle));
                }

                imagePreview.setVisibility(View.INVISIBLE);
            }

            return true;

        }

    }

    public static double angleBetween2Lines(Line line1, Line line2)
    {
        double angle1 = Math.atan2(line1.y1 - line1.y2,
                line1.x1 - line1.x2);
        double angle2 = Math.atan2(line2.y1 - line2.y2,
                line2.x1 - line2.x2);
        double result = Math.abs(angle1-angle2);
        double finalresult = Math.toDegrees(result);
        if (finalresult >= 180)
            return (Math.toDegrees(result)-180);
        return Math.toDegrees(result);
    }

    private void loadImage() {
        Intent intent = getIntent();

        // receive the value by getStringExtra() method
        // and key must be same which is send by first activity
        String str_image = intent.getStringExtra("image_key");

        final CountDownLatch latch = new CountDownLatch(1);

        Thread thread = new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    URL url = new URL(str_image);
                    Log.i("url!!!!!!!!!", url.toString());
                    b[0] = BitmapFactory.decodeStream(url.openStream());// value[0] = 2;
                    latch.countDown();
                } catch (IOException e) {
                    System.out.println(e);
                }
            }
        });

        thread.start();
        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        imageView.setImageBitmap(b[0]);
        Log.i("bitmap!!!!!!!!!", b[0].toString());
//        InputStream inputStream = getContentResolver().openInputStream(b);
//        Bitmap selectedBitmap = BitmapFactory.decodeStream(input);
//        imageView.setImageBitmap(selectedBitmap);

        //Log.i("bitmap!!!!!!!!!",b.toString());

        Display currentDisplay = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        currentDisplay.getSize(size);
        float dw = b[0].getWidth();
        float dh = b[0].getHeight();
        Log.i("width b0", String.valueOf(dw));
        Log.i("height b0", String.valueOf(dh));
//        float dw = currentDisplay.width();
//        float dh = currentDisplay.height();

        bitmap = Bitmap.createBitmap((int) dw, (int) dh, Bitmap.Config.ARGB_8888);

        canvas = new Canvas(bitmap);

        paint = new Paint();
        paint.setColor(Color.BLUE);
        paint.setStrokeWidth(45);

        imageView.setImageBitmap(b[0]);
        imageViewStroke.setImageBitmap(bitmap);
        Log.i("our bitmap", String.valueOf(b[0]));
        Log.i("bitmap", String.valueOf(bitmap));
    }
}