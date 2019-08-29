package com.luluoverflow.tflite;

class Config {
    /**
     * inputShape[0] is number of batch.
     * inputShape[1] is number of picture channels.图片的通道数
     * inputShape[2] & inputShape[3] is number of crop.
     */
    static int channelsGray = 1;
    static int channlesRGB = 3;
    static int[] inputShape = {1, channlesRGB, 224, 224};

    private static final String[] PADDLE_MODEL= {
            "mobilenet_v1_1.0_224",
            "mobilenet_v1_1.0_224_quant"
    };
    /**
     * 选择要测试的模型
     */
    static final String MODEL = PADDLE_MODEL[0];
    /**
     * result:若是非量化模型 数据类型为float  若是量化模型 数据类型为byte 相应的也要在MainActivity中更改数据类型
     * numBytesPerChannel:若是非量化模型 值为1 若是量化模型 值为4
     */
    static boolean quantized = false;
    static float[][] result = new float[1][1001];
    static int numBytesPerChannel = 4;

//    static boolean quantized = true;
//    static byte[][] result = new byte[1][1001];
//    static int numBytesPerChannel = 1;

}
