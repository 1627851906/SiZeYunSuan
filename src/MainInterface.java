import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
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
        Group root = new Group();
        //场景
        Scene scene = new Scene(root, 500, 400, Color.WHITE);
        int x = 100;
        int y = 100;
        int red = 30;
        int green = 40;
        int blue = 50;

        Text text = new Text(x, y, "JavaFX 2.0");

        text.setFill(Color.rgb(red, green, blue, .99)); //opacity透明度
        root.getChildren().add(text);

        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
