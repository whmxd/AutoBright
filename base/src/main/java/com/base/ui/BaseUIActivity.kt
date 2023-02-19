package com.base.ui;

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import androidx.annotation.IdRes
import androidx.annotation.LayoutRes
import androidx.core.view.LayoutInflaterCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.base.util.ToastUtil
import com.base.widget.LoadingDialog

/**
 * 这里做一些公用的方法
 */
abstract class BaseUIActivity : BaseActivity(), View.OnClickListener{

    val dialog by lazy {
        LoadingDialog(this)
    }
    val mContext by lazy {
        applicationContext
    }

    override fun onCreate(savedInstanceState: Bundle?) {
//        debugViewTime()
        super.onCreate(savedInstanceState)
        if (!initRoot())
            return
        if (getContentView() == null) {
            setContentView(getLayoutId())
        } else {
            setContentView(getContentView())
        }
        initState(savedInstanceState)
        initView()
        initData()
    }

    /************老版防重复点击**************/
//    两次点击按钮之间的点击间隔不能少于500毫秒
    private val minControlTime = 500
    private var lastClickTime: Long = 0
    override fun onClick(v: View) {
        val curClickTime = System.currentTimeMillis()
        if (curClickTime - lastClickTime >= minControlTime) {
            // 超过点击间隔后再将lastClickTime重置为当前点击时间
            lastClickTime = curClickTime
            onMultiClick(v)
        }
    }

    /**
     * 查看UI渲染时间
     */
    private fun debugViewTime() {
        LayoutInflaterCompat.setFactory(
            LayoutInflater.from(this)
        ) { parent, name, context, attrs -> //获取指定类型控件的方法
            //                if(!TextUtils.isEmpty(name) && name.equals("Button")){
            //                    Button button = new Button(context, attrs);
            //                    return button;
            //                }
            //替换成自己的控件方法
            //                if(!TextUtils.isEmpty(name) && name.equals("TextView")){
            //                    Button button = new Button(context, attrs);
            //                    return button;
            //                }
            val start = System.currentTimeMillis()
            val delegate = delegate
            val view = delegate.createView(parent, name, context, attrs)
            Log.i(
                "BaseUi",
                "-----控件------->>>:" + name + " 绘制耗时>>>> " + (System.currentTimeMillis() - start)
            )
            view
        }
    }

    /**
     * TODO：一定要注意、注意，这里使用此方法，一定要注意这里如果返回false,
     *  生命周期从onCreate是没有执行的，那我们这里写的onDestroy，就一定要防止空指针处理
     *  这里做一些在OnCreate生命周期之前的事情
     */
    open fun initRoot(): Boolean {
        return true
    }

    /**
     * 基于项目的ViewBinding封装
     */
    open fun getContentView(): View? {
        return null
    }

    /**
     * 基于布局ID直接使用
     */
    @LayoutRes
    open fun getLayoutId(): Int {
        return -1
    }

    /**
     * 界面被回收时，再次显示，从这里取保存的数据
     * 异常状态恢复
     */
    open fun initState(savedInstanceState: Bundle?) {

    }

    /**
     * 布局初始化
     */
    open fun initView() {
        hideSoftInput()
    }

    /**
     * 数据初始化
     */
    open fun initData() {}

    open fun onMultiClick(v: View) {}

    inline fun <reified T> startActivity() {
        val intent = Intent(this, T::class.java)
        startActivity(intent)
    }

    inline fun <reified T> startActivity(bundle: Bundle) {
        val intent = Intent(this, T::class.java)
        intent.putExtras(bundle)
        startActivity(intent)
    }

    inline fun <reified T> startActivityResult(resultCode: Int) {
        val intent = Intent(this, T::class.java)
        startActivityForResult(intent, resultCode)
    }

    inline fun <reified T> startActivityResult(bundle: Bundle, resultCode: Int) {
        val intent = Intent(this, T::class.java)
        intent.putExtras(bundle)
        startActivityForResult(intent, resultCode)
    }

    /**
     * 跳转页面
     *
     * @param pClass 跳转的页面
     * @param result 为请求码requestCode
     */
    protected fun startActivityResult(pClass: Class<*>, result: Int) {
        val intent = Intent(this, pClass)
        startActivityForResult(intent, result)
    }

    /**
     * 跳转页面
     *
     * @param pClass  跳转的页面
     * @param pBundle 传递过去的参数
     * @param result  为请求码requestCode
     */
    protected fun startActivityResult(pClass: Class<*>, pBundle: Bundle, result: Int) {
        val intent = Intent(this, pClass)
        intent.putExtras(pBundle)
        startActivityForResult(intent, result)
        //关闭跳转动画
//        overridePendingTransition(0,0)
    }

    /**************Toast**************/
    protected fun toastShow(str: CharSequence) {
        ToastUtil.showShort(this, str)
    }

    /**
     * 此方法限定为FragmentTransaction事务提交方式控制
     *
     * @param viewId    为fragment添加到view当中的控件id，如R.id.xx
     * @param fragments 所有需要进行显示的fragments
     * @param position  显示第几个Fragment
     */
    protected fun initFragment(@IdRes viewId: Int, fragments: List<Fragment>?, position: Int) {
        val fragmentManager = supportFragmentManager
        val transaction = fragmentManager.beginTransaction()
        //针对于如果出现BUG，有可能会引起的Fragment重叠。
        for (fragment in fragmentManager.fragments) {
            transaction.remove(fragment)
        }
        if (fragments != null) {
            for (i in fragments.indices) {
                //这里做了布局初始化，并且全部隐藏，原因是需要触发onHiddenChanged()方法。
                if (!fragments[i].isAdded)
                    transaction.add(viewId, fragments[i]).hide(fragments[i])
                //这里做显示
                if (i == position) {
                    transaction.show(fragments[i])
                }
            }
        }
        transaction.commitAllowingStateLoss()
    }

    /**
     * 此方法限定为FragmentTransaction事务提交方式控制
     *
     * @param position 控制fragment显示和隐藏
     */
    protected fun showFragment(position: Int) {
        for ((i, fragment) in supportFragmentManager.fragments.withIndex()) {
            val transaction = supportFragmentManager.beginTransaction()
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
            if (position == i) {
                transaction.show(fragment).commitAllowingStateLoss()
            } else {
                transaction.hide(fragment).commitAllowingStateLoss()
            }
        }
    }

}