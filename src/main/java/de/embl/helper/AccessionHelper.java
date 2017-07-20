package de.embl.helper;

import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * Created by rafaelmm on 18/07/17.
 */
public class AccessionHelper {

    public static List<String> sort(List<String> list){
        Collections.sort(list);
        return list;
    }

    public static List<String> splitAccessions(String accessionNumbers){
        return Pattern.compile(",")
                .splitAsStream(accessionNumbers)
                .map(String::trim)
                .collect(Collectors.toList());
    }

    private static List<String> splitAccession(String accessionNumber){
        List<String> splited = Pattern.compile("(?<=\\D)(?=\\d)")
                .splitAsStream(accessionNumber)
                .map(String::trim)
                .collect(Collectors.toList());

        return splited;
    }

    private static boolean isSameAccessionGroup(String accessionA, String accessionB){
        return getAccessionGroup(accessionA).equalsIgnoreCase(getAccessionGroup(accessionB));
    }

    private static boolean isSequentialNumbers(String accessionA, String accessionB){

        String strNumberA = getAccessionNumber(accessionA);
        String strNumberB = getAccessionNumber(accessionB);

        if (strNumberA.length() != strNumberB.length())
            return false;

        int numberA = Integer.parseInt(strNumberA);
        int numberB = Integer.parseInt(strNumberB);

        if ( Math.abs((numberA - numberB)) == 1 )
            return true;
        return false;
    }

    public static boolean isSequentialAssessions(String accessionA, String accessionB){

        if (isSameAccessionGroup(accessionA, accessionB) &&
                isSequentialNumbers(accessionA, accessionB)){
            return true;
        }

        return false;
    }

    private static String getAccessionGroup(String accessionNumber){
        return splitAccession(accessionNumber).get(0);
    }

    private static String getAccessionNumber(String accessionNumber){
        return splitAccession(accessionNumber).get(1);
    }

    public static String getLastItem(String accession){
        if (accession.contains("-")){
            return getItem(accession, 1);
        }
        return accession;
    }

    public static String getFirstItem(String accession){
        if (accession.contains("-")){
            return getItem(accession, 0);
        }
        return accession;
    }

    private static String getItem(String accession, int index){
        return Pattern.compile("-")
                .splitAsStream(accession)
                .map(String::trim)
                .collect(Collectors.toList()).get(index);
    }

    public static void main(String args[]){
        AccessionHelper s = new AccessionHelper();
        if (s.isSequentialNumbers("040001", "0000002"))
            System.out.println("Sequential Numbers");
        else
            System.out.println("Non Sequential Numbers");

        System.out.println(s.splitAccession("ERR000112"));

        System.out.println(s.getAccessionNumber("ERR000111"));
        System.out.println(s.getAccessionNumber("ERR000111-ERR000112"));

        if (s.isSameAccessionGroup("ERR", "ERR"))
            System.out.println("Same Group");
        else
            System.out.println("Different Group");

        if (s.isSequentialAssessions("ERR000112","ERR000115"))
            System.out.println("Group");
        else
            System.out.println("Non Group");
    }

}
