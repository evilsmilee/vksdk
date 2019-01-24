package nickb.ru.vksdk.common;

import android.support.v7.widget.RecyclerView;
import android.util.ArrayMap;
import android.util.Log;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import nickb.ru.vksdk.model.view.BaseViewModel;
import nickb.ru.vksdk.ui.holder.BaseViewHolder;

public class BaseAdapter extends RecyclerView.Adapter<BaseViewHolder<BaseViewModel>> {

    private List<BaseViewModel> list = new ArrayList<>();

    private ArrayMap<Integer, BaseViewModel> mTypeInstances = new ArrayMap<>();

    @Override
    public BaseViewHolder<BaseViewModel> onCreateViewHolder(ViewGroup parent, int viewType) {
        return mTypeInstances.get(viewType).createViewHolder(parent);
    }

    @Override
    public void onBindViewHolder(BaseViewHolder<BaseViewModel> holder, int position) {
        holder.bindViewHolder(getItem(position));
    }

    @Override
    public void onViewRecycled(BaseViewHolder<BaseViewModel> holder) {
        super.onViewRecycled(holder);
        holder.unbindViewHolder();
    }

    public void registerTypeInstance(BaseViewModel item) {
        if (!mTypeInstances.containsKey(item.getType())) {
            mTypeInstances.put(item.getType().getValue(), item);
        }
    }


    public void setItems(List<BaseViewModel> items) {
        clearList();
        addItems(items);
    }

    public void addItems(List<? extends BaseViewModel> newItems) {

        for (BaseViewModel newItem : newItems) {
            registerTypeInstance(newItem);
        }

        list.addAll(newItems);

        notifyDataSetChanged();
    }


    public void clearList() {
        list.clear();
    }


    @Override
    public int getItemViewType(int position) {
        return getItem(position).getType().getValue();
    }

    @Override
    public int getItemCount() {
        Log.d("TAG", "getItemCount return " + String.valueOf(list.size()));
        return list.size();
    }


    public BaseViewModel getItem(int position) {
        return list.get(position);
    }

    public int getRealItemCount() {
        int count = 0;
        for (int i = 0; i < getItemCount(); i++) {
            if (!getItem(i).isItemDecorator()) {
                count += 1;
            }
        }

        Log.d("TAG", "getRealItemCount return " + String.valueOf(count));
        return count;
    }

    public void insertItem(BaseViewModel newItem) {
        registerTypeInstance(newItem);

        list.add(newItem);
        notifyItemInserted(getItemCount() - 1);
    }

}
