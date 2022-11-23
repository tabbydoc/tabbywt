package TableThings;

import webreduce.data.TableType;

import org.jsoup.nodes.Element;


/* Phase 2 table classification
 * Non-layout -- Matrix, Relation, ...
 */

public abstract class TableClassifier {
    public TableType classify(Element table){
        return TableType.LAYOUT; // default, override me!
    }
}
