package com.wutong.autobright

import android.os.Build
import android.service.quicksettings.TileService
import androidx.annotation.RequiresApi
import com.base.util.ToastUtil

/**
 * 服务--标题栏 - 修改自动亮度开关
 */
@RequiresApi(Build.VERSION_CODES.N)
class TileService: TileService() {

    override fun onTileAdded(){//add
    }

    override fun onStartListening(){//可见，状态栏下拉
    }

    override fun onStopListening(){//隐藏，状态栏隐藏
    }

    override fun onClick() {//显示
        Util.checkPermiss(this){
            Util.setAutoBrightStatus(this)
            val msg = if(Util.getAutoBrightStatus(this) == 0) "已关闭自动亮度" else "已开启自动亮度"
            ToastUtil.showLong(this, msg)
        }
    }
}