package nickb.ru.vksdk.model.view;

import android.view.View;
import android.widget.TextView;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import nickb.ru.vksdk.MyApplication;
import nickb.ru.vksdk.R;
import nickb.ru.vksdk.common.manager.MyFragmentManager;
import nickb.ru.vksdk.model.Place;
import nickb.ru.vksdk.model.Topic;
import nickb.ru.vksdk.ui.activity.BaseActivity;
import nickb.ru.vksdk.ui.fragment.TopicCommentsFragment;
import nickb.ru.vksdk.ui.view.holder.BaseViewHolder;

public class TopicViewModel extends BaseViewModel {

    private int mId;
    private int mGroupId;
    private String mTitle;
    private String mCommentsCount;

    public TopicViewModel() {
    }

    public TopicViewModel(Topic topic) {
        this.mId = topic.id;
        this.mGroupId = topic.groupId;
        this.mTitle = topic.title;
        this.mCommentsCount = topic.getComments() + " сообщений";
    }

    @Override
    public LayoutTypes getType() {
        return LayoutTypes.Topic;
    }

    @Override
    protected BaseViewHolder onCreateViewHolder(View view) {
        return new TopicViewHolder(view);
    }

    public int getmId() {
        return mId;
    }

    public void setmId(int mId) {
        this.mId = mId;
    }

    public int getmGroupId() {
        return mGroupId;
    }

    public void setmGroupId(int mGroupId) {
        this.mGroupId = mGroupId;
    }

    public String getmTitle() {
        return mTitle;
    }

    public void setmTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public String getmCommentsCount() {
        return mCommentsCount;
    }

    public static class TopicViewHolder extends BaseViewHolder<TopicViewModel> {

        @Inject
        MyFragmentManager mFragmentManager;

        @BindView(R.id.tv_title)
        public TextView tvTitle;

        @BindView(R.id.tv_comments_count)
        public TextView tvCommentsCount;


        public TopicViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            MyApplication.getsApplicationComponent().inject(this);
        }

        @Override
        public void bindViewHolder(TopicViewModel topicViewModel) {
            tvTitle.setText(topicViewModel.getmTitle());
            tvCommentsCount.setText(topicViewModel.getmCommentsCount());
            itemView.setOnClickListener(view -> mFragmentManager.addFragment((BaseActivity) view.getContext(),
                    TopicCommentsFragment.newInstance(new Place(String.valueOf(topicViewModel.getmGroupId()), String.valueOf(topicViewModel.getmId()))),
                    R.id.main_wrapper));
        }

        @Override
        public void unbindViewHolder() {
            tvTitle.setText(null);
            tvCommentsCount.setText(null);
        }
    }
}
