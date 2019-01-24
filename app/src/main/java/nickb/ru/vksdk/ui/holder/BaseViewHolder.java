package nickb.ru.vksdk.ui.holder;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import nickb.ru.vksdk.model.view.BaseViewModel;

public abstract class BaseViewHolder<Item extends BaseViewModel> extends RecyclerView.ViewHolder {


    public BaseViewHolder(@NonNull View itemView) {
        super(itemView);
    }

    public abstract void bindViewHolder(Item item);

    public abstract void unbindViewHolder();

}
