package cn.dj.android.common.lib.ui.widget;

import android.content.Context;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

/**
 * 基础适配器
 *
 * @author : gaodianjie / gaodianjie
 * @version : 1.0
 * @date : 2017-03-13
 */
public abstract class BaseRecyclerViewAdapter<T> extends RecyclerView.Adapter<BaseRecyclerViewHolder> {

    public static final int VIEW_TYPE_BLANK = -1;

    /**
     * Base config
     */
    private List<T> mList;
    private Context mContext;
    private LayoutInflater mInflater;

    public BaseRecyclerViewAdapter(Context context) {
        this(context, null);
    }

    public BaseRecyclerViewAdapter(Context context, List<T> list) {
        mList = null == list ? new ArrayList<T>() : list;
        mContext = context;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public BaseRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return onCreateNormalViewHolder(parent, viewType);
    }

    @Override
    public void onBindViewHolder(BaseRecyclerViewHolder holder, int position) {
        holder.onBindViewHolder(position);
    }

    @Override
    public int getItemCount() {
        return getDataCount();
    }

    protected abstract int getDataCount();

    protected abstract BaseRecyclerViewHolder onCreateNormalViewHolder(ViewGroup parent, int viewType);

    protected Context getContext() {
        return mContext;
    }

    public void refresh(List<T> data) {
        mList = data;
        notifyDataSetChanged();
    }

    public void loadMore(List<T> data) {
        mList.addAll(data);
        notifyDataSetChanged();
    }

    public List<T> getList() {
        return mList;
    }

    public void setList(List<T> list) {
        mList.clear();
        mList.addAll(list);
        notifyDataSetChanged();
    }

    public void setList(T[] list) {
        List<T> arrayList = new ArrayList<T>();
        ;
        for (T t : list) {
            arrayList.add(t);
        }
        setList(arrayList);
    }

    protected T getItem(int position) {
        return mList.get(position);
    }

}
