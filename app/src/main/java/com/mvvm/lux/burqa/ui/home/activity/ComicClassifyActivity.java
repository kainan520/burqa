package com.mvvm.lux.burqa.ui.home.activity;

import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.text.TextUtils;

import com.github.mzule.activityrouter.annotation.Router;
import com.github.mzule.activityrouter.router.Routers;
import com.mvvm.lux.burqa.R;
import com.mvvm.lux.burqa.databinding.ActivityComicClassifyBinding;
import com.mvvm.lux.burqa.model.ClassifyViewModel;
import com.mvvm.lux.framework.base.SwipeBackActivity;
import com.mvvm.lux.widget.swipeback.SwipeBackLayout;

/**
 * @Description
 * @Author luxiao418
 * @Email luxiao418@pingan.com.cn
 * @Date 2017/1/16 10:13
 * @Version n
 */
@Router("classify/:tag_id/:title")  //tag_id和title为跳转传递的参数名,传递参数第一种
public class ComicClassifyActivity extends SwipeBackActivity {

    private ActivityComicClassifyBinding mDataBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDataBinding = DataBindingUtil.setContentView(this, R.layout.activity_comic_classify);
        initData();
        setEdge(SwipeBackLayout.EDGE_ALL);
    }

    private void initData() {
        String tag_id = getIntent().getExtras().getString("tag_id");
        String title = getIntent().getExtras().getString("title");
        ClassifyViewModel mViewModel = new ClassifyViewModel(this);
        if (TextUtils.isEmpty(tag_id))
            mViewModel.downloadImg.set(false);
        mViewModel.tag_id.set(tag_id);
        mViewModel.title.set(title);
        mViewModel.initData(false);
        mViewModel.initView();  //里面的加载更多监听会优先执行initData()
        mDataBinding.setViewModel(mViewModel);
    }

    public static void launch(Activity activity, String tag_id, String title) {
        Routers.open(activity, "lux://classify/" + tag_id + "/" + title);
    }
}
