import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class MainInterface extends Application{
    public static void main(String[] args) {
        Application.launch(args);
    }
    @Override
    public void start(Stage primaryStage) throws Exception {
        //主舞台
        primaryStage.setTitle("四则运算");
        primaryStage.setResizable(false);
        primaryStage.centerOnScreen();

        int red = 30;
        int green = 40;
        int blue = 50;
        int width = 500;
        int height = 300;

        Text text = new Text("java");
        text.setX(500);
        text.setY(500);
        text.setFont(Font.font(null, FontWeight.BOLD, 32));
        text.setFill(Color.rgb(red, green, blue, .99)); //opacity透明度

        GridPane gridpane = new GridPane();
        gridpane.setPadding(new Insets(10, 10, 10, 10));
        gridpane.setHgap(5); //列与列之间的距离
        gridpane.setVgap(5);
        ColumnConstraints column1 = new ColumnConstraints(100);
        ColumnConstraints column2 = new ColumnConstraints(50, 150, 300);
        column2.setHgrow(Priority.ALWAYS);
        gridpane.getColumnConstraints().addAll(column1, column2);
        Canvas canvas = new Canvas();
        StackPane stackPane = new StackPane();
        stackPane.setStyle("-fx-background-color: white");
        canvas.setHeight(height);
        canvas.setWidth(width);
        stackPane.getChildren().add(canvas);
        gridpane.getChildren().addAll(stackPane,text);
        //场景
        Scene scene = new Scene(gridpane);

        primaryStage.setScene(scene);
        primaryStage.show();
   }
}
