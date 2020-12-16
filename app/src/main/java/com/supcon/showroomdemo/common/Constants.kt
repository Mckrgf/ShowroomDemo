package com.supcon.showroomdemo.common

object Constants {
    const val APP_ID = "72kkCvftMbEhBeeVH5zxuiaJKTMHf5sNPLS19g9xEMgv"
    const val SDK_KEY = "GE69LzPwk86o4PU9bHYMb3QXQydjmCxyGX2mvvYBXmbN"

    /**
     * IR预览数据相对于RGB预览数据的横向偏移量，注意：是预览数据，一般的摄像头的预览数据都是 width > height
     */
    const val HORIZONTAL_OFFSET = 0

    /**
     * IR预览数据相对于RGB预览数据的纵向偏移量，注意：是预览数据，一般的摄像头的预览数据都是 width > height
     */
    const val VERTICAL_OFFSET = 0

    const val INTENT_KEY_USER = "INTENT_KEY_USER"


    const val IP_KEY = "IP_KEY"
    const val DEFAULT_IP = "192.168.90.49"
    const val PORT_KEY = "PORT_KEY"
    const val DEFAULT_PORT = "8080"

    //人脸识别相关参数
    const val MAX_DETECT_NUM: Int = 10

    /**
     * 当FR成功，活体未成功时，FR等待活体的时间
     */
     const val WAIT_LIVENESS_INTERVAL = 100

    /**
     * 失败重试间隔时间（ms）
     */
     const val FAIL_RETRY_INTERVAL: Long = 1000

    /**
     * 出错重试最大次数
     */
     const val MAX_RETRY_TIME = 3

    /**
     * 注册人脸状态码，准备注册
     */
     const val REGISTER_STATUS_READY = 0

    /**
     * 注册人脸状态码，注册中
     */
     const val REGISTER_STATUS_PROCESSING = 1

    /**
     * 注册人脸状态码，注册结束（无论成功失败）
     */
     const val REGISTER_STATUS_DONE = 2

    /**
     * 识别阈值
     */
     const val SIMILAR_THRESHOLD = 0.8f
}