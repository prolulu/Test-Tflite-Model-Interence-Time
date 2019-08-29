package com.luluoverflow.tflite;

import androidx.appcompat.app.AppCompatActivity;
import android.content.res.AssetFileDescriptor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.tensorflow.lite.Interpreter;

import java.io.FileInputStream;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getName();

    private ImageView imageView;
    private TextView textInferTime;


    private Interpreter tflite = null;
    private static int[] inputShape = Config.inputShape;
    private static float[][] result = Config.result;
    private static boolean quantized = Config.quantized;
    private static int numBytesPerChannel = Config.numBytesPerChannel;
    private static final String MODEL = Config.MODEL;
    private static int channels = Config.channlesRGB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageView = findViewById(R.id.imageview);
        textInferTime = findViewById(R.id.infer_info);

        TextView textModel = findViewById(R.id.model_info);
        Button button = findViewById(R.id.choose);
        TextView textCrop = findViewById(R.id.crop_info);

        String crop = inputShape[2]+"*"+inputShape[3];
        textCrop.setText(crop);
        textModel.setText(MODEL);

        load_model(MODEL); //加载模型

        button.setOnClickListener((View v) -> {
                    long time = Run();
                    Log.d(TAG,"Interpreter Time:"+time);
                    String output = time + "ms";
                    textInferTime.setText(output);
                });

    }

    private MappedByteBuffer loadModelFile(String model) throws IOException {
        AssetFileDescriptor fileDescriptor = getApplicationContext().getAssets().openFd(model + ".tflite");
        FileInputStream inputStream = new FileInputStream(fileDescriptor.getFileDescriptor());
        FileChannel fileChannel = inputStream.getChannel();
        long startOffset = fileDescriptor.getStartOffset();
        long declaredLength = fileDescriptor.getDeclaredLength();
        return fileChannel.map(FileChannel.MapMode.READ_ONLY, startOffset, declaredLength);
    }

    // load infer model
    private void load_model(String model) {
        try {

            tflite = new Interpreter(loadModelFile(model));
            Toast.makeText(MainActivity.this, model + " model load success", Toast.LENGTH_SHORT).show();
            Log.d(TAG, model + " model load success");
            tflite.setNumThreads(4);

        } catch (IOException e) {
            Toast.makeText(MainActivity.this, model + " model load fail", Toast.LENGTH_SHORT).show();
            Log.d(TAG, model + " model load fail");
            e.printStackTrace();
        }
    }

    private long Run() {
        Bitmap bitmap = BitmapFactory.decodeResource(this.getApplicationContext().getResources(), R.drawable.test);
        imageView.setImageBitmap(bitmap);

        ByteBuffer inputData = PhotoUtil.getScaledMatrix(bitmap, inputShape, quantized,numBytesPerChannel,channels);

        long start = System.currentTimeMillis();
        tflite.run(inputData, result);
        long end = System.currentTimeMillis();
        return end - start;
    }

}

