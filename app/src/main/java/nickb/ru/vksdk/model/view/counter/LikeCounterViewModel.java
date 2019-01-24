package nickb.ru.vksdk.model.view.counter;

import nickb.ru.vksdk.model.Likes;


public class LikeCounterViewModel extends CounterViewModel {

    private Likes mLikes;


    public LikeCounterViewModel(Likes likes) {
        super(likes.getCount());

        this.mLikes = likes;

        if (mLikes.getUserLikes() == 1) {
            setAccentColor();
        }
    }

    public Likes getmLikes() {

        return mLikes;
    }

}
