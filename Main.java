import java.io.*;
import java.util.*;

public class Main {
    static final int MONTHS = 12;
    static final int DAYS = 28;
    static final int COMMS = 5;
    static String[] commodities = {"Gold", "Oil", "Silver", "Wheat", "Copper"};
    static String[] months = {"January","February","March","April","May","June",
            "July","August","September","October","November","December"};
    static int[][][] data = new int[MONTHS][DAYS][COMMS];


    // ======== REQUIRED METHOD LOAD DATA (Students fill this) ========
    public static void loadData() {
        for (int m = 0; m < months.length; m++) {
            try {
                Scanner sc = new Scanner(new File("src/Data_Files/" + months[m] + ".txt"));

                if (sc.hasNextLine()) {
                    sc.nextLine();
                }

                while (sc.hasNextLine()) {
                    String line = sc.nextLine();
                    String[] p = line.split(",");

                    int day = Integer.parseInt(p[0]);
                    String commodity = p[1];
                    int profit = Integer.parseInt(p[2]);

                    for (int c = 0; c < commodities.length; c++) {
                        if (commodities[c].equals(commodity)) {
                            data[m][day - 1][c] = profit;
                            break;
                        }
                    }
                }
            }
            catch (FileNotFoundException e) {
                System.out.println("Dosya bulunamadı!");
            }
        }
    }

    // ======== 10 REQUIRED METHODS (Students fill these) ========

    public static String mostProfitableCommodityInMonth(int month) {
        if (month < 0 || month > 11) {
            return "INVALID_MONTH";
        }

        int maxProfit = Integer.MIN_VALUE;
        String result = "";

        for (int c = 0; c < commodities.length; c++) {
            int total = 0;

            for (int d = 0; d < 28; d++) {
                total += data[month][d][c];
            }

            if (total > maxProfit) {
                maxProfit = total;
                result = commodities[c] + " " + total;
            }
        }

        return result;
    }

    public static int totalProfitOnDay(int month, int day) {
        if (month > 11 || month < 0 || day < 0 || day > 27 ) {
            return -99999; //error
        }

        int totalProfitOnDay = 0;
        for (int i = 0 ; i < 5 ; i++ ) {
            totalProfitOnDay += data[month][day][i];
        }
        return totalProfitOnDay;
    }

    public static int commodityProfitInRange(String commodity, int from, int to) {
        if ( commodity != "Gold" && commodity != "Wheat" && commodity != "Oil" && commodity != "Silver" && commodity != "Copper" ) {
            if (to > 28 || from < 0 || to < 0 || from > to) {
                return -99999;
            }
        }
        int sum = 0;
        int month = 0;
        if (month > 11 || month < 0) {
            return -99999;
        }
        int a = 0;
        for(int i = 0 ; i < commodities.length ; i++) {
           if (commodities[i] == commodity) {
               a = i;
               break;
           }
        }
        for (; from < to ; from++) {
            sum += data[month][from][a];
        }
        return sum;
    }

    public static int bestDayOfMonth(int month) {
        if (month > 11 || month < 0) {
            return -99999;
        }
        int bestDay = Integer.MIN_VALUE;
        int returnday = 0;
        for (int i = 0 ; i < DAYS ; i++) {
            if (totalProfitOnDay(month,i) > bestDay) {
                returnday = i;
            }
        }
        return returnday;
    }

    public static String bestMonthForCommodity(String comm) {
        if ( comm != "Gold" && comm != "Wheat" && comm != "Oil" && comm != "Silver" && comm != "Copper" ) {
            return "INVALID_COMMODITY";
        }
        String mon = "";
        int maxmonth = 0;
        int maxMValue = Integer.MIN_VALUE;
        for (int i = 0 ; i < MONTHS ; i++) {
            if (commodityProfitInRange(comm,0,28) > maxMValue) {
                maxmonth = i;
                maxMValue = commodityProfitInRange(comm,0,28);
            }
        }
        return months[maxmonth] + " " + maxMValue;
    }

    public static int consecutiveLossDays(String comm) {
        if ( comm != "Gold" && comm != "Wheat" && comm != "Oil" && comm != "Silver" && comm != "Copper" ) {
            return -1;
        }
        int a = 0;
        for(int i = 0 ; i < commodities.length ; i++) {
            if (commodities[i] == comm) {
                a = i;
                break;
            }
        }
        int Conseclssdays = 0;
        int maxconsday = 0;
        for (int i = 0 ; i < MONTHS ; i++) {
            for (int c = 0 ; c < DAYS ; c++ ) {
                if (data[i][c][a] < 0) {
                    Conseclssdays += 1;
                    if (Conseclssdays > maxconsday) {
                        maxconsday = Conseclssdays;
                    }
                }
                if (data[i][c][a] > 0) {
                    Conseclssdays = 0;
                }
            }
        }
        return maxconsday;
    }

    public static int daysAboveThreshold(String comm, int threshold) {
        if ( comm != "Gold" && comm != "Wheat" && comm != "Oil" && comm != "Silver" && comm != "Copper" ) {
            return -1;
        }
        int trdays = 0;
        int a = 0;
        for(int i = 0 ; i < commodities.length ; i++) {
            if (commodities[i] == comm) {
                a = i;
                break;
            }
        }
        for (int i = 0 ; i < MONTHS ; i++) {
            for (int c = 0 ; c < DAYS ; c++ ) {
                if (data[i][c][a] > threshold) {
                    trdays += 1;
                }
            }
        }
        return trdays;
    }

    public static int biggestDailySwing(int month) {
        int biggstswng = 0;
        int gxc = 0;
        for (int i = 0 ; i < DAYS-1 ; i++) {
            gxc = Math.abs(totalProfitOnDay(month, i)) - Math.abs(totalProfitOnDay(month, i+1));
            if ( gxc > biggstswng ) {
                biggstswng = gxc;
            }
        }
        return gxc;
    }

    public static String compareTwoCommodities(String c1, String c2) {
        int comparedvalue = 0;
        comparedvalue = commodityProfitInRange(c1,0,28) - commodityProfitInRange(c2,0,28);
        if (commodityProfitInRange(c1,0,28) > commodityProfitInRange(c2,0,28)) {
            return c1 + " is better by " + comparedvalue;
        }
        else if (commodityProfitInRange(c2,0,28) > commodityProfitInRange(c1,0,28)) {
            return c2 + " is better by " + comparedvalue;
        }
        else {
            return "equal";
        }
    }

    public static String bestWeekOfMonth(int month) {
        int bestmonth = 0;
        int bestmonthproft = 0;
        int i = 0;
        int xx= 7;
        int monthlyproft = 0;
        for (int c = 0 ; c < 4 ; c++) {
            for (; i < xx;) {
                i++;
                monthlyproft += totalProfitOnDay(month, i);
            }
            if (monthlyproft > bestmonthproft) {
                bestmonth = c + 1;
            }
            monthlyproft = 0;
            xx +=7;
        }
        return "Week " + bestmonth;
    }

    public static void main(String[] args) {
        loadData();
        System.out.println("Data loaded – ready for queries");
    }
}