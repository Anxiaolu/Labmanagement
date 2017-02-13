/*
 * Copyright 2017 huanlu.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package cn.edu.sdut.softlab.util;

/**
 *
 * @author huanlu
 */

public class dateUtil {

//    /**
//     * Given a Date, returns a String of the form "Day, Month Number, Year", e.g., "Friday, January
//     * 30, 2015". Used by getStartDay and getEndDay. Arguably simpler than doing the formatting in
//     * the results page.
//     */
//    public static String formatDay(Date date) {
//        if (date == null) {
//            return ("");
//        } else {
//            return (String.format("%tA, %tB %te, %tY",
//                    date, date, date, date));
//        }
//    }
//
//    /**
//     * Given a Date, returns a String of the form "hh:mm:ss am on Day, Month Number, Year", e.g.,
//     * "12:23:42 pm on Wednesday, November 13, 2013". Used by getSampleTime.
//     */
//    public static String formatTime(Date date) {
//        if (date == null) {
//            return ("");
//        } else {
//            return (String.format("%tl:%tM:%tS %tp on %tA, %tB %te, %tY",
//                    date, date, date, date, date, date, date, date));
//        }
//    }
//
//    /**
//     * Returns a Date 24 hours later than the input Date.
//     */
//    public static Date nextDay(Date day) {
//        long millisPerDay = 24 * 60 * 60 * 1000;
//        return (new Date(day.getTime() + millisPerDay));
//    }
//
//    /**
//     * Returns true if the first Date is between the other two; returns false otherwise.
//     */
//    public static boolean between(Date testDate, Date startDate, Date endDate) {
//        return (testDate.after(startDate) && testDate.before(endDate));
//    }
//
//    /**
//     * Test routine.
//     */
//    public static void main(String[] args) {
//        Date d = new Date();
//        System.out.println("Day: " + formatDay(d));
//        System.out.println("Time: " + formatTime(d));
//    }
//
//    private dateUtil() {
//    } // Uninstantiatable class
}
