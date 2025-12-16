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
            if (to > 27 || from < 0 || to < 0 || from > to) {
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
        return 1234;
    }

    public static String bestMonthForCommodity(String comm) {
        return "DUMMY";
    }

    public static int consecutiveLossDays(String comm) {
        return 1234;
    }

    public static int daysAboveThreshold(String comm, int threshold) {
        return 1234;
    }

    public static int biggestDailySwing(int month) {
        return 1234;
    }

    public static String compareTwoCommodities(String c1, String c2) {
        return "DUMMY is better by 1234";
    }

    public static String bestWeekOfMonth(int month) {
        return "DUMMY";
    }

    public static void main(String[] args) {
        loadData();
        System.out.println("Data loaded – ready for queries");
        System.out.println(mostProfitableCommodityInMonth(6));
        System.out.println(data[0][14][0]);
        System.out.println(totalProfitOnDay(0,27));
        System.out.println(commodityProfitInRange("Gold",0,1));
    }
}