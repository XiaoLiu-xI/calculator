package je.xo.xiaoliu; // 这里改成你自己的包名！

import je.xo.xiaoliu.R; // 这里改成你自己的包名.R！
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    EditText show;
    StringBuffer input;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        show = findViewById(R.id.et_show);
        input = new StringBuffer();

        // 数字按钮
        findViewById(R.id.btn0).setOnClickListener(this);
        findViewById(R.id.btn1).setOnClickListener(this);
        findViewById(R.id.btn2).setOnClickListener(this);
        findViewById(R.id.btn3).setOnClickListener(this);
        findViewById(R.id.btn4).setOnClickListener(this);
        findViewById(R.id.btn5).setOnClickListener(this);
        findViewById(R.id.btn6).setOnClickListener(this);
        findViewById(R.id.btn7).setOnClickListener(this);
        findViewById(R.id.btn8).setOnClickListener(this);
        findViewById(R.id.btn9).setOnClickListener(this);

        // 符号按钮
        findViewById(R.id.btnAdd).setOnClickListener(this);
        findViewById(R.id.btnMin).setOnClickListener(this);
        findViewById(R.id.btnMul).setOnClickListener(this);
        findViewById(R.id.btnDiv).setOnClickListener(this);
        findViewById(R.id.btnDot).setOnClickListener(this);

        // 功能按钮
        findViewById(R.id.btnC).setOnClickListener(this);
        findViewById(R.id.btnDel).setOnClickListener(this);
        findViewById(R.id.btnEq).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.btn0) addText("0");
        else if (id == R.id.btn1) addText("1");
        else if (id == R.id.btn2) addText("2");
        else if (id == R.id.btn3) addText("3");
        else if (id == R.id.btn4) addText("4");
        else if (id == R.id.btn5) addText("5");
        else if (id == R.id.btn6) addText("6");
        else if (id == R.id.btn7) addText("7");
        else if (id == R.id.btn8) addText("8");
        else if (id == R.id.btn9) addText("9");
        else if (id == R.id.btnAdd) addText("+");
        else if (id == R.id.btnMin) addText("-");
        else if (id == R.id.btnMul) addText("*");
        else if (id == R.id.btnDiv) addText("/");
        else if (id == R.id.btnDot) addText(".");
        else if (id == R.id.btnC) {
            input.delete(0, input.length());
            show.setText("");
        } else if (id == R.id.btnDel) {
            if (input.length() > 0) {
                input.deleteCharAt(input.length() - 1);
                show.setText(input.toString());
            }
        } else if (id == R.id.btnEq) {
            calc();
        }
    }

    void addText(String str) {
        input.append(str);
        show.setText(input.toString());
    }

    void calc() {
        try {
            double res = evalExpression(input.toString());
            if (res == (long) res) {
                show.setText(String.valueOf((long) res));
            } else {
                show.setText(String.valueOf(res));
            }
            input.delete(0, input.length()).append(show.getText());
        } catch (Exception e) {
            show.setText("格式错误");
            input.delete(0, input.length());
        }
    }

    private double evalExpression(String expr) throws Exception {
        String[] addParts = expr.split("(?=[+-])");
        double result = 0;
        for (String part : addParts) {
            if (part.isEmpty()) continue;
            boolean negative = part.startsWith("-");
            if (negative) part = part.substring(1);

            String[] mulParts = part.split("(?=[*/])");
            double val = 1;
            char lastOp = '*';
            for (String mp : mulParts) {
                if (mp.isEmpty()) continue;
                if (mp.charAt(0) == '*' || mp.charAt(0) == '/') {
                    lastOp = mp.charAt(0);
                    mp = mp.substring(1);
                }
                double num = Double.parseDouble(mp);
                if (lastOp == '*') val *= num;
                else if (lastOp == '/') val /= num;
            }

            if (negative) result -= val;
            else result += val;
        }
        return result;
    }
}
