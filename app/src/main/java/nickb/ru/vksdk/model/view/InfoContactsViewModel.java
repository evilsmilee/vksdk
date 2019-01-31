package nickb.ru.vksdk.model.view;

import android.view.View;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import butterknife.BindView;
import butterknife.ButterKnife;
import nickb.ru.vksdk.R;
import nickb.ru.vksdk.ui.view.holder.BaseViewHolder;

public class InfoContactsViewModel extends BaseViewModel {


    @Override
    public LayoutTypes getType() {
        return LayoutTypes.InfoContacts;
    }

    @Override
    public InfoContactsViewHolder onCreateViewHolder(View view) {
        return new InfoContactsViewHolder(view);
    }

 public    static class InfoContactsViewHolder extends BaseViewHolder<InfoContactsViewModel> {

        @BindView(R.id.rv_contacts)
        RelativeLayout rvContacts;

        public InfoContactsViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @Override
        public void bindViewHolder(InfoContactsViewModel infoContactsViewModel) {

        }

        @Override
        public void unbindViewHolder() {

        }
    }
}
