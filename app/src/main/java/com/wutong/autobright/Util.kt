package com.wutong.autobright

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.Settings

/**
 * 当前项目的工具类
 * 判断是否拥有权限
 * 取反/设置/获取 自动亮度开关状态
 */
object Util {
    /**
     * 判断是否有修改系统设置权限
     */
    fun checkPermiss(context: Context, success: () -> Unit){
        //当前APP的minSdkVersion为24
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (!Settings.System.canWrite(context)) {
                val intent = Intent(
                    Settings.ACTION_MANAGE_WRITE_SETTINGS,
                    Uri.parse("package:com.wutong.autobright")
                )
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                context.startActivity(intent)
            } else {
                success()
            }
//        }
    }

    /**
     * 获取自动亮度开关状态
     */
    fun getAutoBrightStatus(context: Context): Int{
        return Settings.System.getInt(context.contentResolver, Settings.System.SCREEN_BRIGHTNESS_MODE)
    }

    /**
     * 取反 || 设置 自动亮度开关状态
     *
     * @param context 当前上下文
     * @param isAuto 是否开启开关 0关闭/1开启/-1取反，默认-1
     */
    fun setAutoBrightStatus(context: Context, isAuto: Int = -1){
        when(isAuto){
            0, 1 -> {//设置系统亮度模式
                Settings.System.putInt(context.contentResolver, Settings.System.SCREEN_BRIGHTNESS_MODE, isAuto)
            }
            -1 -> {//取反
                val status = if(getAutoBrightStatus(context) == 0) 1 else 0
                Settings.System.putInt(context.contentResolver, Settings.System.SCREEN_BRIGHTNESS_MODE, status)
            }
        }
    }
}