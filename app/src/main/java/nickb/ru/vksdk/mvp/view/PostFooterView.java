package nickb.ru.vksdk.mvp.view;

import com.arellomobile.mvp.MvpView;

import nickb.ru.vksdk.model.WallItem;
import nickb.ru.vksdk.model.view.counter.LikeCounterViewModel;

public interface PostFooterView extends MvpView {
    void like(LikeCounterViewModel likes);

    void openComments(WallItem wallItem);
}