package de.wesim.joditfx;

import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

// TODO Upgrade to Jdk 16 and JavaFX 16
// TODO Integrate https://bugs.openjdk.java.net/browse/JDK-8253696
// TODO Integrate content update
public class DemoApp extends Application {
    
    @Override
    public void start(Stage primaryStage) throws Exception {
        
        final JoditFx editor = new JoditFx(getHostServices(), "Hello World!", null);

        final TextArea area = new TextArea();
        area.setPrefRowCount(5);
        final MenuBar menuBar = new MenuBar();
        final Menu menu = new Menu("Public API methods");
        final MenuItem getHtmlText = new MenuItem("getHtmlText()");
        final MenuItem setHtmlText = new MenuItem("setHtmlText(String content)");
        final MenuItem quit = new MenuItem("Quit");
        menu.getItems().addAll(getHtmlText, setHtmlText, new SeparatorMenuItem(), quit);
        menuBar.getMenus().add(menu);
        quit.setOnAction(event -> {
                primaryStage.fireEvent(new WindowEvent(primaryStage, WindowEvent.WINDOW_CLOSE_REQUEST));
        });
        getHtmlText.setOnAction(e-> {
            final String htmlText = editor.getHtmlText();
            area.setText(htmlText);
        });
        setHtmlText.setOnAction( e-> {
            final String plainText = area.getText();
            editor.setHtmlText(plainText);
        });

        final BorderPane mainPane = new BorderPane();

        final Scene scene = new Scene(mainPane, 600, 400);
        mainPane.setCenter(editor);
        mainPane.setTop(menuBar);
        mainPane.setBottom(area);
        
        primaryStage.setScene(scene);
        primaryStage.show();
        
        primaryStage.titleProperty()
                .bind(Bindings.createStringBinding(
                        () -> { 
                            var ret_string = "JoditFX Demo Application";
                            if (editor.contentUpdateProperty().get()) {
                                ret_string = ret_string + " - Changed";
                            }
                            return ret_string; }, 
                
                editor.contentUpdateProperty()));
                
    }

    /* 
    	If we include the main() method, then we will need to call Application.launch()
    	for launching the JavaFX application
    */
    public static void main(String[] args) {
        launch(args);
    }
}
