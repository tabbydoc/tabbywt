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
    private static final String MODEL_PATH = "/SimpleCart_P1.mdl"; // Путь к модели для классификации
    private TableConvert tableConvert; // Объект для конвертации элементов таблицы
    private Classifier classifier; // Классификатор для определения типа таблицы
    private FeaturesP1 features; // Объект для хранения и вычисления характеристик элементов таблицы
    private Attribute classAttr1; // Атрибут класса для хранения типа таблицы
    private double layoutVal; // Значение, представляющее тип "LAYOUT"

    public DiscriminatorErebius(){
        try {
            // Загрузка модели классификации из файла
            classifier = loadModelFromClasspath(MODEL_PATH);
        } catch (Exception e){
            e.printStackTrace(); // Вывод исключений в случае ошибки загрузки модели
        }

        features = new FeaturesP1(); // Инициализация объекта features
        tableConvert = new TableConvert(2, 2); // Инициализация объекта tableConvert
        classAttr1 = new Attribute("class", features.getClassVector()); // Инициализация атрибута classAttr1
        layoutVal = classAttr1.indexOfValue("LAYOUT"); // Присвоение layoutVal индекса значения "LAYOUT" в classAttr1
    }

    @Override
    public TableType classify(Element table){
        // Конвертация элемента table и проверка, присутствует ли он
        Optional<Element[][]> convertedTable = tableConvert.toTable(table);
        if(!convertedTable.isPresent())
            return TableType.LAYOUT; // Если элемента нет, возвращаем тип "LAYOUT"

        // Если элемент присутствует, производим классификацию
        return classifyTable(convertedTable.get());
    }

    private TableType classifyTable(Element[][] convertedTable) {
        // Вычисление характеристик для convertedTable
        Instance currentInst = features.computeFeatures(convertedTable);
        try {
            // Классификация currentInst
            double cls = classifier.classifyInstance(currentInst);
            if (cls == layoutVal) {
                return TableType.LAYOUT; // Если значение равно layoutVal, возвращаем тип "LAYOUT"
            } else {
                return TableType.OTHER; // В противном случае, возвращаем тип "OTHER"
            }
        } catch (Exception e) {
            e.printStackTrace(); // Вывод исключений в случае ошибки классификации
            return null; // Возвращаем null в случае ошибки
        }
    }

    // Метод для загрузки модели классификации из файла, находящегося в classpath
    public static Classifier loadModelFromClasspath(String path) throws Exception {
        // Получение InputStream из файла, находящегося в classpath
        InputStream stream = TableClassification.class.getResourceAsStream(path);
        // Чтение и возврат объекта Classifier из InputStream
        return (Classifier) weka.core.SerializationHelper.read(stream);
    }
}

