import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.stream.IntStream;

import static java.lang.String.format;

public class HilariousCalculatorBuilder {
    private final static Integer MATRIX_SIZE = 500;
    private final static String PATH_TO_FOLDER = "/home/andrii/projects/calc/src/Calculator.java";
    private final static String IF_STATEMENT = "\t\tif(st==%s && nd==%s && sign.equals(\"%s\")){\n";
    private final static String SOUT_STATEMENT = "\t\t\tSystem.out.println(\"%s %s %s = %s\");\n";
    private final static String CLOSURE_END = "\t\t}\n";

    private final static String FIRST_HALF_CLASS_BODY =
            "import java.util.Scanner;\n" +
                    "\n" +
                    "public class Calculator {\n" +
                    "\n" +
                    "    public static void main(String[] args) {\n" +
                    "\n" +
                    "       Scanner sc = new Scanner(System.in);\n" +
                    "       System.out.println(\"Enter first number\");\n" +
                    "       int st = sc.nextInt();\n" +
                    "       System.out.println(\"Enter sign\");\n" +
                    "       String sign = sc.next();\n" +
                    "       System.out.println(\"Enter second number\");\n" +
                    "       int nd = sc.nextInt();\n";

    private static FileOutputStream OUTPUT_STREAM;


    public static void main(String[] args) throws IOException {
        final long l = System.currentTimeMillis();
        OUTPUT_STREAM = new FileOutputStream(PATH_TO_FOLDER, true);

        write(FIRST_HALF_CLASS_BODY);

        getIntStreamRange().forEach(el -> getIntStreamRange().forEach(el2 -> buildAndWriteBodyToFile(el, el2)));

        write(CLOSURE_END+CLOSURE_END);

        System.out.println("Execution time= " + (System.currentTimeMillis() - l));
    }

    private static void buildAndWriteBodyToFile(final int el, final int el2) {
        StringBuilder builder = new StringBuilder();
        builder.append(format(IF_STATEMENT, el, el2, Signs.MULTIPLY.getSign()));
        builder.append(format(SOUT_STATEMENT, el, Signs.MULTIPLY.getSign(), el2, el * el2));
        builder.append(CLOSURE_END);
        builder.append(format(IF_STATEMENT, el, el2, Signs.DIVIDE.getSign()));
        builder.append(format(SOUT_STATEMENT, el, Signs.DIVIDE.getSign(), el2, getDivideStringRes(el, el2)));
        builder.append(CLOSURE_END);
        builder.append(format(IF_STATEMENT, el, el2, Signs.SUBTRACT.getSign()));
        builder.append(format(SOUT_STATEMENT, el, Signs.SUBTRACT.getSign(), el2, el - el2));
        builder.append(CLOSURE_END);
        builder.append(format(IF_STATEMENT, el, el2, Signs.PLUS.getSign()));
        builder.append(format(SOUT_STATEMENT, el, Signs.PLUS.getSign(), el2, el + el2));
        builder.append(CLOSURE_END);
        try {
            write(builder.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String getDivideStringRes(int el, int el2) {
        String devres=null;
        if (el2 ==0){
            devres="Cannot divide by zero!";
        }else {
            float f = el / el2;
            devres = ""+f;
        }
        return devres;
    }

    private static IntStream getIntStreamRange() {
        return IntStream.rangeClosed(0, MATRIX_SIZE);
    }

    private static void write(final String body) throws IOException {
        OUTPUT_STREAM.write((body).getBytes(StandardCharsets.UTF_8));
    }
    
    enum Signs {
        DIVIDE("/"),
        SUBTRACT("-"),
        MULTIPLY("*"),
        PLUS("+");
        private String sign;

        Signs(final String sign) {
            this.sign = sign;
        }

        public String getSign() {
            return sign;
        }
    }
}
