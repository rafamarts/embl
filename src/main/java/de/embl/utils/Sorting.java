package de.embl.utils;

import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * Created by rafaelmm on 18/07/17.
 */
public class Sorting {

    public List<String> sort(List<String> list){
        Collections.sort(list);
        return list;
    }

    private List<String> splitAccessionNumber(String accessionNumber){
        List<String> splited = Pattern.compile("(?<=\\D)(?=\\d)")
                .splitAsStream(accessionNumber)
                .map(String::trim)
                .collect(Collectors.toList());

        return splited;
    }

    private boolean isSameAccessionGroup(String accessionA, String accessionB){
        return accessionA.equalsIgnoreCase(accessionB);
    }

    private boolean isSequentialNumbers(String numberA, String numberB){

        if (numberA.length() != numberB.length())
            return false;

        int numA = Integer.parseInt(numberA);
        int numB = Integer.parseInt(numberB);

        if ( Math.abs((numA - numB)) == 1 )
            return true;
        return false;
    }

    public boolean isSequentialAssessions(String accessionA, String accessionB){

        List<String> accessionNumberA = splitAccessionNumber(accessionA);
        List<String> accessionNumberB = splitAccessionNumber(accessionB);

        if (isSameAccessionGroup(accessionNumberA.get(0), accessionNumberB.get(0)) &&
                isSequentialNumbers(accessionNumberA.get(1), accessionNumberB.get(1))){
            return true;
        }

        return false;
    }

    public static void main(String args[]){
        Sorting s = new Sorting();
        if (s.isSequentialNumbers("040001", "0000002"))
            System.out.println("Sequential Numbers");
        else
            System.out.println("Non Sequential Numbers");

        System.out.println(s.splitAccessionNumber("ERR000112"));

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
