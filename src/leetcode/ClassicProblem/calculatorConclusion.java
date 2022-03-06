package leetcode.ClassicProblem;


import java.util.*;
import java.util.regex.Pattern;

// 基本计算器总结
// 掌握可以解决一切计算器相关问题的双栈解法
// 掌握普通表达式转逆波兰式（逆波兰式也很容易解计算器问题）
public class calculatorConclusion {
    //  +-*/()计算器
    // 双栈解法，数字栈和符号栈
    // 如果是数字，则继续往后取，取完整数字入栈（个位数以外的数）
    // 如果是操作符，在入符号栈之前把符号栈顶可以计算的先计算（符号栈顶操作符优先级大于等于当前操作符）
    // 如果是右括号，则将符号栈和数字栈进行计算，直到符号栈顶是左括号为止，弹出左括号。
    // 边界条件：（1）第一个数是负数：初始化时往数字栈加一个0  （2）左括号和加减运算符前都往数字栈加0
    Map<Character, Integer> map = new HashMap<>() {{
        put('-', 1);
        put('+', 1);
        put('*', 2);
        put('/', 2);
        put('%', 2);
        put('^', 3);
    }};

    public int calculate(String s) {
        // 将所有的空格去掉
        s = s.replaceAll(" ", "");
        char[] cs = s.toCharArray();
        int n = s.length();
        // 存放所有的数字
        Deque<Integer> nums = new ArrayDeque<>();
        // 为了防止第一个数为负数，先往 nums 加个 0
        nums.addLast(0);
        // 存放所有「非数字以外」的操作
        Deque<Character> ops = new ArrayDeque<>();
        for (int i = 0; i < n; i++) {
            char c = cs[i];
            if (c == '(') {
                ops.addLast(c);
            } else if (c == ')') {
                // 计算到最近一个左括号为止
                while (!ops.isEmpty()) {
                    if (ops.peekLast() != '(') {
                        calc(nums, ops);
                    } else {
                        ops.pollLast();
                        break;
                    }
                }
            } else { // 数字
                if (isNumber(c)) {
                    int u = 0;
                    int j = i;
                    // 将从 i 位置开始后面的连续数字整体取出，加入 nums
                    while (j < n && isNumber(cs[j])) u = u * 10 + (cs[j++] - '0');
                    nums.addLast(u);
                    //i在每次循环后会自增1，所以这里要减一，避免跳过下一个字符
                    i = j - 1;
                } else { //运算符
                    //左括号和加减运算符前都往数字栈加0
                    if (i > 0 && (cs[i - 1] == '(' || cs[i - 1] == '+' || cs[i - 1] == '-')) {
                        nums.addLast(0);
                    }
                    // 有一个新操作要入栈时，先把栈内可以算的都算了
                    // 只有满足「栈内运算符」比「当前运算符」优先级高/同等，才进行运算
                    while (!ops.isEmpty() && ops.peekLast() != '(') {
                        char prev = ops.peekLast();
                        if (map.get(prev) >= map.get(c)) {
                            calc(nums, ops);
                        } else {
                            break;
                        }
                    }
                    ops.addLast(c);
                }
            }
        }
        // 将剩余的计算完
        while (!ops.isEmpty()) calc(nums, ops);
        return nums.peekLast();
    }

    void calc(Deque<Integer> nums, Deque<Character> ops) {
        if (nums.isEmpty() || nums.size() < 2) return;
        if (ops.isEmpty()) return;
        int b = nums.pollLast(), a = nums.pollLast();
        char op = ops.pollLast();
        int ans = 0;
        if (op == '+') ans = a + b;
        else if (op == '-') ans = a - b;
        else if (op == '*') ans = a * b;
        else if (op == '/') ans = a / b;
        else if (op == '^') ans = (int) Math.pow(a, b);
        else if (op == '%') ans = a % b;
        nums.addLast(ans);
    }

    boolean isNumber(char c) {
        return Character.isDigit(c);
    }


    // 转化为逆波兰式 https://blog.csdn.net/yuan_xw/article/details/104436091
    // 其实思路和双栈计算器差不多
    // 数字直接加入结果
    // 运算符时，将符号栈顶中运算级大于等于当前运算符的都弹出并压入结果
    // 左括号直接压入，右括号的话，把符号栈顶中第一个左括号之前的运算符都弹出并压入结果，左右括号直接丢弃
    public static List<String> toReversePolishExpressionList(List<String> expressionList) {
        //  1. 初始化两个栈：运算符栈s1和储存中间结果的列表s2；
        Deque<String> s1 = new ArrayDeque<>();
        List<String> s2 = new ArrayList<>();

        // 2. 从左至右扫描中缀表达式；
        for (String item : expressionList) {
            // 3. 遇到操作数时，将其压s2；
            if (Pattern.matches("-?[0-9]+(\\.[0-9]+)?", item)) {
                s2.add(item);
            }

            /**
             * 4. 遇到运算符时，比较其与s1栈顶运算符的优先级：
             * 4.2 否则，若优先级小于或等于栈顶运算符优先级，将s1栈顶的运算符弹出并压入到s2中。将当前运算符压入栈中，
             * 4.3 再次转到(4.1)与s1中新的栈顶运算符相比较；
             */
            if (isSymbol(item)) {
                while (s1.size() > 0 && isSymbol(s1.peek()) && priority(item) <= priority(s1.peek())) {
                    s2.add(s1.pop());
                }
                s1.push(item);
            }

            // 5. 遇到括号时：
            // 5.1 如果是左括号“(”，则直接压入s1；
            if (item.equals("(")) {
                s1.push(item);
            }

            // 5.2 如果是右括号“)”，则依次弹出s1栈顶的运算符，并压入s2，直到遇到左括号为止，此时将这一对括号丢弃；
            if (item.equals(")")) {
                while (!s1.peek().equals("(")) {
                    s2.add(s1.pop());
                }
                // 将左边的括号，弹栈
                s1.pop();
            }
        }
        while (s1.size() > 0) {
            s2.add(s1.pop());
        }
        return s2;
    }

    public static boolean isSymbol(String str) {
        return "+-*/".contains(str);
    }

    public static int priority(String value) {
        if ("+-".contains(value)) {
            return 1;
        } else if ("*/".contains(value)) {
            return 2;
        } else {
            throw new RuntimeException("暂不支持操作符：" + value);
        }
    }


    // 字符串解码 https://leetcode-cn.com/problems/decode-string/
    // 弱化版的基本计算器问题，双栈解决
    public String decodeString(String s) {
        char[] ss = s.toCharArray();
        Deque<Integer> nums = new ArrayDeque<>();
        Deque<String> ops = new ArrayDeque<>();
        for(int i=0;i<ss.length;i++) {
            if(Character.isDigit(ss[i])) {
                int num=0;
                int j=i;
                while(j<ss.length && Character.isDigit(ss[j])) num = num*10+(ss[j++]-'0');
                i = j-1;
                nums.addLast(num);
            } else {
                if(ss[i]==']') {
                    StringBuilder temp = new StringBuilder();
                    while(!ops.peekLast().equals("[")) {
                        temp.insert(0,ops.pollLast());
                    }
                    ops.pollLast();
                    int count = nums.pollLast();
                    StringBuilder cur = new StringBuilder();
                    while(count-->0) {
                        cur.append(temp);
                    }
                    ops.addLast(cur.toString());
                } else {
                    ops.addLast(Character.toString(ss[i]));
                }
            }
        }
        StringBuilder res = new StringBuilder();
        while(!ops.isEmpty()) {
            res.insert(0,ops.pollLast());
        }
        return res.toString();
    }

}
