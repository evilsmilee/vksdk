package nickb.ru.vksdk.mvp.view;

import com.arellomobile.mvp.MvpView;

import nickb.ru.vksdk.model.Profile;
import nickb.ru.vksdk.ui.fragment.BaseFragment;

public interface MainView extends MvpView {

    void startSignIn();
    void signedId();
    void showCurrentUser(Profile profile);
    void showFragmentFromDrawer(BaseFragment baseFragment);
    void startActivityFromDrawer(Class<?> act);
}
