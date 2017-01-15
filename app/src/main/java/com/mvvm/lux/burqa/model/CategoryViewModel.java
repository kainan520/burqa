package com.mvvm.lux.burqa.model;

import android.app.Activity;
import android.databinding.ObservableBoolean;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.mvvm.lux.burqa.R;
import com.mvvm.lux.burqa.databinding.FragmentCategoryBinding;
import com.mvvm.lux.burqa.http.RetrofitHelper;
import com.mvvm.lux.burqa.model.response.CategoryResponse;
import com.mvvm.lux.framework.base.BaseViewModel;
import com.mvvm.lux.framework.http.RxHelper;
import com.mvvm.lux.framework.http.RxSubscriber;
import com.mvvm.lux.framework.manager.recycler.itemDecoration.AlphaDividerItemDecoration;
import com.mvvm.lux.framework.manager.recycler.recyclerview.CommonAdapter;
import com.mvvm.lux.framework.manager.recycler.recyclerview.base.ViewHolder;
import com.mvvm.lux.widget.emptyview.EmptyView;
import com.mvvm.lux.widget.utils.DisplayUtil;

import java.util.List;

import lib.lhh.fiv.library.FrescoImageView;

/**
 * @Description
 * @Author luxiao418
 * @Email luxiao418@pingan.com.cn
 * @Date 2017/1/5 13:35
 * @Version
 */
public class CategoryViewModel extends BaseViewModel {

    public ObservableBoolean refreshing = new ObservableBoolean(false);
    public ObservableBoolean showEmpty = new ObservableBoolean(false);
    private FragmentCategoryBinding mDataBinding;
    private List<CategoryResponse> mCategoryResponses;

    public CategoryViewModel(Activity activity, FragmentCategoryBinding dataBinding) {
        super(activity);
        mDataBinding = dataBinding;
        refreshing.set(true);
        initData();
    }

    public RecyclerView.ItemDecoration itemDecoration = new AlphaDividerItemDecoration(DisplayUtil.dp2px(mActivity, 5));

    public RecyclerView.LayoutManager getLayoutManager() {
        GridLayoutManager layoutManager = new GridLayoutManager(mActivity, 6);
        layoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                return 2;
            }
        });
        return layoutManager;
    }

    public EmptyView.ReloadOnClickListener mReloadOnClickListener = this::initData;

    public SwipeRefreshLayout.OnRefreshListener onRefreshListener() {
        return this::initData;  // refreshing.set(true); 双向绑定之后,刷新之后就不用设置为true了
    }

    public void initData() {
        RetrofitHelper.init()
                .getCategory()
                .compose(RxHelper.io_main())
                .subscribe(new RxSubscriber<List<CategoryResponse>>() {

                    @Override
                    public void onNext(List<CategoryResponse> categoryResponses) {
                        mCategoryResponses = categoryResponses;
                        refreshing.set(false);
                        mDataBinding.recyclerView.setAdapter(new CommonAdapter<CategoryResponse>(mActivity, R.layout.adapter_category_layout, mCategoryResponses) {
                            @Override
                            protected void convert(ViewHolder holder, CategoryResponse categoryResponse, int position) {
                                holder.setText(R.id.category_tv, categoryResponse.getTitle());
                                FrescoImageView frescoImageView = holder.getView(R.id.category_iv);
                                frescoImageView.asCircle();
                                frescoImageView.loadView(categoryResponse.getCover(), R.drawable.default_bg);
                            }
                        });
                    }
                });
    }
}
