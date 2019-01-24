package nickb.ru.vksdk.model.view;

import android.support.annotation.LayoutRes;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import nickb.ru.vksdk.R;
import nickb.ru.vksdk.ui.holder.BaseViewHolder;

public abstract class BaseViewModel {

    public abstract LayoutTypes getType();

    public BaseViewHolder createViewHolder(ViewGroup parent) {
          return   onCreateViewHolder(LayoutInflater.from(parent.getContext())
                    .inflate(getType().getValue(), parent, false));

    }

    protected abstract BaseViewHolder onCreateViewHolder(View view);

    public enum LayoutTypes {
        NewsFeedItemHeader(R.layout.item_news_header),
        NewsFeedItemBody(R.layout.item_news_body),
        Member(R.layout.item_member),
        NewsFeedItemFooter(R.layout.item_news_footer);


        private final int id;

        LayoutTypes(int resId) {
            this.id = resId;
        }

        @LayoutRes
        public int getValue() {
            return id;
        }
    }

    public boolean isItemDecorator() {
        return false;
    }
}
