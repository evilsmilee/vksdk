package nickb.ru.vksdk.di.component;

import javax.inject.Singleton;

import dagger.Component;
import nickb.ru.vksdk.common.manager.NetworkManager;
import nickb.ru.vksdk.di.module.ApplicationModule;
import nickb.ru.vksdk.di.module.ManagerModule;
import nickb.ru.vksdk.di.module.RestModule;
import nickb.ru.vksdk.model.view.CommentBodyViewModel;
import nickb.ru.vksdk.model.view.CommentFooterViewModel;
import nickb.ru.vksdk.model.view.InfoContactsViewModel;
import nickb.ru.vksdk.model.view.InfoLinksViewModel;
import nickb.ru.vksdk.model.view.TopicViewModel;
import nickb.ru.vksdk.mvp.presenter.BoardPresenter;
import nickb.ru.vksdk.mvp.presenter.CommentsPresenter;
import nickb.ru.vksdk.mvp.presenter.InfoContactsPresenter;
import nickb.ru.vksdk.mvp.presenter.InfoLinksPresenter;
import nickb.ru.vksdk.mvp.presenter.InfoPresenter;
import nickb.ru.vksdk.mvp.presenter.MainPresenter;
import nickb.ru.vksdk.mvp.presenter.MembersPresenter;
import nickb.ru.vksdk.mvp.presenter.NewsFeedPresenter;
import nickb.ru.vksdk.mvp.presenter.OpenedCommentPresenter;
import nickb.ru.vksdk.mvp.presenter.OpenedPostPresenter;
import nickb.ru.vksdk.mvp.presenter.TopicCommentsPresenter;
import nickb.ru.vksdk.ui.activity.BaseActivity;
import nickb.ru.vksdk.ui.activity.MainActivity;
import nickb.ru.vksdk.ui.activity.OpenedPostFromPushActivity;
import nickb.ru.vksdk.ui.fragment.CommentsFragment;
import nickb.ru.vksdk.ui.fragment.InfoContactsFragment;
import nickb.ru.vksdk.ui.fragment.InfoLinksFragment;
import nickb.ru.vksdk.ui.fragment.NewsFeedFragment;
import nickb.ru.vksdk.ui.fragment.OpenedCommentFragment;
import nickb.ru.vksdk.ui.fragment.OpenedPostFragment;
import nickb.ru.vksdk.ui.fragment.TopicCommentsFragment;
import nickb.ru.vksdk.ui.view.holder.NewsItemBodyHolder;
import nickb.ru.vksdk.ui.view.holder.NewsItemFooterHolder;
import nickb.ru.vksdk.ui.view.holder.attachment.ImageAttachmentHolder;
import nickb.ru.vksdk.ui.view.holder.attachment.VideoAttachmentHolder;

@Singleton
@Component(modules = {ApplicationModule.class, ManagerModule.class, RestModule.class})
public interface ApplicationComponent {

    //activities
    void inject(BaseActivity activity);
    void inject(MainActivity activity);
    void inject(OpenedPostFromPushActivity activity);

    //fragments
    void inject(NewsFeedFragment fragment);
    void inject(OpenedPostFragment fragment);
    void inject(OpenedCommentFragment fragment);
    void inject(CommentsFragment fragment);
    void inject(TopicCommentsFragment fragment);
    void inject(InfoLinksFragment fragment);
    void inject(InfoContactsFragment fragment);

    //holders
    void inject(NewsItemBodyHolder holder);
    void inject(NewsItemFooterHolder holder);
    void inject(ImageAttachmentHolder holder);
    void inject(VideoAttachmentHolder holder);
    void inject(CommentBodyViewModel.CommentBodyViewHolder holder);
    void inject(CommentFooterViewModel.CommentFooterHolder holder);
    void inject(TopicViewModel.TopicViewHolder holder);
    void inject(InfoLinksViewModel.InfoLinksViewHolder holder);
    void inject(InfoContactsViewModel.InfoContactsViewHolder holder);

    //presenters
    void inject(NewsFeedPresenter presenter);
    void inject(MainPresenter presenter);
    void inject(MembersPresenter presenter);
    void inject(BoardPresenter presenter);
    void inject(InfoPresenter presenter);
    void inject(OpenedPostPresenter presenter);
    void inject(CommentsPresenter presenter);
    void inject(OpenedCommentPresenter presenter);
    void inject(TopicCommentsPresenter presenter);
    void inject(InfoLinksPresenter presenter);
    void inject(InfoContactsPresenter presenter);

    //managers
    void inject(NetworkManager manager);
}
