package com.example.demo;

import TableThings.Discriminator;
import com.google.common.base.Optional;
import org.jsoup.nodes.Element;
import webreduce.data.TableType;
import webreduce.extraction.mh.TableClassification;
import webreduce.extraction.mh.features.FeaturesP1;
import webreduce.extraction.mh.tools.TableConvert;
import weka.classifiers.Classifier;
import weka.core.Attribute;
import weka.core.Instance;

import java.io.InputStream;

public class DiscriminatorErebius extends Discriminator {
    private static final String MODEL_PATH = "/SimpleCart_P1.mdl";
    private TableConvert tableConvert;
    private Classifier classifier;
    private FeaturesP1 features;
    private Attribute classAttr1;
    private double layoutVal;

    public DiscriminatorErebius(){
        try {
            classifier = loadModelFromClasspath(MODEL_PATH);
        } catch (Exception e){
            e.printStackTrace();
        }

        features = new FeaturesP1();
        tableConvert = new TableConvert(2, 2);
        classAttr1 = new Attribute("class", features.getClassVector());
        layoutVal = classAttr1.indexOfValue("LAYOUT");
    }

    @Override
    public TableType classify(Element table){
        Optional<Element[][]> convertedTable = tableConvert.toTable(table);
        if(!convertedTable.isPresent())
            return TableType.LAYOUT;

        return classifyTable(convertedTable.get());
    }

    private TableType classifyTable(Element[][] convertedTable) {
        Instance currentInst = features.computeFeatures(convertedTable);
        try {
            double cls = classifier.classifyInstance(currentInst);
            if (cls == layoutVal) {
                return TableType.LAYOUT;
            } else {
                return TableType.OTHER;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Classifier loadModelFromClasspath(String path) throws Exception {
        InputStream stream = TableClassification.class.getResourceAsStream(path);
        return (Classifier) weka.core.SerializationHelper.read(stream);
    }
}
