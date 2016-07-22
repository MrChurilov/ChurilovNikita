package ru.test.rambler.churilovnikita;

import javax.inject.Singleton;

import dagger.Component;
import ru.test.rambler.churilovnikita.Managers.HelpManager;
import ru.test.rambler.churilovnikita.ui.MainActivity;
import ru.test.rambler.churilovnikita.modules.AppModule;
import ru.test.rambler.churilovnikita.ui.OAuthDialog;
import ru.test.rambler.churilovnikita.ui.PhotoFragment;


@Singleton
@Component(modules = {AppModule.class})
public interface AppComponent {
    void inject(PhotoFragment photoFragment);
    void inject(OAuthDialog dialog);
}
