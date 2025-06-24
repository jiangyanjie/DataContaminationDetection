package io.xpipe.app.prefs;

import io.xpipe.app.comp.base.TileButtonComp;
import io.xpipe.app.core.AppI18n;
import io.xpipe.app.core.AppProperties;
import io.xpipe.app.core.window.AppWindowHelper;
import io.xpipe.app.fxcomps.Comp;
import io.xpipe.app.fxcomps.impl.LabelComp;
import io.xpipe.app.fxcomps.impl.VerticalComp;
import io.xpipe.app.util.Hyperlinks;
import io.xpipe.app.util.JfxHelper;
import io.xpipe.app.util.OptionsBuilder;

import javafx.beans.property.SimpleStringProperty;
import javafx.geometry.Insets;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Region;

import atlantafx.base.theme.Styles;

import java.util.List;

public class AboutCategory extends AppPrefsCategory {

    private Comp<?> createLinks() {
        return new OptionsBuilder()
                .addComp(
                        new TileButtonComp("discord", "discordDescription", "mdi2d-discord", e -> {
                                    Hyperlinks.open(Hyperlinks.DISCORD);
                                    e.consume();
                                })
                                .grow(true, false),
                        null)
                .addComp(
                        new TileButtonComp("slack", "slackDescription", "mdi2s-slack", e -> {
                                    Hyperlinks.open(Hyperlinks.SLACK);
                                    e.consume();
                                })
                                .grow(true, false),
                        null)
                .addComp(
                        new TileButtonComp("securityPolicy", "securityPolicyDescription", "mdrmz-security", e -> {
                                    Hyperlinks.open(Hyperlinks.SECURITY);
                                    e.consume();
                                })
                                .grow(true, false),
                        null)
                .addComp(
                        new TileButtonComp("privacy", "privacyDescription", "mdomz-privacy_tip", e -> {
                                    Hyperlinks.open(Hyperlinks.PRIVACY);
                                    e.consume();
                                })
                                .grow(true, false),
                        null)
                .addComp(
                        new TileButtonComp("thirdParty", "thirdPartyDescription", "mdi2o-open-source-initiative", e -> {
                                    AppWindowHelper.sideWindow(
                                                    AppI18n.get("openSourceNotices"),
                                                    stage -> Comp.of(() -> createThirdPartyDeps()),
                                                    true,
                                                    null)
                                            .show();
                                    e.consume();
                                })
                                .grow(true, false),
                        null)
                .addComp(
                        new TileButtonComp("eula", "eulaDescription", "mdi2c-card-text-outline", e -> {
                                    Hyperlinks.open(Hyperlinks.EULA);
                                    e.consume();
                                })
                                .grow(true, false),
                        null)
                .buildComp();
    }

    private Region createThirdPartyDeps() {
        var list = new ThirdPartyDependencyListComp().createRegion();
        list.getStyleClass().add("open-source-notices");
        var sp = new ScrollPane(list);
        sp.setFitToWidth(true);
        sp.setPrefWidth(600);
        sp.setPrefHeight(500);
        return sp;
    }

    @Override
    protected String getId() {
        return "about";
    }

    @Override
    protected Comp<?> create() {
        var props = createProperties().padding(new Insets(0, 0, 0, 15));
        var update = new UpdateCheckComp().grow(true, false);
        return new VerticalComp(List.of(props, Comp.separator(), update, Comp.separator(), createLinks()))
                .apply(s -> s.get().setFillWidth(true))
                .apply(struc -> struc.get().setSpacing(15))
                .styleClass("information")
                .styleClass("about-tab")
                .apply(struc -> struc.get().setPrefWidth(600));
    }

    private Comp<?> createProperties() {
        var title = Comp.of(() -> {
                    return JfxHelper.createNamedEntry(
                            AppI18n.observable("xPipeClient"),
                            new SimpleStringProperty(
                                    "Version " + AppProperties.get().getVersion() + " ("
                                            + AppProperties.get().getArch() + ")"),
                            "logo.png");
                })
                .styleClass(Styles.TEXT_BOLD);

        var section = new OptionsBuilder()
                .addComp(title, null)
                .addComp(Comp.vspacer(10))
                .name("build")
                .addComp(new LabelComp(AppProperties.get().getBuild()), null)
                .name("runtimeVersion")
                .addComp(new LabelComp(System.getProperty("java.vm.version")), null)
                .name("virtualMachine")
                .addComp(
                        new LabelComp(System.getProperty("java.vm.vendor") + " " + System.getProperty("java.vm.name")),
                        null)
                .buildComp();
        return section.styleClass("properties-comp");
    }
}
