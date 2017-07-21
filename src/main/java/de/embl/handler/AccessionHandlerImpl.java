package de.embl.handler;

import de.embl.helper.AccessionHelper;
import org.springframework.stereotype.Repository;

import javax.inject.Singleton;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by rafaelmm on 20/07/17.
 */
@Singleton
@Repository
public class AccessionHandlerImpl implements AccessionHandler {

    @Override
    public String execute(String text) {
        List<String> result = new ArrayList<String>();

        //Comma split the content in text json field.
        List<String> accessionList = AccessionHelper.splitAccessions(text);

        //Sorting the accessions list
        accessionList = AccessionHelper.sort(accessionList);

        //Check the accessions and group if necessary
        accessionList.forEach(item->{

            //In case the first time in loop just add the first accession in list
            if (result.isEmpty()) {
                result.add(item);
                return;
            }

            //getting the last value insert in list
            String lastItem = AccessionHelper.getLastItem(result.get(result.size()-1));
            //checking if the current item is sequential of last item
            if (AccessionHelper.isSequentialAccessions(lastItem, item) ){
                //The accessions are sequential and should be grouped
                //get the first item if it is already a grouped value
                String startValue = AccessionHelper.getFirstItem(result.get(result.size()-1));
                //remove the last (old) value and add the new value
                result.remove(result.size() - 1);
                result.add(startValue + "-" + item);
            }else {
                //If not sequential accessions, so just add it in list
                result.add(item);
            }
        });

        return String.join(",", result);
    }
}
