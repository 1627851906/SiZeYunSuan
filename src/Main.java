import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class Main extends Application {
    private boolean isEnd = false;
    private int doneNumber = 0;
    private int qNumber;
    private int setedTime = -1;
    private int parent = -1;
    private static ObservableList<String> expressions = FXCollections.observableArrayList();
    private static ArrayList<Float> resultList = new ArrayList<Float>();
    private Questions questions = new Questions();
    private BorderPane borderPane = new BorderPane();
    private Label time = new Label();
    private final Timer timer = new Timer();
    Button ensureBtn = new Button("确认答案");
    Button resetBtn = new Button("清空");
    ProgressIndicator progressIndicator = new ProgressIndicator();
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        Stage startStage = new Stage();
        GridPane startGp = new GridPane();
        startGp.setPrefWidth(150);
        Label numberLb = new Label("题目个数 :");
        Label timeLb = new Label("答题时间:");
        final ComboBox numberComboBox = new ComboBox();
        numberComboBox.getItems().addAll("1",
                "2",
                "3",
                "4",
                "5"
        );
        numberComboBox.setValue("3");
        qNumber = 3;
        final ComboBox timeComboBox = new ComboBox();
        timeComboBox.getItems().addAll("10",
                "30",
                "50",
                "80",
                "100",
                "120"
        );
        timeComboBox.setPrefWidth(80);
        timeComboBox.setEditable(true);
        timeComboBox.setPromptText("秒");
        Button startBtn = new Button("开始答题");
        startBtn.setPrefWidth(startGp.getPrefWidth());
        startGp.add(numberLb, 0, 0);
        startGp.add(numberComboBox, 1, 0);
        startGp.add(timeLb, 0, 1);
        startGp.add(timeComboBox, 1, 1);
        startGp.add(startBtn, 0, 2,2,1);
        startGp.setAlignment(Pos.CENTER);
        startGp.setVgap(10);
        startGp.setHgap(10);
        Alert timeAlert = new Alert(Alert.AlertType.ERROR);
        startBtn.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                try {
                    setedTime = Integer.parseInt(timeComboBox.getSelectionModel().getSelectedItem().toString());
                    if (setedTime <1 || setedTime > 120) {
                        timeAlert.setContentText("时间不能小于1秒或大于120秒");
                        timeAlert.showAndWait();
                        timeComboBox.setValue("");
                        timeComboBox.requestFocus();
                    }
                } catch (Exception e) {
                    timeAlert.setContentText("时间格式出错");
                    timeAlert.showAndWait();
                    timeComboBox.setValue("");
                    timeComboBox.requestFocus();
                }
                if (!(setedTime <1 || setedTime > 120)) {
                    parent = setedTime;
                    qNumber = Integer.parseInt(numberComboBox.getSelectionModel().getSelectedItem().toString());
                    startStage.close();
                    questions.printQuestions(qNumber);
                    primaryStage.show();
                    timer.schedule(new TimerTask() {
                        @Override
                        public void run() {
                            Platform.runLater(new Runnable() {
                                @Override
                                public void run() {
                                    setedTime--;
                                    time.setText(String.valueOf(setedTime));
                                    if (setedTime == 0) {
                                        showAlert(1);
                                        timer.cancel();
                                        resetBtn.setVisible(false);
                                        ensureBtn.setVisible(false);
                                        isEnd = true;
                                    }
                                    progressIndicator.setProgress((double)setedTime / (double)parent);
                                    //更新JavaFX的主线程的代码放在此处
                                }
                            });
                        }
                    },0,1000);
                }
            }});
        Scene startScene = new Scene(startGp, 300,300);
        startStage.setScene(startScene);
        startStage.show();
        primaryStage.setResizable(false);
        primaryStage.centerOnScreen();
        VBox centerVBox = new VBox();
        VBox leftVBox = new VBox();
        leftVBox.setSpacing(50);
        centerVBox.setPadding(new Insets(0,0,10,0));
        centerVBox.setSpacing(10);
        HBox btnHBox = new HBox();
        btnHBox.setSpacing(10);
        TextArea userAnswer = new TextArea();
        final Tooltip tooltip = new Tooltip();
        tooltip.setText("若答案为分数，可以输入分数。若答案为无理数，则四舍五入。");
        userAnswer.setTooltip(tooltip);
        userAnswer.setPrefHeight(100);
        Label answerMsg = new Label();
        answerMsg.setWrapText(true);
        Label bigQuestion = new Label();
        answerMsg.setFont(new Font("Arial",20));
        answerMsg.setTextFill(Color.rgb(255,0,0));
        bigQuestion.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        bigQuestion.setTextFill(Color.rgb(0,0,0));
        btnHBox.getChildren().addAll(ensureBtn, resetBtn);
        btnHBox.setAlignment(Pos.CENTER_RIGHT);
        centerVBox.getChildren().addAll(bigQuestion, userAnswer, btnHBox, answerMsg);
        ListView<String> listView = new ListView<String>();
        listView.setItems(expressions);
        listView.setPrefHeight(200);
        Label spareTimeLb = new Label("剩余时间: ");
        spareTimeLb.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        time.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        HBox timeHBox = new HBox();
        timeHBox.setSpacing(10);
        timeHBox.setAlignment(Pos.CENTER);
        progressIndicator.setPrefWidth(80);
        progressIndicator.setPrefHeight(80);
        timeHBox.getChildren().addAll(progressIndicator, spareTimeLb, time);
        leftVBox.getChildren().addAll(listView, timeHBox);
        borderPane.setLeft(leftVBox);
        borderPane.setCenter(centerVBox);
        listView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
                    public void changed(ObservableValue<? extends String> ov, String old_val, String new_val) {
                        if (!isEnd) {
                            if (old_val != null && !ensureBtn.isVisible()) {
                                int index = expressions.indexOf(old_val);
                                expressions.remove(index);
                            }
                            resetBtn.setVisible(true);
                            ensureBtn.setVisible(true);
                            userAnswer.clear();
                            answerMsg.setText("");
                        }
                        bigQuestion.setText(new_val);
                    }});
        ensureBtn.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                doneNumber++;
                Float myQuestion = questions.inputAnswer(userAnswer.getText());
                int index = listView.getSelectionModel().getSelectedIndex();
                if (myQuestion == -1) {
                    answerMsg.setText("请确认输入的是数字或者分数!");
                } else {
                    answerMsg.setText(questions.answerCheck(resultList.get(index), myQuestion));
                }
                ensureBtn.setVisible(false);
                resetBtn.setVisible(false);
                if (expressions.size() == 1) {
                    showAlert(2);
                    timer.cancel();
                    resetBtn.setVisible(false);
                    ensureBtn.setVisible(false);
                }
            }
        });
        resetBtn.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                userAnswer.clear();
            }
        });
        Scene scene = new Scene(borderPane, 500,300);
        primaryStage.setScene(scene);
        //primaryStage.show();
    }
    public static void setQuestion(String expression) {
        expressions.add(expression);
    }
    public static void getAnswer(float answer) {
        resultList.add(answer);
    }
    private void showAlert(int type) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        if (type ==1) {
            alert.setContentText("时间结束，不能答题。做题"+qNumber+"道，答对" + questions.grade +
                  "道, " + "答错" + (doneNumber - questions.grade) + "道， 未答"+(qNumber - doneNumber) + "道， 用时：" + (parent - setedTime) + "s");
        } else {
            alert.setContentText("你已做完全部题目。做题"+qNumber+"道， 答对" + questions.grade +
                    "道， 答错" + (qNumber - questions.grade) +"道， 用时：" + (parent - setedTime) + "s");
        }
        alert.show();
    }
}
