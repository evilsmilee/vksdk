package nickb.ru.vksdk.model.view.counter;

import nickb.ru.vksdk.model.Comments;

public class CommentCounterViewModel extends CounterViewModel {

    private Comments mComments;

    public CommentCounterViewModel(Comments comments) {
        super(comments.getCount());

        this.mComments = comments;
    }

    public Comments getComments() {
        return mComments;
    }
}
