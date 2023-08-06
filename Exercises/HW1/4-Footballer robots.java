import java.util.*;

public class Robot {

    public static final String robotName = "forward";
    public static final String beginString = "@#";
    public static final String endString = "#@";
    public static final String xString = "x=";
    public static final String yString = "y=";
    public static final String distaceString = "distance=";
    public static final int idelState = 0;
    public static final int nearRobotState = 1;
    public static final int shootState = 2;
    static info newData = new info();
    static info lastData = new info();
    static int errorNo = 0;
    static int data = 0;
    static boolean nearRobotFlag = false;
    static boolean goesFarFlag = false;
    static boolean nearGoalFlag = false;
    static String inputStream = new String();
    static int inputStreamIndexPointer = 0;
    static int lastStreamIndexPointer = 0;
    static int streamSize;
    static boolean endOfProcess = false;
    static int state = 0;

    public static char readCharFromStream() {
        inputStreamIndexPointer++;
        if (inputStreamIndexPointer <= streamSize) {
            return inputStream.charAt(inputStreamIndexPointer - 1);
        } else {
            endOfProcess = true;
            return 0;
        }
    }

    public static int r2g() {
        return (int) Math.sqrt(Math.pow(newData.x, 2) + Math.pow(newData.y, 2));
    }

    public static int distant(info i1, info i2) {
        return (int) Math.sqrt(Math.pow((i1.x - i2.x), 2) + Math.pow((i1.y - i2.y), 2));
    }

    static void nearRobot() {
        nearRobotFlag = (newData.d2b <= 10);
    }

    static void goesFar() {
        goesFarFlag = (lastData.d2b < newData.d2b);
    }

    static void nearGoal() {
        nearGoalFlag = Math.abs(newData.d2b - r2g()) < 10;
    }


    static void replaceInfo(info source, info destination) {
        destination.x = source.x;
        destination.y = source.y;
        destination.d2b = source.d2b;
    }

    static boolean refreshState() {

        info newi = new info();

        if (newi.getMessage()) {
            replaceInfo(newData, lastData);
            replaceInfo(newi, newData);
            nearRobot();
            nearGoal();
            goesFar();
            return true;
        } else return false;
    }

    public static int stateMan() {

        int goalCount = 0;

        while (!endOfProcess) {
            while (state == idelState) {
                if (refreshState()) {
                    if (nearRobotFlag) state = nearRobotState;
                } else if (endOfProcess) return goalCount;
            }
            while (state == nearRobotState) {
                if (refreshState()) {
                    if (!nearGoalFlag && goesFarFlag) state = shootState;
                    if (nearGoalFlag && goesFarFlag) {
                        goalCount++;
                        state = idelState;
                    }
                } else if (endOfProcess) return goalCount;
            }
            while (state == shootState) {
                if (refreshState()) {
                    if (nearGoalFlag && goesFarFlag) {
                        state = idelState;
                        goalCount++;
                    }
                    if (!nearGoalFlag && !goesFarFlag) state = idelState;
                    if (nearGoalFlag && !goesFarFlag) state = nearRobotState;
                } else if (endOfProcess) return goalCount;
            }
        }
        return goalCount;
    }

    public static void main(String[] args) {

        newData.d2b = 999;
        Scanner sc = new Scanner(System.in);
        inputStream = sc.nextLine();
        streamSize = inputStream.length();
        if (streamSize == 0) return;

        System.out.println(stateMan());
    }

    private static class info {

        int x;
        int y;
        int d2b;

        void info() {

            x = 0;
            y = 0;
            d2b = -1;
        }

        boolean getMessage() {

            boolean rm = false;
            info ni = new info();
            while (!rm && !endOfProcess) {

                if (!ni.findStart()) return false;
                if (ni.matchKeyString(robotName + ',')) {
                    if (ni.matchKeyString(xString)) {
                        if (ni.getData()) {
                            ni.x = data;
                            if (ni.matchKeyString(yString)) {
                                if (ni.getData()) {
                                    ni.y = data;
                                    if (ni.matchKeyString(distaceString)) {
                                        if (ni.getData()) {
                                            ni.d2b = data;
                                            if (ni.matchKeyString(endString)) {
                                                rm = true;
                                                lastStreamIndexPointer = inputStreamIndexPointer;
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                if (errorNo >= 200) return false;
            }
            replaceInfo(ni, this);
            return true;
        }

        boolean checkError() {
            errorNo++;
            if (errorNo >= 200) {
                endOfProcess = true;
                return true;
            } else return false;
        }

        boolean findStart() {

            boolean ns = true;
            char tcl;
            char tcn = '?';

            tcl = readCharFromStream();
            if (checkError()) return false;
            while (ns && !endOfProcess) {
                tcn = readCharFromStream();
                if (checkError()) return false;
                if (tcn != beginString.charAt(1) || tcl != beginString.charAt(0)) {
                    tcl = tcn;
                } else ns = false;
            }
            if (endOfProcess) return false;
            else {
                errorNo = 0;
                return true;
            }
        }

        boolean matchKeyString(String ms) {

            char readchar;
            int sms;
            sms = ms.length();
            int i = 0;
            do {
                readchar = readCharFromStream();
                if (checkError()) return false;
                if (readchar != ms.charAt(i)) {
                    if (checkError()) return false;
                    inputStreamIndexPointer--;
                    return false;
                }
                i++;
            } while (i < sms && !endOfProcess);
            errorNo = 0;
            return true;
        }

        boolean getData() {

            char readchar;
            int n;
            boolean Continue = true;


            int i = 0;
            data = 0;

            do {
                readchar = readCharFromStream();
                n = readchar - '0';
                if (n >= 0 && n <= 9) {
                    data = data * 10 + n;
                } else {
                    Continue = false;
                    if (readchar == '@') {
                        inputStreamIndexPointer--;
                    } else if (readchar != ',') {
                        inputStreamIndexPointer--;
                        if (i == 0) return false;
                    }
                }
                i++;
            } while (Continue);
            return true;
        }
    }
}