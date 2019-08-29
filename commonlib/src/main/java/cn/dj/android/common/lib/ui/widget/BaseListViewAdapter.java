package cn.dj.android.common.lib.ui.widget;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;


/**
 * ListView基础适配器
 *
 * @author : gaodianjie / gaodianjie
 * @version : 1.0
 * @date : 2017-03-13
 */
public abstract class BaseListViewAdapter<T> extends android.widget.BaseAdapter {
    /**
     * 数据集合
     */
    protected List<T> mList = new ArrayList<T>();
    /**
     * 关联Activity
     */
    protected Activity mContext;

    public BaseListViewAdapter(Activity context) {
        this.mContext = context;
    }

    public BaseListViewAdapter(Activity mContext, List<T> mList) {
        super();
        this.mList = mList;
        this.mContext = mContext;
    }

    /*
     * (non-Javadoc)
     *
     * @see android.widget.Adapter#getCount()
     */
    @Override
    public int getCount() {
        if (mList != null)
            return mList.size();
        else
            return 0;
    }

    /*
     * (non-Javadoc)
     *
     * @see android.widget.Adapter#getItem(int)
     */
    @Override
    public Object getItem(int position) {
        return mList == null ? null : mList.get(position);
    }

    /*
     * (non-Javadoc)
     *
     * @see android.widget.Adapter#getItemId(int)
     */
    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    abstract public View getView(int position, View convertView, ViewGroup parent);


    public List<T> getList() {
        return mList;
    }

    public void setList(List<T> list) {
        this.mList.clear();
        this.mList.addAll(list);
        notifyDataSetChanged();
    }

    public void setList(T[] list) {
        List<T> arrayList = new ArrayList<T>();
        for (T t : list) {
            arrayList.add(t);
        }
        setList(arrayList);
    }

    public void addData(List<T> list) {
        mList.addAll(list);
        notifyDataSetChanged();
    }

    public void addData(T data) {
        mList.add(data);
        notifyDataSetChanged();
    }

    public void addDataToFirst(List<T> list) {
        for (int i = 0; i < list.size(); i++) {
            mList.add(i, list.get(i));
        }
        notifyDataSetChanged();
    }

    public void clearData() {
        if (mList != null) {
            mList.clear();
        }
        notifyDataSetChanged();
    }

}
