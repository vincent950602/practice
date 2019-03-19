package com.company;
import java.text.NumberFormat;

public class Main {
    public static void main(String[] args) {
	// write your code here
        String format_process = "";
        for(int i = 0; i < args.length; i++){
            format_process = format_process + args[i];
        }
        //除空格
        format_process = format_process.replace(" ","");
        if(inputCorrect(format_process)){
            if(formatCorrect(format_process)){
                double a = reduceBracket(format_process);
                if(Double.isInfinite(a) || !isAverage(a)){
                    System.out.println("VALUE ERROR");
                    //return "VALUE ERROR";
                }else {
                    //System.out.println(result);
                    System.out.println("result" + validDigits(a));
                }
            }else {
                System.out.println("FORMAT ERROR");
                //return "FORMAT ERROR";
            }
        }else{
            System.out.println("INPUT ERROR");
            //return "INPUT ERROR";
        }
    }

    //合法字符检测是否在{"+","-","*","/","^",".","(",")","0-9"}
    public static Boolean inputCorrect(String str){
        for(int i = 0; i < str.length(); i++){
            int chr = str.charAt(i);
            if (chr >= 40 && chr <= 57 && chr != 44 || chr == 94 || chr == 32) {
                return true;
            } else {
                return false;
            }
        }
        if(str.length() == 0){
            return false;
        }
        return null;
    }

    public static Boolean formatCorrect(String str){
        //开头和结尾不能是符号
        if(isHave(str.substring(0,1)) || isHave(str.substring(str.length()-1))){
            return false;
        }
        //检测重复符号,从第二个字符开始至倒数第二个字符结束。
        int j;
        for(int i = 1; i < str.length() - 1; i++){
            j = i + 1;
            if(isHave(str.substring(i,i+1))&&isHave(str.substring(j,j+1))){
                return false;
            }
        }
        //左括号 右括号 括号中间不能为空
        for(int i = 0; i < str.length(); i++){
            if(i-1 >= 0){
                if(str.charAt(i) == 40 && str.charAt(i-1) == 46){
                    return false;
                }else if(str.charAt(i) == 41 && (isHave(str.substring(i-1,i)) || str.charAt(i-1) == 40)){
                    return false;
                }
            }
            if(i+2 <= str.length()){
                if(str.charAt(i) == 40 && (isHave(str.substring(i+1,i+2)) || str.charAt(i+1) == 41)){
                    return false;
                }else if(str.charAt(i) == 41 && str.charAt(i+1) == 46){
                    return false;
                }
            }
        }
        //括号匹配
        int left_bracket = 0;
        for(int i = 0; i < str.length(); i++){
            if(left_bracket < 0){
                return false;
            }else {
                if(str.charAt(i) == 40){
                    left_bracket += 1;
                }else if(str.charAt(i) == 41){
                    left_bracket -= 1;
                }
            }
        }
        if(left_bracket != 0){
            return false;
        }else {
            return true;
        }
    }

    private static Boolean isHave(String chr){
        String sign[] ={"+","-","*","/","^","."};
        for(int i = 0; i < sign.length; i++){
            if(chr.equals(sign[i])){
                return true;
            }
        }
        return false;
    }

    public static String validDigits(double s){
        System.out.println(s);
        NumberFormat nf = NumberFormat.getInstance();
        nf.setMaximumFractionDigits(10);//设置保留多少位小数
        nf.setGroupingUsed(false);// 取消科学计数法
        String format_s = nf.format(s);
        for(int i = 0; i < format_s.length(); i++){
            /*if(format_s.charAt(0) == 48 && format_s.charAt(1) == 46){

            }
            if(format_s.charAt(format_s.length()-1) == 48 && format_s.charAt(format_s.length()-2) == 46){

            }*/
        }
        return format_s;
    }

    public static Boolean isAverage(Double s){
        double max = 1E9;
        double min = 1E-10;
        if((Math.abs(s) <= max && Math.abs(s) >= min) || Math.abs(s)==0){
            return true;
        }else {
            return false;
        }
    }

    //去括号计算算式
    public static double reduceBracket(String s){
        String S = ""; //后缀
        char[] Operators = new char[s.length()];
        int Top = -1;
        for (int i = 0; i < s.length(); i++)
        {
            char C = s.charAt(i);
            switch(C)
            {
                case ' ' :
                    break;
                case '+' : //操作符
                case '-' :
                    while (Top >= 0) //栈不为空时
                    {
                        char c = Operators[Top--]; //出栈
                        if (c == '(')
                        {
                            Operators[++Top] = c; //入栈
                            break;
                        }else
                        {
                            S = S + c;
                        }
                    }
                    Operators[++Top] = C; //push Operator
                    S += " ";
                    break;
                case '*' : //操作符
                case '/' :
                    while (Top >= 0) //栈不为空时
                    {
                        char c = Operators[Top--]; //pop Operator
                        if (c == '(')
                        {
                            Operators[++Top] = c; //push Operator
                            break;
                        }
                        else
                        {
                            if (c == '+' || c == '-')
                            {
                                Operators[++Top] = c; //push Operator
                                break;
                            }
                            else
                            {
                                S = S + c;
                            }
                        }
                    }
                    Operators[++Top] = C; //push Operator
                    S += " ";
                    break;
                case '(' : //操作符
                    Operators[++Top] = C;
                    S += " ";
                    break;
                case ')' : //操作符
                    while (Top >= 0) //栈不为空时
                    {
                        char c = Operators[Top--]; //pop Operator
                        if (c == '(')
                        {
                            break;
                        }
                        else
                        {
                            S = S + c;
                        }
                    }
                    S += " ";
                    break;
                default : //操作数
                    S = S + C;
                    break;
            }
        }
        while (Top >= 0)
        {
            S = S + Operators[Top--]; //pop Operator
        }

        System.out.println(S); //后缀

        //后缀表达式计算
        double[] Operands = new double[S.length()];
        double x, y, v;
        Top = - 1;
        String Operand = "";
        for (int i = 0; i < S.length(); i++)
        {
            char c = S.charAt(i);
            if ((c >= '0' && c <= '9') || c == '.')
            {
                Operand += c;
            }

            if ((c == ' ' || i == S.length() - 1) && Operand != "") //Update
            {
                Operands[++Top] = java.lang.Double.parseDouble(Operand) ; //push Operands
                Operand = "";
            }

            if (c == '+' || c == '-' || c == '*' || c == '/')
            {
                if ((Operand != ""))
                {
                    Operands[++Top] = java.lang.Double.parseDouble(Operand) ; //push Operands
                    Operand = "";
                }
                y = Operands[Top--]; //pop 双目运算符的第二操作数 (后进先出)注意操作数顺序对除法的影响
                x = Operands[Top--]; //pop 双目运算符的第一操作数
                switch (c)
                {
                    case '+' :
                        v = x + y;
                        break;
                    case '-' :
                        v = x - y;
                        break;
                    case '*' :
                        v = x * y;
                        break;
                    case '/' :
                        if(y == 0){
                            v = 1.0 / 0.0;
                        }else {
                            v = x / y; // 第一操作数 / 第二操作数 注意操作数顺序对除法的影响
                        }
                        break;
                    default :
                        v = 0;
                        break;
                }
                Operands[++Top] = v; //push 中间结果再次入栈
            }
        }
        v = Operands[Top--]; //pop 最终结果
        System.out.println("v = " +v);
    return v;
    }
}
