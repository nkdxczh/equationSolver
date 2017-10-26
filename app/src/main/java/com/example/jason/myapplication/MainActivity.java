package com.example.jason.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView questionTxt;
    private TextView answerTxt;
    private EditText rootTxt1;
    private EditText rootTxt2;
    private Button submitBtn;
    private Button nextBtn;

    private int count;
    private int mode;

    private int[] paras;

    private int correct;
    private int wrong;

    private  Generator generator;

    private long timeBase;

    private int[] newQuestion(int mode){
        if(mode == 0){
            int[] paras = generator.generateFirst();
            String question = paras[0] + "x";
            if(paras[1] < 0)question += paras[1] + "x";
            else question += "+" + paras[1] + "x";
            question += "=" + paras[2];
            questionTxt.setText(question);
            return paras;
        }
        else{
            int[] paras = generator.generateSecond();
            String question = paras[0] + "x^2";
            if(paras[1] < 0)question += paras[1];
            else question += "+" + paras[1] + "x";
            if(paras[2] < 0)question += paras[2];
            else question += "+" + paras[2];
            question += "=" + paras[3];
            questionTxt.setText(question);
            return paras;
        }
    }

    private void solve(int mode, int[] paras, double[] input){
        if(mode == 0){
            double res;
            try {
                res = generator.solveFirst(paras);
            } catch (Generator.WrongException e) {
                e.printStackTrace();
                answerTxt.setText("Wrong Input!");
                return;
            }
            if(Math.abs(res - input[0]) < 1e-6){
                correct++;
                answerTxt.setText("Correct! "+"x=" + res);
            }
            else{
                wrong++;
                answerTxt.setText("Wrong! "+"x=" + res);
            }
        }
        else{
            double[] res;
            try {
                res = generator.solveSecond(paras);
            } catch (Generator.WrongException e) {
                e.printStackTrace();
                answerTxt.setText("Wrong Input!");
                return;
            }
            if(Math.abs(Math.max(res[0], res[1]) - Math.max(input[0], input[1])) < 1e-6 && Math.abs(Math.min(res[0], res[1]) - Math.min(input[0], input[1])) < 1e-6){
                correct++;
                answerTxt.setText("Correct! "+"x1=" + res[0] + ", x2=" + res[1]);
            }
            else{
                wrong++;
                answerTxt.setText("Wrong! "+"x1=" + res[0] + ", x2=" + res[1]);
            }
        }
    }

    private void submit(){
        rootTxt1.setEnabled(false);
        rootTxt2.setEnabled(false);
        submitBtn.setEnabled(false);
        nextBtn.setEnabled(true);

        double[] input = new double[2];
        if(mode == 0){
            try {
                input[0] = Double.parseDouble(rootTxt1.getText().toString());
            }catch (NumberFormatException e){
                answerTxt.setText("Wrong format");
            }

        }
        else{
            try {
                input[0] = Double.parseDouble(rootTxt1.getText().toString());
                input[1] = Double.parseDouble(rootTxt2.getText().toString());
            }catch (NumberFormatException e){
                answerTxt.setText("Wrong format");
            }
        }
        solve(mode, paras, input);
    }

    private void update(){
        questionTxt.setText("");
        answerTxt.setText("Your Answer");
        rootTxt1.setText("");
        rootTxt2.setText("");

        rootTxt1.setEnabled(true);
        rootTxt2.setEnabled(mode  == 1);
        submitBtn.setEnabled(true);
        nextBtn.setEnabled(false);

        if(count > 5)mode = 1;

        paras = newQuestion(mode);
        count++;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        generator = new Generator();
        count = 0;
        mode = 0;

        questionTxt = (TextView)findViewById(R.id.questionTxt);
        answerTxt = (TextView)findViewById(R.id.answerTxt);
        rootTxt1 = (EditText)findViewById(R.id.rootTxt1);
        rootTxt2 = (EditText)findViewById(R.id.rootTxt2);
        submitBtn = (Button)findViewById(R.id.submitBtn);
        nextBtn = (Button)findViewById(R.id.nextBtn);

        submitBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                submit();
            }
        });

        nextBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                update();
            }
        });

        update();
    }
}
