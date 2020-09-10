package com.liyy.mytest.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.liyy.mytest.R;

public class CalculatorActivity extends BaseActivity {

    private View.OnClickListener mOnClickListener;
    private TextView display;
    private double number1 = 0;
    private double number2 = 0;
    private double result = 0;
    private boolean EquBtnDownFlag = false;
    private enum operator{DEFAULT, ADD, SUBTRACT, MULTIPLY, DIVIDE};
    private operator mCalType = operator.DEFAULT;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calculator);
        setTitle(R.string.calculator);
        display = findViewById(R.id.display);
        init();
        click();
        findById();
    }

    private void init() {
        number1 = 0;
        number2 = 0;
        result = 0;
        EquBtnDownFlag = false;
        display.setText(null);
    }

    private void findById() {
        findViewById(R.id.clear).setOnClickListener(mOnClickListener);
        findViewById(R.id.num0).setOnClickListener(mOnClickListener);
        findViewById(R.id.num1).setOnClickListener(mOnClickListener);
        findViewById(R.id.num2).setOnClickListener(mOnClickListener);
        findViewById(R.id.num3).setOnClickListener(mOnClickListener);
        findViewById(R.id.num4).setOnClickListener(mOnClickListener);
        findViewById(R.id.num5).setOnClickListener(mOnClickListener);
        findViewById(R.id.num6).setOnClickListener(mOnClickListener);
        findViewById(R.id.num7).setOnClickListener(mOnClickListener);
        findViewById(R.id.num8).setOnClickListener(mOnClickListener);
        findViewById(R.id.num9).setOnClickListener(mOnClickListener);
        findViewById(R.id.add).setOnClickListener(mOnClickListener);
        findViewById(R.id.subtract).setOnClickListener(mOnClickListener);
        findViewById(R.id.multiply).setOnClickListener(mOnClickListener);
        findViewById(R.id.divide).setOnClickListener(mOnClickListener);
        findViewById(R.id.point).setOnClickListener(mOnClickListener);
        findViewById(R.id.equal).setOnClickListener(mOnClickListener);
    }

    private void click() {
        mOnClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.clear:
                        init();
                        break;
                    case R.id.num0:
                        setDisplay("0");
                        break;
                    case R.id.num1:
                        setDisplay("1");
                        break;
                    case R.id.num2:
                        setDisplay("2");
                        break;
                    case R.id.num3:
                        setDisplay("3");
                        break;
                    case R.id.num4:
                        setDisplay("4");
                        break;
                    case R.id.num5:
                        setDisplay("5");
                        break;
                    case R.id.num6:
                        setDisplay("6");
                        break;
                    case R.id.num7:
                        setDisplay("7");
                        break;
                    case R.id.num8:
                        setDisplay("8");
                        break;
                    case R.id.num9:
                        setDisplay("9");
                        break;
                    case R.id.point:
                        setDisplay(".");
                        break;
                    case R.id.add:
                        setOperationType(operator.ADD);
                        break;
                    case R.id.subtract:
                        setOperationType(operator.SUBTRACT);
                        break;
                    case R.id.multiply:
                        setOperationType(operator.MULTIPLY);
                        break;
                    case R.id.divide:
                        setOperationType(operator.DIVIDE);
                        break;
                    case R.id.equal:
                        CharSequence equal = display.getText();
                        if (equal.equals("") || equal == null || equal.equals(null)) {
                            return;
                        }

                        number2 = Double.parseDouble(equal.toString());

                        switch (mCalType) {
                            case DEFAULT:
                                result = number2;
                                break;
                            case ADD:
                                result = number1 + number2;
                                break;
                            case SUBTRACT:
                                result = number1 - number2;
                                break;
                            case MULTIPLY:
                                result = number1 * number2;
                                break;
                            case DIVIDE:
                                result = number1 / number2;
                                break;
                            default:
                                result = 0;
                                break;
                        }

                        String temp_result = String.valueOf(result);
                        display.setText(temp_result);
                        EquBtnDownFlag = true;
                        break;
                    default:
                        break;
                }
            }
        };
    }

    // 设置计算器显示数字
    private void setDisplay(String string)
    {
        if (EquBtnDownFlag) {
            display.setText(null);
            EquBtnDownFlag = false;
        }

        // 新输入的数字与之前的数字拼接到一起
        display.setText(display.getText().toString() + string);
    }

    //设置操作类型
    private void setOperationType(operator calType) {
        CharSequence operator = display.getText();
        if (operator.equals("") || operator == null || operator.equals(null)) {
            return;
        }

        number1 = Double.parseDouble(operator.toString());
        mCalType = calType;
        display.setText(null);
    }
}