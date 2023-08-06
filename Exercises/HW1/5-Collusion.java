import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Tabani5 {

    final static String errorMessage = "CANNOT PERFORM THE COMMAND SUCCESSFULLY";

    public static void main(String[] args) {
        function main = new function();
        Scanner scanner = new Scanner(System.in);
        main.text = scanner.nextLine();
        String resultText = "";
        String inputLine;

        while (!(inputLine = scanner.nextLine()).matches("\\s*end\\s*")) {
            if (inputLine.matches("\\s*mul\\s*")) {
                resultText = main.mul();
            } else if (inputLine.matches("\\s*add\\s*")) {
                resultText = main.add();
            } else if (inputLine.matches("\\s*sub\\s*")) {
                resultText = main.sub();
            } else if (inputLine.matches("\\s*sum\\s\\d+\\s(-b|-f)\\s*")) {
                String[] str = inputLine.split("\\s");
                resultText = main.sum(str[1], str[2]);
            } else if (inputLine.matches("\\s*gcd\\s\\d+\\s(-b|-f)\\s*")) {
                String[] str = inputLine.split("\\s");
                resultText = main.gcdd(str[1], str[2]);
            } else if (inputLine.matches("\\s*replace\\s\\S+\\s\\S+\\s\\d+\\s*")) {
                String[] str = inputLine.split("\\s");
                resultText = main.replace(str[1], str[2], str[3]);
            } else if (inputLine.matches("\\s*count_entail\\s\\S+\\s*")) {
                String[] str = inputLine.split("\\s");
                resultText = main.count_entail(str[1]);
            } else if (inputLine.matches("\\s*insert\\s\\S+(\\s\\d*)?\\s*")) {
                String[] str = inputLine.split("\\s");
                resultText = main.insert(str);
            } else if (inputLine.matches("\\s*delete\\s\\S+(\\s-f)?\\s*")) {
                String[] str = inputLine.split("\\s");
                resultText = main.delete(str);
            } else if (inputLine.matches("\\s*print\\s*")) {
                resultText = main.text;
            } else {
                main.errorStatus = 2;
            }
            if (main.errorStatus == 0) {
                System.out.println(resultText);
                main.text = resultText;
            } else {
                if (main.errorStatus == 1) {
                    System.out.println(errorMessage);
                } else if (main.errorStatus == 2) {
                    System.out.println("THE COMMAND IS INVALID");
                }
                main.errorStatus = 0;
            }
        }
        if (inputLine.matches("\\s*end\\s*")) {
            System.out.println("END OF PROGRAM");
        }
    }

    private static class function {
        String text;
        int errorStatus = 0;

        public function() {
            text = new String();
        }

        static int gcd(int a, int b) {
            if (a == 0) return b;
            return gcd(b % a, a);
        }

        String mul() {
            String regex = "[-|+]?[0-9][\\d]*";
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(text);
            ArrayList<String> list = new ArrayList<String>();
            while (matcher.find()) {
                list.add(text.substring(matcher.start(), matcher.end()));
            }
            if (list.size() >= 2) {
                String firstStr = Integer.toString(Integer.parseInt(list.get(0)) * Integer.parseInt(list.get(1)));
                int i = text.indexOf(list.get(0));
                text = text.substring(0, i) + firstStr + text.substring(text.indexOf(list.get(1), i + list.get(0).length()) + list.get(1).length(), text.length());
            } else {
                errorStatus = 1;
            }
            return text;
        }

        String add() {
            String regex = "[-|+]?[0-9][\\d]*";
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(text);
            ArrayList<String> list = new ArrayList<String>();
            while (matcher.find()) {
                list.add(text.substring(matcher.start(), matcher.end()));
            }
            if (list.size() >= 2) {
                String firstStr = Integer.toString(Integer.parseInt(list.get(0)) + Integer.parseInt(list.get(1)));
                int i = text.indexOf(list.get(0));
                text = text.substring(0, i) + firstStr + text.substring(text.indexOf(list.get(1), i + list.get(0).length()) + list.get(1).length(), text.length());
            } else {
                errorStatus = 1;
            }
            return text;
        }

        String sub() {
            String regex = "[-|+]?[0-9][\\d]*";
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(text);
            ArrayList<String> list = new ArrayList<String>();
            while (matcher.find()) {
                list.add(text.substring(matcher.start(), matcher.end()));
            }
            if (list.size() >= 2) {
                String firstStr = Integer.toString(Integer.parseInt(list.get(0)) - Integer.parseInt(list.get(1)));
                int i = text.indexOf(list.get(0));
                text = text.substring(0, i) + firstStr + text.substring(text.indexOf(list.get(1), i + list.get(0).length()) + list.get(1).length(), text.length());
            } else {
                errorStatus = 1;
            }
            return text;
        }

        String sum(String str1, String str2) {
            String regex = "[-|+]?[0-9][\\d]*";
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(text);
            ArrayList<String> list = new ArrayList<String>();
            while (matcher.find()) {
                list.add(text.substring(matcher.start(), matcher.end()));
            }
            int result = 0;
            int numberNo;
            int start = 0;
            int fine = 0;
            boolean error = false;

            numberNo = list.size();
            int n = Integer.parseInt(str1);
            if (n > numberNo) {
                error = true;
            } else {
                if (str2.compareTo("-b") == 0) {
                    start = 0;
                    fine = n;
                } else {
                    if (str2.compareTo("-f") == 0) {
                        start = numberNo - n;
                        fine = numberNo;
                    } else error = true;
                }
            }
            if (!error) {
                result = Integer.parseInt(list.get(start));
                if (n != 1) {
                    for (int i = start + 1; i < fine; i++) {
                        result += Integer.parseInt(list.get(i));
                    }
                }
            }
            if (error) {
                errorStatus = 1;
            } else {
                text = text + "S" + Integer.toString(result) + "S";
            }
            return text;
        }

        String gcdd(String str1, String str2) {
            String regex = "[-|+]?[0-9][\\d]*";
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(text);
            ArrayList<String> list = new ArrayList<String>();
            while (matcher.find()) {
                list.add(text.substring(matcher.start(), matcher.end()));
            }
            int result = 0;
            int numberNo;
            int start = 0;
            int fine = 0;
            boolean error = false;
            numberNo = list.size();

            int n = Integer.parseInt(str1);
            if (n > numberNo) {
                error = true;
            } else {
                if (str2.compareTo("-b") == 0) {
                    start = 0;
                    fine = n;
                } else {
                    if (str2.compareTo("-f") == 0) {
                        start = numberNo - n;
                        fine = numberNo;
                    } else error = true;
                }
            }
            if (!error) {
                result = Integer.parseInt(list.get(start));
                if (n != 1) {
                    for (int i = start + 1; i < fine; i++) {
                        result = gcd(Integer.parseInt(list.get(i)), result);
                        if (result == 1) i = 1 + fine;
                    }
                }
            }
            if (error) {
                errorStatus = 1;
            } else text = text + "G" + Integer.toString(result) + "G";
            return text;
        }

        String replace(String str1, String str2, String str3) {
            int n = Integer.parseInt(str3);
            while (text.contains(str1) && n != 0) {
                text = text.replaceFirst(str1, str2);
                n--;
            }
            return text;
        }

        String count_entail(String str1) {
            if (text.contains(str1)) {
                int c = 0;
                errorStatus = 0;
                for (int i = 0; i < text.length(); i++) {
                    int ii = text.indexOf(str1, i);
                    if (ii != -1) {
                        c++;
                        i = ii;
                    }
                }
                text = text.concat("C" + Integer.toString(c) + "C");
            } else {
                errorStatus = 1;
            }
            return text;
        }

        String insert(String[] str) {
            if (str.length == 2) {
                text = text.concat(str[1]);
            } else if (str.length == 3) {
                if (text.length() >= Integer.parseInt(str[2])) {
                    text = text.substring(0, Integer.parseInt(str[2])) + str[1] + text.substring(Integer.parseInt(str[2]));
                } else {
                    errorStatus = 1;
                }
            }
            return text;
        }

        String delete(String[] str) {
            int strlength = str.length;
            if (text.contains(str[1])) {
                if (strlength == 3 && str[2].compareTo("-f") == 0) {
                    int firstNumber = text.lastIndexOf(str[1], text.length());
                    int lastNumber = firstNumber + str[1].length();
                    text = text.substring(0, firstNumber) + text.substring(lastNumber, text.length());
                } else if (strlength == 2) {
                    int firstNumber = text.indexOf(str[1]);
                    int lastN = firstNumber + str[1].length();
                    text = text.substring(0, firstNumber) + text.substring(lastN, text.length());
                }
            } else {
                {
                    errorStatus = 1;
                }
            }
            return text;
        }
    }
}
	
  