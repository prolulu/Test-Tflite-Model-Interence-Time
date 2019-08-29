package com.luluoverflow.tflite;

import android.graphics.Bitmap;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

class PhotoUtil {
    private static final float IMAGE_MEAN = 127.5f;
    private static final float IMAGE_STD = 127.5f;


    // TensorFlow model，get predict data
    static ByteBuffer getScaledMatrix(Bitmap bitmap, int[] dims, boolean quantized, int numBytesPerChannel,int channels) {
        ByteBuffer imgData = ByteBuffer.allocateDirect(dims[0] * dims[1] * dims[2] * dims[3] * numBytesPerChannel);
        imgData.order(ByteOrder.nativeOrder());

        // get image pixel
        int[] pixels = new int[dims[2] * dims[3]];
        Bitmap bm = Bitmap.createScaledBitmap(bitmap, dims[2], dims[3], false);
        bm.getPixels(pixels, 0, bm.getWidth(), 0, 0, dims[2], dims[3]);
        int pixel = 0;
        //标准化
        for (int i = 0; i < dims[2]; ++i) {
            for (int j = 0; j < dims[3]; ++j) {
                final int val = pixels[pixel++];
                if(quantized&&channels==3){//量化RGB quantized RGB
                    imgData.put((byte) ((val >> 16) & 0xFF));
                    imgData.put((byte) ((val >> 8) & 0xFF));
                    imgData.put((byte) (val & 0xFF));
                }else if (quantized&&channels==1){//量化灰度 quantized Gray
                    imgData.put((byte) (val & 0xFF));
                }else if (!quantized&&channels==3){//非量化RGB non-quantized RGB
                    imgData.putFloat(((((val >> 16) & 0xFF) - IMAGE_MEAN) / IMAGE_STD));
                    imgData.putFloat(((((val >> 8) & 0xFF) - IMAGE_MEAN) / IMAGE_STD));
                    imgData.putFloat((((val & 0xFF) - IMAGE_MEAN) / IMAGE_STD));
                }else if(!quantized&&channels==1){//非量化灰度 non-quantized Gray
                    imgData.putFloat((((val & 0xFF) - IMAGE_MEAN) / IMAGE_STD));
                }

            }
        }

        if (bm.isRecycled()) {
            bm.recycle();
        }
        return imgData;
    }


}
