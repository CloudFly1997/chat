package com.jack.chat.component;

/**
 * @author Jinkang He
 * @version 1.0
 * @date 2020/3/2 1:33
 */

import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.web.HTMLEditor;
import javafx.stage.Stage;

import java.util.List;
import java.util.regex.Pattern;

public class HTMLEditorCustomizationSample extends Application {
    // limits the fonts a user can select from in the html editor.
    private static final List<String> limitedFonts = FXCollections.observableArrayList("Arial", "Times New Roman", "Courier New", "Comic Sans MS");

    public static void main(String[] args) { launch(args); }
    @Override public void start(Stage stage) {

        final HTMLEditor htmlEditor = new HTMLEditor();
        stage.setScene(new Scene(htmlEditor));
        stage.show();

        // hide controls we don't need.
        hideImageNodesMatching(htmlEditor, Pattern.compile(".*(Cut|Copy|Paste).*"), 0);
        Node seperator = htmlEditor.lookup(".separator");
        seperator.setVisible(false); seperator.setManaged(false);

        // modify font selections.
        int i = 0;
        for (Node candidate: (htmlEditor.lookupAll("MenuButton"))) {
            // fonts are selected by the second menu in the htmlEditor.
            if (candidate instanceof MenuButton && i == 1) {
                // limit the font selections to our predefined list.
                MenuButton menuButton = (MenuButton) candidate;
                List<MenuItem> removalList = FXCollections.observableArrayList();
                final List<MenuItem> fontSelections = menuButton.getItems();
                for (MenuItem item: fontSelections) {
                    if (!limitedFonts.contains(item.getText())) {
                        removalList.add(item);
                    }
                }
                fontSelections.removeAll(removalList);
                // Select a font from out limited font selection.
                // Selection done in Platform.runLater because if you try to do
                // the selection immediately, it won't take place.
                Platform.runLater(new Runnable() {
                    @Override public void run() {
                        boolean fontSelected = false;
                        for (final MenuItem item: fontSelections) {
                            if ("Comic Sans MS".equals(item.getText())) {
                                if (item instanceof RadioMenuItem) {
                                    ((RadioMenuItem) item).setSelected(true);
                                    fontSelected = true;
                                }
                            }
                        }
                        if (!fontSelected && fontSelections.size() > 0 && fontSelections.get(0) instanceof RadioMenuItem) {
                            ((RadioMenuItem) fontSelections.get(0)).setSelected(true);
                        }
                    }
                });
            }
            i++;
        }
        // add a custom button to the top toolbar.
        Node node = htmlEditor.lookup(".top-toolbar");
        if (node instanceof ToolBar) {
            ToolBar bar = (ToolBar) node;
            ImageView graphic = new ImageView(new Image("http://bluebuddies.com/gallery/title/jpg/Smurf_Fun_100x100.jpg", 32, 32, true, true));
            graphic.setEffect(new DropShadow());
            Button smurfButton = new Button("", graphic);
            bar.getItems().add(smurfButton);
            smurfButton.setOnAction(new EventHandler<ActionEvent>() {
                @Override public void handle(ActionEvent arg0) {
                    htmlEditor.setHtmlText("<font face='Comic Sans MS' color='blue'>Smurfs are having fun :-)</font>");
                }
            });
        }
    }

    // hide buttons containing nodes whose image url matches a given name pattern.
    public void hideImageNodesMatching(Node node, Pattern imageNamePattern, int depth) {
        if (node instanceof ImageView) {
            ImageView imageView = (ImageView) node;
            String url = imageView.getImage().impl_getUrl();
            if (url != null && imageNamePattern.matcher(url).matches()) {
                Node button = imageView.getParent().getParent();
                button.setVisible(false); button.setManaged(false);
            }
        }
        if (node instanceof Parent){
            for (Node child : ((Parent) node).getChildrenUnmodifiable()) {
                hideImageNodesMatching(child, imageNamePattern, depth + 1);

            }
        }

    }
}
