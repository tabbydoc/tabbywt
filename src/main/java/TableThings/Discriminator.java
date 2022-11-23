package TableThings;

import webreduce.data.TableType;

import org.jsoup.nodes.Element;


/* Phase 1 classification
 * Layout/other
 */
public abstract class Discriminator {
    public TableType classify(Element table){
        return TableType.LAYOUT;
    }
}
