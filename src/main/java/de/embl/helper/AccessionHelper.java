package de.embl.helper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * Created by rafaelmm on 18/07/17.
 */
public class AccessionHelper {

    private static final String ACCESSION_SEPARATOR = ",";
    private static final String ACCESSION_RANGE_SEPARATOR = "-";

    /**
    * Sort the List
    *
    * @param list
    * @return List<String>
    * */
    public static List<String> sort(List<String> list){
        if (list == null)
            return new ArrayList<>();

        Collections.sort(list);
        return list;
    }

    /**
    * Split the text accessions separated by separator
    *
    * @param accessionNumbers
    * @return List<String>
    * */
    public static List<String> splitAccessions(String accessionNumbers){
        if (accessionNumbers == null)
            return new ArrayList<String>();
        //spliting in SEPARATOR and converting to list
        return Pattern.compile(ACCESSION_SEPARATOR)
                .splitAsStream(accessionNumbers)
                .map(String::trim)
                .collect(Collectors.toList());
    }

    /**
    * Split the Group and Number from accession
    *
    * @param accessionNumber
    * @return List<String> where index 0 is Group and index 1 is strNumber
    * */
    private static List<String> splitAccession(String accessionNumber){
        if (accessionNumber == null)
            return new ArrayList<String>();
        //Spliting the accession from a regex, separating accession group from accesson number
        List<String> splited = Pattern.compile("(?<=\\D)(?=\\d)")
                .splitAsStream(accessionNumber)
                .map(String::trim)
                .collect(Collectors.toList());

        return splited;
    }

    /**
    * Check if two accessions are from same group
    *
    * @param accessionA
    * @param accessionB
    * @return boolean
    * */
    private static boolean isSameAccessionGroup(String accessionA, String accessionB){
        return getAccessionGroup(accessionA).equalsIgnoreCase(getAccessionGroup(accessionB));
    }

    /**
     * Check if two accession has sequential number
     *
     * @param accessionA
     * @param accessionB
     * @return boolean
     */
    private static boolean isSequentialNumbers(String accessionA, String accessionB){

        //Getting the string number part of accession
        String strNumberA = getAccessionNumber(accessionA);
        String strNumberB = getAccessionNumber(accessionB);

        //Check if the string number has same length - because left padded 0s
        if (strNumberA.length() != strNumberB.length())
            return false;

        //Casting for int - don't need try-catch because the strNumber already
        //was validate by regex in SortBodyValidator class
        int numberA = Integer.parseInt(strNumberA);
        int numberB = Integer.parseInt(strNumberB);

        //checking if the number are sequential - diff in 1
        if ( Math.abs((numberA - numberB)) == 1 )
            return true;
        return false;
    }

    /**
     * Check if two accessions are sequential
     *
     * @param accessionA
     * @param accessionB
     * @return boolean
     */
    public static boolean isSequentialAccessions(String accessionA, String accessionB){

        //checking if is from same group and if are sequential
        if (isSameAccessionGroup(accessionA, accessionB) &&
                isSequentialNumbers(accessionA, accessionB)){
            return true;
        }

        return false;
    }

    /**
     * Get the Group from accession
     *
     * @param accession
     * @return String
     */
    private static String getAccessionGroup(String accession){
        return splitAccession(accession).get(0);
    }

    /**
     * Get the Number from accession
     *
     * @param accessionNumber
     * @return String
     */
    private static String getAccessionNumber(String accessionNumber){
        return splitAccession(accessionNumber).get(1);
    }

    /**
     * Get last item from ranged accession
     *      if not grouped return the original accession
     *
     * @param accession
     * @return String
     */
    public static String getLastItem(String accession){
        //Check if has ranged separator
        if (accession.contains(ACCESSION_RANGE_SEPARATOR)){
            return getItem(accession, 1);//return the last part
        }
        return accession;//original accession
    }

    /**
     * Get first item from ranged accession
     *      if not grouped return the original accession
     *
     * @param accession
     * @return String
     */
    public static String getFirstItem(String accession){
        //Check if has ranged separator
        if (accession.contains(ACCESSION_RANGE_SEPARATOR)){
            return getItem(accession, 0);//return the fist part
        }
        return accession;//original accession
    }

    /**
     * Get First part (index 0) or get the Last part (index 1)
     *
     * @param accession
     * @param index 0 | 1
     * @return String
     */
    private static String getItem(String accession, int index){
        if (accession == null)
            return "";
        return Pattern.compile(ACCESSION_RANGE_SEPARATOR)
                .splitAsStream(accession)
                .map(String::trim)
                .collect(Collectors.toList()).get(index);
    }

    //helping during development  - private method
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

        if (s.isSequentialAccessions("ERR000112","ERR000115"))
            System.out.println("Group");
        else
            System.out.println("Non Group");
    }

}
