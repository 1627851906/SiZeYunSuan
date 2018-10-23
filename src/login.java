import javafx.application.Application;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

import javafx.stage.Stage;

import javax.swing.text.Position;

public class login extends Application {
    public static void main(String[] args) {
        Application.launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        //主舞台
        primaryStage.setResizable(false);
        primaryStage.centerOnScreen();
        GridPane gridpane = new GridPane();
        VBox vBox = new VBox(); //间距
        gridpane.setPadding(new Insets(10, 10, 10, 10));
        gridpane.setHgap(10); //列与列之间的距离
        gridpane.setVgap(10);
        Label usernameLb = new Label("用户名");
        TextField usernameTf = new TextField();
        Label pswLb = new Label("密   码");
        PasswordField pswPf = new PasswordField();
        Button loginBtn = new Button("登录");
        loginBtn.setMaxWidth(210);
        gridpane.add(usernameLb,0,0);
        gridpane.add(usernameTf,1,0);
        gridpane.add(pswLb,0,1);
        gridpane.add(pswPf,1,1);
        gridpane.add(loginBtn,0,2,2,1);
        gridpane.setAlignment(Pos.CENTER);
        vBox.getChildren().add(gridpane);
        vBox.setAlignment(Pos.CENTER);
        //场景
        Scene scene = new Scene(vBox, 500,400);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
