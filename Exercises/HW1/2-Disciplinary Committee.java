import java.util.*;
import java.io.*;

public class HW2 {

    static InputStreamReader myStream;

    public static String readLine() throws IOException {
        char charRead;
        String newLineInput = "";
        do {
            charRead = (char) myStream.read();
        } while ((charRead == '\n' || charRead == '\r'));
        while (charRead != '\n' && charRead != '\r') {
            if ((charRead >= '0' && charRead <= '9') || (charRead >= 'a' && charRead <= 'z') || (charRead >= ' '))
                newLineInput = newLineInput + charRead;
            charRead = (char) myStream.read();
        }
        return newLineInput;
    }

    public static void main(String[] args) {

        ArrayList<String> players = new ArrayList<String>();
        guiltyTeamList guiltyTeams = new guiltyTeamList();
        String newLine;

        myStream = new InputStreamReader(System.in);

        try {
            newLine = readLine();
            String[] arrOfStr = newLine.split(" ");
            int n = Integer.parseInt(arrOfStr[0]);
            int m = Integer.parseInt(arrOfStr[1]);

            int[] congestionDetect = new int[n];
            String[] firstHitTeam = new String[n];

            for (int playerIndex = 0; playerIndex < n; playerIndex++) {
                congestionDetect[playerIndex] = 0;
                players.add(readLine());
            }
            String teamname;
            for (int teamIndex = 0; teamIndex < m; teamIndex++) {
                int activeTeamIndex = 0;
                teamname = readLine();
                int teamPlayerNo = Integer.parseInt(readLine());
                int noNeedToAdd = 0;
                while (activeTeamIndex < teamPlayerNo) {
                    int teamPlayerListIndex = players.indexOf(readLine());
                    if (teamPlayerListIndex != -1) {
                        if (congestionDetect[teamPlayerListIndex] == 0) {
                            congestionDetect[teamPlayerListIndex] = 1;
                            firstHitTeam[teamPlayerListIndex] = teamname;
                        } else if (congestionDetect[teamPlayerListIndex] == 1) {
                            congestionDetect[teamPlayerListIndex] = 2;
                            guiltyTeams.addteam(firstHitTeam[teamPlayerListIndex]);
                        }
                        if (congestionDetect[teamPlayerListIndex] == 2 && noNeedToAdd == 0) {
                            guiltyTeams.addteam(teamname);
                            noNeedToAdd = 1;
                        }
                    } else if (noNeedToAdd == 0) {
                        guiltyTeams.addteam(teamname);
                        noNeedToAdd = 1;
                    }
                    activeTeamIndex++;
                }
            }
            if (!guiltyTeams.teams.isEmpty()) {
                Collections.sort(guiltyTeams.teams);
                for (String team : guiltyTeams.teams) {
                    System.out.println(team);
                }
            }
            myStream.close();
        } catch (IOException e) {
            System.out.print("Exception");
        }
    }

    private static class guiltyTeamList {
        ArrayList<String> teams;

        public guiltyTeamList() {
            teams = new ArrayList<String>();
        }

        void addteam(String newteam) {
            int teamIndex = teams.indexOf(newteam);
            if (teamIndex == -1) teams.add(newteam);
        }

    }
}