package nickb.ru.vksdk.model.view;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

import butterknife.BindView;
import butterknife.ButterKnife;
import nickb.ru.vksdk.R;
import nickb.ru.vksdk.ui.view.holder.BaseViewHolder;

public class CreatePostTextViewModel extends BaseViewModel {

    private String mMessage;

    public CreatePostTextViewModel() {

    }



    public String getMessage() {
        return mMessage;
    }

    public void setMessage(String mMessage) {
        this.mMessage = mMessage;
    }

    @Override
    public LayoutTypes getType() {
        return LayoutTypes.CreatePostText;
    }




    @Override
    public NewPostTextViewHolder onCreateViewHolder(View view) {
        return new NewPostTextViewHolder(view);
    }

    public static class NewPostTextViewHolder extends BaseViewHolder<CreatePostTextViewModel> {

        @BindView(R.id.et_message)
        public EditText message;

        public NewPostTextViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
        }

        @Override
        public void bindViewHolder(CreatePostTextViewModel createPostTextViewModel) {
            message.setText(createPostTextViewModel.getMessage());

            //слушатель изменений текста в текстовом поле, которые мы сохраняем в переменную mMessage
            message.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    createPostTextViewModel.setMessage(charSequence.toString());
                }

                @Override
                public void afterTextChanged(Editable editable) {
                }
            });
        }

        @Override
        public void unbindViewHolder() {

        }
    }

}
