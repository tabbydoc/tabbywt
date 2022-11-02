package com.example.demo;

import com.google.common.base.Optional;
import org.jsoup.nodes.Element;
import webreduce.data.TableType;
import webreduce.extraction.mh.TableClassification;
import webreduce.extraction.mh.features.FeaturesP2;
import webreduce.extraction.mh.tools.ClassificationResult;
import webreduce.extraction.mh.tools.TableConvert;
import weka.classifiers.Classifier;
import weka.core.Attribute;
import weka.core.Instance;

import java.io.InputStream;

public class ErebiusClassifier extends TableClassifier {
    private static final String MODEL_PATH = "/RandomForest_P2.mdl";
    private TableConvert tableConvert;
    private Classifier classifier2;
    private FeaturesP2 phase2Features;
    private Attribute classAttr2;
    private double layoutVal, relationVal, entityVal, matrixVal, noneVal;

    public ErebiusClassifier(){
        try {
            classifier2 = loadModelFromClasspath(MODEL_PATH);
        } catch (Exception e){
            e.printStackTrace();
        }

        phase2Features = new FeaturesP2();
        tableConvert = new TableConvert(2, 2);

        // Phase 2
        classAttr2 = new Attribute("class", phase2Features.getClassVector());

        relationVal = classAttr2.indexOfValue("RELATION");
        entityVal = classAttr2.indexOfValue("ENTITY");
        matrixVal = classAttr2.indexOfValue("MATRIX");
        noneVal = classAttr2.indexOfValue("NONE");
    }

    @Override
    public TableType classify(Element table){
        Optional<Element[][]> convertedTable = tableConvert.toTable(table);
        if(!convertedTable.isPresent())
            return TableType.LAYOUT;

        return classifyTable(convertedTable.get());
    }

    public TableType classifyTable(Element[][] convertedTable) {
        try {
            Instance currentInst = phase2Features.computeFeatures(convertedTable);
            double cls = classifier2.classifyInstance(currentInst);
            // classifier2.distributionForInstance(instance)
            TableType resultType;
            if (cls == relationVal)
                resultType = TableType.RELATION;
            else if (cls == entityVal)
                resultType = TableType.ENTITY;
            else if (cls == matrixVal)
                resultType = TableType.MATRIX;
            else if (cls == noneVal)
                resultType = TableType.OTHER;
            else {
                // Error
                resultType = TableType.LAYOUT;
            }
            return resultType;
        } catch (Exception e) {
            // classification failed
            e.printStackTrace();
            return null;
        }
    }

    public static Classifier loadModelFromClasspath(String path) throws Exception {
        InputStream stream = TableClassification.class.getResourceAsStream(path);
        return (Classifier) weka.core.SerializationHelper.read(stream);
    }
}
